package net.cromulence.datawrapper.mongo;

import java.lang.Deprecated;
import net.cromulence.datawrapper.document.DocumentDataWrapperImpl;

/**
 * Mongo-based wrapper. Adds some extra methods which allow manipulation of
 * stored data in a document-aware manner. It is still possible to use Mongo
 * within a regular DataWrapperImpl, but some of the features of Mongo are
 * unavailable like this
 * @deprecated
 */
@Deprecated
public class MongoDataWrapperImpl extends DocumentDataWrapperImpl {

    public MongoDataWrapperImpl(MongoDataStoreConnector dataStore) {
        super(dataStore);
    }

    @Override
    public MongoDataStoreConnector getConnector() {
        return (MongoDataStoreConnector) super.getConnector();
    }
}
