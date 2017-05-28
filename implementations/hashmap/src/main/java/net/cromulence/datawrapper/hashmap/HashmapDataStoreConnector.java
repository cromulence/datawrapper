package net.cromulence.datawrapper.hashmap;

import net.cromulence.datawrapper.AbstractDataStoreConnector;

import java.util.HashMap;
import java.util.Map;


public class HashmapDataStoreConnector extends AbstractDataStoreConnector
{
	private Map<String, Object> data;

	public HashmapDataStoreConnector()
	{
		data = new HashMap<>();
	}

	public HashmapDataStoreConnector(Map<String, Object> existing)
	{
		data = existing;
	}

	@Override
	public void remove(String name)
	{
		data.remove(name);
	}

	@Override
	public void put(String name, Object value)
	{
		data.put(name, value);
	}

	@Override
	public Object get(String name) {
		return data.get(name);
	}

	@Override
	public boolean contains(String name)
	{
		return data.containsKey(name);
	}
}
