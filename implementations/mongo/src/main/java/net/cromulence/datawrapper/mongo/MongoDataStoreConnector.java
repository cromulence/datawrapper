package net.cromulence.datawrapper.mongo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.unset;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;

import net.cromulence.datawrapper.document.AbstractDocumentDataStoreConnector;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MongoDataStoreConnector extends AbstractDocumentDataStoreConnector {

    private static Logger LOG = LoggerFactory.getLogger(MongoDataStoreConnector.class);

    private MongoDatabase database;

    private final String collectionName;

    private static final String DEFAULT_OBJECT_ID = "wrapperObject";

    private final String objectName;

    public MongoDataStoreConnector(String host, int port, String user, String password, String db, String collection) throws Exception {
        this(host, port, user, password, db, collection, DEFAULT_OBJECT_ID);
    }

    public MongoDataStoreConnector(String host, int port, String user, String password, String db, String collection, String objectName) throws Exception {
        MongoCredential credential = MongoCredential.createCredential(user, db, password.toCharArray());

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClient.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(new MyCodecProvider()));

        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(false).codecRegistry(codecRegistry).build();

        MongoClient mongo = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credential), options);
        database = mongo.getDatabase(db);

        // TODO do we need to check auth anywhere?
//		if(mongoDB.authenticate(user, password.toCharArray()) == false) {
//		    throw new DataWrapperException("Failed to authenticate against mongo db");
//		}

        this.collectionName = collection;

        this.objectName = objectName;

    }

    @Override
    public void remove(String name) {
        remove(objectName, name);
    }

    public void remove(String objectName, String name) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        LOG.debug("remove: removing " + name);
        UpdateResult id = collection.updateOne(eq("_id", objectName), unset(name));

        LOG.debug("remove: done");
    }

    @Override
    public void put(String name, Object value) {
        put(objectName, name, value);
    }

    public void put(String objectName, String name, Object value) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        collection.findOneAndUpdate(eq("_id", objectName), set(name, value), new FindOneAndUpdateOptions().upsert(true));
    }

    @Override
    public Object get(String name) {
        return get(objectName, name, Object.class);
    }

    @Override
    public Object get(String objectName, String name) {
        return get(objectName, name, Object.class);
    }

    @Override
    public <T> T get(final String name, final Class<T> clazz) {
        return get(objectName, name, clazz);
    }

    public <T> T get(String objectName, final String name, final Class<T> clazz) {
        // Mongo does its own casting, so we can override this

        LOG.debug("get(Str): key[" + name + "]");

        MongoCollection<Document> collection = database.getCollection(collectionName);

        final List<T> foundItems = new ArrayList<>();

        collection.find(eq("_id", objectName)).forEach((Block<Document>) document -> foundItems.add(document.get(name, clazz)));

        if (foundItems.isEmpty()) {
            return null;
        }

        if (foundItems.size() > 1) {
            LOG.warn("Multiple values returned");
        }

        return foundItems.get(0);
    }

    public class GsonCodec<T> implements Codec<T> {

        private final Class<T> clazz;
        private final DocumentCodec documentCodec;

        public GsonCodec(Class<T> clazz) {
            this(clazz, new DocumentCodec());
        }

        public GsonCodec(Class<T> clazz, DocumentCodec documentCodec) {
            this.clazz = clazz;
            this.documentCodec = documentCodec;
        }

        @Override
        public T decode(BsonReader bsonReader, DecoderContext decoderContext) {

            Document decode = documentCodec.decode(bsonReader, decoderContext);

            String serialize = JSON.serialize(decode);

            return new Gson().fromJson(serialize, clazz);
        }

        @Override
        public void encode(BsonWriter bsonWriter, T o, EncoderContext encoderContext) {
            String s = new Gson().toJson(o);

            Document doc = Document.parse(s);

            documentCodec.encode(bsonWriter, doc, encoderContext);
        }

        @Override
        public Class<T> getEncoderClass() {
            return clazz;
        }
    }

    public class MyCodecProvider implements CodecProvider {

        @Override
        public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
            return new GsonCodec<>(aClass);
        }
    }
}
