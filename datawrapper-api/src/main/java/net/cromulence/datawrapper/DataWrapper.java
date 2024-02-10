package net.cromulence.datawrapper;

/**
 * A DataWrapper is a higher-level interface around a DataStoreConnector,
 * providing typed methods for storing KVP data to, and retrieving it from, an
 * underlying data store.
 *
 * As single data store can be used by many DataWrappers, DataWrappers can
 * optionally provide a namespace to be used as a prefix to the keys specified.
 * This allows multiple clients to store identical data without making changes
 * to the keys used
 *
 * A DataWrapper can also wrap another DataWrapper, delegating its access to the
 * data store through that. This is particularly useful when coupled with the
 * namespacing functionality, as all DataWrappers will apply their namespaces
 * to the keys, allowing for a hierarchical data structure in a flat KVP
 * datastore
 */
public interface DataWrapper {

    /**
     * Get the underlying DataStoreConnector. This may be accessed indirectly via
     * any number of layers of delegates
     * @return The underlying DataStoreConnector
     */
    DataStoreConnector getConnector();

    /**
     * Returns the namespace being used by this wrapper instance, which will be
     * used to prefix the keys. This allows the same data store to be used to
     * store multiple sets of data without risk of key collision.
     * When used, the prefix will have a dot appended to it if one is not present
     * @return The namespace
     */
    String getPrefix();

    /**
     * Returns true if the data store contains a value with the specified key
     * @param name The key to search for
     * @return true if the data store contains a value with the specified key
     */
    boolean contains(String name);

    /**
     * If the data store requires an explicit action to persist changes (eg
     * a Properties object backed with a File), implement that here
     *
     * @throws DataWrapperException Any exception during commit
     */
    void commit() throws DataWrapperException;

    /**
     * Remove the specified value.
     * @param name The key to remove from the datastore
     */
    void remove(String name);

    /**
     * Retrieve a String value
     * @param name The name of the value to retrieve from the datastore
     * @return The value
     */
    String getString(String name);

    /**
     * Retrieve a String value. If the value is not present, the default will be returned instead
     * @param name The name of the value to retrieve
     * @param defaultValue The default value to return if the requested value is not already present
     * @return The value
     */
    String getString(String name, String defaultValue);

    /**
     * Retrieve a String value. If the value is not present, the default will inserted and returned
     * @param name The name of the value to retrieve
     * @param defaultValue The default value to store and return if the requested value is not already present
     * @return The value
     */
    String getOrInsertString(String name, String defaultValue);

    /**
     * Store a String value
     * @param name The name of the value to store
     * @param value The value to store
     */
    void putString(String name, String value);

    String[] getStringArray(String name);

    String[] getStringArray(String name, String[] defaultValue);

    String[] getOrInsertStringArray(String name, String[] defaultValue);

    void putStringArray(String name, String[] value);

    void appendToStringArray(String name, String value);

    void appendToStringArray(String name, String value, boolean unique);

    /**
     * Retrieve a Boolean value
     * @param name The name of the value to retrieve
     * @return The value
     */
    Boolean getBoolean(String name);

    Boolean getBoolean(String name, Boolean defaultValue);

    Boolean getOrInsertBoolean(String name, Boolean defaultValue);

    void putBoolean(String name, Boolean value);

    Boolean[] getBooleanArray(String name);

    Boolean[] getBooleanArray(String name, Boolean[] defaultValue);

    Boolean[] getOrInsertBooleanArray(String name, Boolean[] defaultValue);

    void putBooleanArray(String name, Boolean[] value);

    void appendToBooleanArray(String name, Boolean value);

    void appendToBooleanArray(String name, Boolean value, boolean unique);

    /**
     * Retrieve a Integer value
     * @param name The name of the value to retrieve
     * @return The value
     */
    Integer getInteger(String name);

    Integer getInteger(String name, Integer defaultValue);

    Integer getOrInsertInteger(String name, Integer defaultValue);

    void putInteger(String name, Integer value);

    Integer[] getIntegerArray(String name);

    Integer[] getIntegerArray(String name, Integer[] defaultValue);

    Integer[] getOrInsertIntegerArray(String name, Integer[] defaultValue);

    void putIntegerArray(String name, Integer[] value);

    void appendToIntegerArray(String name, Integer value);

    void appendToIntegerArray(String name, Integer value, boolean unique);

    /**
     * Retrieve a Long value
     * @param name The name of the value to retrieve
     * @return The value
     */
    Long getLong(String name);

    Long getLong(String name, Long defaultValue);

    Long getOrInsertLong(String name, Long defaultValue);

    void putLong(String name, Long value);

    Long[] getLongArray(String name);

    Long[] getLongArray(String name, Long[] defaultValue);

    Long[] getOrInsertLongArray(String name, Long[] defaultValue);

    void putLongArray(String name, Long[] value);

    void appendToLongArray(String name, Long value);

    void appendToLongArray(String name, Long value, boolean unique);

    /**
     * Retrieve a Double value
     * @param name The name of the value to retrieve
     * @return The value
     */
    Double getDouble(String name);

    Double getDouble(String name, double defaultValue);

    Double getOrInsertDouble(String name, double defaultValue);

    void putDouble(String name, Double value);

    Double[] getDoubleArray(String name);

    Double[] getDoubleArray(String name, Double[] defaultValue);

    Double[] getOrInsertDoubleArray(String name, Double[] defaultValue);

    void putDoubleArray(String name, Double[] value);

    void appendToDoubleArray(String name, Double value);

    void appendToDoubleArray(String name, Double value, boolean unique);

    /**
     * Retrieve an Object value
     * @param name The name of the value to retrieve
     * @return The value
     */
    Object getObject(String name);

    Object getObject(String name, Object defaultValue);

    Object getOrInsertObject(String name, Object defaultValue);

    void putObject(String name, Object value);

    Object[] getObjectArray(String name);

    Object[] getObjectArray(String name, Object[] defaultValue);

    Object[] getOrInsertObjectArray(String name, Object[] defaultValue);

    void putObjectArray(String name, Object[] value);

    void appendToObjectArray(String name, Object value);

    void appendToObjectArray(String name, Object value, boolean unique);

    <T> T getObject(String name, Class<T> clazz);

    <T> T  getObject(String name, Class<T> clazz, T defaultValue);

    <T> T getOrInsertObject(String name, Class<T> clazz, T defaultValue);

    <T> T[] getObjectArray(String name, Class<T> clazz);

    <T> T[] getObjectArray(String name, Class<T> clazz, T[] defaultValue);

    <T> T[] getOrInsertObjectArray(String name, Class<T> clazz, T[] defaultValue);
}
