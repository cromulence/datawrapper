package net.cromulence.datawrapper.document;

import net.cromulence.datawrapper.DataWrapperImpl;

/**
 * Document Database-based wrapper. Adds some extra methods which allow
 * manipulation of stored data in a document-aware manner. It is still possible
 * to use document dbs within a regular DataWrapperImpl, but some features are
 * unavailable like this
 */
public class DocumentDataWrapperImpl extends DataWrapperImpl {

    public DocumentDataWrapperImpl(DocumentDataStoreConnector dataStore) {
        super(dataStore);
    }

    @Override
    public DocumentDataStoreConnector getConnector() {
        return (DocumentDataStoreConnector) super.getConnector();
    }

    public void put(String objectName, String name, Object value) {
        getConnector().put(objectName, name, value);
    }

    public void remove(String objectName, String name) {
        getConnector().remove(objectName, name);
    }
}

