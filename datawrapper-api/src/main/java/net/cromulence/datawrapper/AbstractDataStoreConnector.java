package net.cromulence.datawrapper;

/**
 * Provides default implementations for some of the methods of DataStoreConnector
 * which can be implemented in a generalised way. DataStoreConnectors can extend
 * this class and override any implemented methods if their specific data store
 * has a better native way. Alternatively, data stores can ignore this class and
 * implement DataStoreConnector directly
 */
public abstract class AbstractDataStoreConnector implements DataStoreConnector {

    @Override
    public <T> T get(String name, Class<T> clazz) {
        Object value = get(name);

        if(value == null) {
            return null;
        }

        if(clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        }

        throw new ClassCastException(String.format("[%s] cannot be cast to %s from %s", name, clazz.getName(), value.getClass().getName()));
    }

    @Override
    public boolean contains(String name) {
        return get(name) != null;
    }

    @Override
    public void commit() throws DataWrapperException {
        // Default to noop
    }
}
