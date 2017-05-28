package net.cromulence.datawrapper;

/**
 * DataStoreConnector is a common interface around a KVP-based data store of
 * some kind (Simple examples would be Properties or HashMap. More complex
 * examples are HttpSession, SQL databases or Document databases). Classes
 * implementing this interface need to provide the glue code needed to perform
 * basic get/put type operations on the specific underlying data store type.
 *
 * A single data store can be used by many DataWrappers, meaning multiple
 * functional areas can have data stored in the same place. If there is a risk
 * of key collisions, a DataWrappers can specify a namespace which is used,
 * transparently, as a prefix to the keys specified.
 */
public interface DataStoreConnector {

	/**
	 * Remove the specified value from the data store
	 * @param name The key of the value to remove
	 */
	void remove(String name);

	/**
	 * Insert the specified key and value into the data store
	 * @param name The key of the value to insert
	 * @param value The value to insert
	 */
	void put(String name, Object value);

	/**
	 * Retrieve the specified value from the data store
	 * @param name The key of the value to retrieve
	 * @return The value
	 */
	Object get(String name);

	/**
	 * Retrieve the specified value from the data store, cast as the given type
	 * @param name The key of the value to retrieve
	 * @param clazz The class of the type to cast the value to
	 * @param <T> The type to cast the value to
	 * @return The value
	 */
	<T> T get(String name, Class<T> clazz);

	/**
	 * Returns true if the data store contains a value with the specified key
	 * @param name The key to search for
	 * @return true if the data store contains a value with the specified key
	 */
	boolean contains(String name);

	/**
	 * If the data store requires an explicit action to persist changes (eg
	 * a Properties object backed with a File), implement that here
	 * @throws DataWrapperException Any exception during commit
	 */
	void commit() throws DataWrapperException;
}