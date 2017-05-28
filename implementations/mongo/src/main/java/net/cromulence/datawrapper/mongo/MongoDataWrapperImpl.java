package net.cromulence.datawrapper.mongo;

import net.cromulence.datawrapper.DataWrapperImpl;

/**
 * Mongo-based wrapper. Adds some extra methods which allow manipulation of
 * stored data in a document-aware manner. It is still possible to use Mongo
 * within a regular DataWrapperImpl, but some of the features of Mongo are
 * unavailable like this
 */
public class MongoDataWrapperImpl extends DataWrapperImpl {

    public MongoDataWrapperImpl(MongoDataStoreConnector dataStore) {
        super(dataStore);
    }

    @Override
    public MongoDataStoreConnector getConnector() {
        return (MongoDataStoreConnector) super.getConnector();
    }

    public void put(String objectName, String name, Object value) {
        getConnector().put(objectName, name, value);
    }

    public void remove(String objectName, String name) {
        getConnector().remove(objectName, name);
    }

}
