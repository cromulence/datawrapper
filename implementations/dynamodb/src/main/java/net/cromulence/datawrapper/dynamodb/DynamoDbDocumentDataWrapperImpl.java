package net.cromulence.datawrapper.dynamodb;

import net.cromulence.datawrapper.document.DocumentDataStoreConnector;
import net.cromulence.datawrapper.document.DocumentDataWrapperImpl;

public class DynamoDbDocumentDataWrapperImpl extends DocumentDataWrapperImpl {

    public DynamoDbDocumentDataWrapperImpl(DocumentDataStoreConnector dataStore) {
        super(dataStore);
    }
}
