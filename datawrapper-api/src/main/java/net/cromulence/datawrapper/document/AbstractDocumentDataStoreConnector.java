package net.cromulence.datawrapper.document;

import net.cromulence.datawrapper.AbstractDataStoreConnector;

/**
 * Provides default implementations for some of the methods of
 * DocumentDataStoreConnector which can be implemented in a generalised way.
 * DocumentDataStoreConnectors can extend this class and override any
 * implemented methods if their specific data store has a better native way.
 * Alternatively, data stores can ignore this class and implement
 * DocumentDataStoreConnector directly
 */
public abstract class AbstractDocumentDataStoreConnector extends AbstractDataStoreConnector implements DocumentDataStoreConnector {

    @Override
    public <T> T get(String documentId, String name, Class<T> clazz) {
        Object value = get(documentId, name);

        if(value == null) {
            return null;
        }

        if(clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        }

        throw new ClassCastException(String.format("[%s] cannot be cast to %s from %s", name, clazz.getName(), value.getClass().getName()));
    }

    @Override
    public boolean contains(String documentId, String name) {
        return get(documentId, name) != null;
    }

}
