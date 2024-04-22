package net.cromulence.datawrapper.gcpstorage;

import com.google.cloud.ReadChannel;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import net.cromulence.datawrapper.AbstractStringToStringDataStoreConnector;
import net.cromulence.datawrapper.DataWrapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Properties;

/**
 * A DataStoreConnector for storing values in a file stored in Google Cloud Platform Cloud Storage.
 * Credentials are accessed according to how the GCP SDK accesses them
 */
public class GcpStorageFileDataStoreConnector extends AbstractStringToStringDataStoreConnector {
    private static final Logger LOG = LoggerFactory.getLogger(GcpStorageFileDataStoreConnector.class);

    Storage storage = StorageOptions.getDefaultInstance().getService();

    private File localPropertiesFile;

    private final String bucket;

    private final String path;

    private final Properties properties;

    private final boolean autoCommit;
    
    public GcpStorageFileDataStoreConnector(String bucket, String path) throws DataWrapperException {
        this(bucket, path, true);
    }

    public GcpStorageFileDataStoreConnector(String bucket, String path, boolean autoCommit) throws DataWrapperException
    {
        this.bucket = bucket;
        this.path = path;
        this.autoCommit = autoCommit;

        properties = new Properties();
        try {
            localPropertiesFile = setupPropertiesFile(bucket, path);

            properties.load(new FileInputStream(localPropertiesFile));
        } catch (Exception e) {
            throw new DataWrapperException("Unable to load properties file", e);
        }
    }

    private File setupPropertiesFile(String bucket, String path) throws IOException {
        final BlobId blobId = BlobId.of(bucket, path);
        Blob blob = storage.get(blobId);

        final File props = File.createTempFile("datawrapper", "properties");;

        // Check if file exists
        if (blob == null) {
            LOG.info("No file in GCP - creating");
            // if not, create it from a new empty properties file
            new Properties().store(new FileOutputStream(props), null);

            storage.createFrom(BlobInfo.newBuilder(blobId).build(), new FileInputStream(props));
            blob = storage.get(blobId);
        }

        ReadChannel channel = blob.reader();

        // download the file to a local temp file
        final ByteBuffer downloadedBytes = ByteBuffer.allocate(blob.getSize().intValue());
        channel.read(downloadedBytes);
        channel.close();

        LOG.info("Writing properties to {}", props.getAbsolutePath());
        final FileOutputStream fod = new FileOutputStream(props);
        fod.write(downloadedBytes.array());
        fod.flush();
        fod.close();

        return props;
    }

    @Override
    public synchronized String doGet(String name) {
        return properties.getProperty(name);
    }

    @Override
    public synchronized void doPut(String name, String value) {
        properties.put(name, value);
        autoCommit();
    }

    @Override
    public synchronized void remove(String name) {
        properties.remove(name);
        autoCommit();
    }

    @Override
    public synchronized boolean contains(String name) {
        return properties.containsKey(name);
    }

    @Override
    public synchronized void commit() throws DataWrapperException {
        try (FileOutputStream fos = new FileOutputStream(localPropertiesFile)) {
            LOG.debug("Writing properties to file");
            properties.store(fos,  null);
        } catch(IOException e) {
            throw new DataWrapperException("Unable to save properties to the local file", e);
        }

        try (WriteChannel writer = storage.get(BlobId.of(bucket, path)).writer()){
            LOG.debug("Writing properties to GCP");
            writer.write(ByteBuffer.wrap(Files.readAllBytes(localPropertiesFile.toPath())));
        } catch (IOException e) {
            throw new DataWrapperException("Unable to sync properties to GCP");
        }
    }

    private void autoCommit() {
        if(autoCommit) {
            try {
                commit();
            } catch (DataWrapperException e) {
                throw new IllegalStateException("Error autocommitting", e);
            }
        }
    }
}
