package net.cromulence.datawrapper.document;


import net.cromulence.datawrapper.DataStoreConnector;

/**
 * Extensions for DataStoreConnector to allow better use document databases for
 * data stores. An extra parameter is provided to specify the id of a document
 * to use for the data
 *
 * A single data store can be used by many DataWrappers, meaning multiple
 * functional areas can have data stored in the same place. If there is a risk
 * of key collisions, a DataWrappers can specify a namespace which is used,
 * transparently, as a prefix to the keys specified.
 */
public interface DocumentDataStoreConnector extends DataStoreConnector {

    /**
     * Remove the specified value from the data store
     * @param documentId The document to remove from
     * @param name The key of the value to remove
     */
    void remove(String documentId, String name);

    /**
     * Insert the specified key and value into the data store
     * @param documentId The document to put in
     * @param name The key of the value to insert
     * @param value The value to insert
     */
    void put(String documentId, String name, Object value);

    /**
     * Retrieve the specified value from the data store
     * @param documentId The document to get from
     * @param name The key of the value to retrieve
     * @return The value
     */
    Object get(String documentId, final String name);

    /**
     * Retrieve the specified value from the data store, cast as the given type
     * @param documentId The document to get from
     * @param name The key of the value to retrieve
     * @param clazz The class of the type to cast the value to
     * @param <T> The type to cast the value to
     * @return The value
     */
    <T> T get(String documentId, final String name, final Class<T> clazz);

    /**
     * Returns true if the data store contains a value with the specified key
     * @param documentId The document to check in
     * @param name The key to search for
     * @return true if the data store contains a value with the specified key
     */
    boolean contains(String documentId, String name);
}
