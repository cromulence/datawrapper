package net.cromulence.datawrapper.properties;

import net.cromulence.datawrapper.DataWrapperException;
import net.cromulence.datawrapper.AbstractStringToStringDataStoreConnector;
import net.cromulence.datawrapper.DataWrapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class PropertiesFileDataStoreConnector extends AbstractStringToStringDataStoreConnector {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesFileDataStoreConnector.class);

    private final File propertiesFile;

    private final SortedProperties properties;

    private final boolean autoCommit;

    public PropertiesFileDataStoreConnector(File propertiesFile) throws DataWrapperException {
        this(propertiesFile, true);
    }

    public PropertiesFileDataStoreConnector(File propertiesFile, boolean autoCommit) throws DataWrapperException
    {
        this.propertiesFile = propertiesFile;
        this.autoCommit = autoCommit;
        LOG.info("Reading database properties file " + propertiesFile.getName());
        properties = new SortedProperties();
        try {
            properties.load(new FileInputStream(propertiesFile));
        } catch (Exception e) {
            throw new DataWrapperException("Unable to load properties file", e);
        }
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
    public synchronized boolean contains(String name)
    {
        return properties.containsKey(name);
    }

    @Override
    public synchronized void commit() throws DataWrapperException {
        try {
            FileOutputStream fos = new FileOutputStream(propertiesFile);
            properties.store(fos,  null);
            fos.close();
        }
        catch(IOException e) {
            throw new DataWrapperException("Unable to save properties", e);
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

    private class SortedProperties extends Properties {
        @Override
        public Set<Object> keySet() {
            return Collections.unmodifiableSet(new TreeSet<Object>(super.keySet()));
        }

        @Override
        public Set<Map.Entry<Object, Object>> entrySet() {

            Set<Map.Entry<Object, Object>> set1 = super.entrySet();
            Set<Map.Entry<Object, Object>> set2 = new LinkedHashSet<Map.Entry<Object, Object>>(set1.size());

            Iterator<Map.Entry<Object, Object>> iterator = set1.stream().sorted(new Comparator<Map.Entry<Object, Object>>() {

                @Override
                public int compare(java.util.Map.Entry<Object, Object> o1, java.util.Map.Entry<Object, Object> o2) {
                    return o1.getKey().toString().compareTo(o2.getKey().toString());
                }
            }).iterator();

            while (iterator.hasNext())
                set2.add(iterator.next());

            return set2;
        }

        @Override
        public synchronized Enumeration<Object> keys() {
            return Collections.enumeration(new TreeSet<Object>(super.keySet()));
        }
    }
}
