package net.cromulence.datawrapper;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DataWrapperImpl implements DataWrapper {

    private final DataWrapper delegate;
    private final String prefix;

    public DataWrapperImpl(DataStoreConnector connector) {
        this(connector, null);
    }

    public DataWrapperImpl(DataStoreConnector connector, String prefix) {
        this(new ConnectorWrapper(connector), prefix);
    }

    public DataWrapperImpl(DataWrapper delegateWrapper) {
        this(delegateWrapper, null);
    }

    public DataWrapperImpl(DataWrapper delegateWrapper, String prefix) {
        this.delegate = delegateWrapper;
        this.prefix = prefix;
    }

    @Override
    public DataStoreConnector getConnector() {
        return delegate.getConnector();
    }

    @Override
    public boolean contains(String name) {
        return delegate.contains(getPrefixedName(name));
    }

    @Override
    public void commit() throws DataWrapperException {
        delegate.commit();
    }

    @Override
    public void remove(String name) {
        delegate.remove(name);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    private String getPrefixedName(String name) {
        String p = getPrefix();

        if (p == null || p.length() == 0) {
            return name;
        }

        return p + (p.endsWith(".") ? "" : ".") + name;
    }

    @Override
    public String getString(String name) {
        return delegate.getString(getPrefixedName(name)); ////
    }

    @Override
    public final String getString(String name, String defaultValue) {
        return delegate.getString(getPrefixedName(name), defaultValue); ////
    }

    @Override
    public final String getOrInsertString(String name, String defaultValue) {
        return delegate.getOrInsertString(getPrefixedName(name), defaultValue); ////
    }

    @Override
    public final void putString(String name, String value) {
        delegate.putString(getPrefixedName(name), value); ////
    }

    @Override
    public final String[] getStringArray(String name) {
        return delegate.getStringArray(getPrefixedName(name)); ////
    }

    @Override
    public final String[] getStringArray(String name, String[] defaultValue) {
        return delegate.getStringArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final String[] getOrInsertStringArray(String name, String[] defaultValue) {
        return delegate.getOrInsertStringArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putStringArray(String name, String[] value) {
        delegate.putStringArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToStringArray(String name, String value) {
        delegate.appendToStringArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToStringArray(String name, String value, boolean unique) {
        delegate.appendToStringArray(getPrefixedName(name), value, unique);////
    }

    @Override
    public Boolean getBoolean(String name) {
        return delegate.getBoolean(getPrefixedName(name));////
    }

    @Override
    public final Boolean getBoolean(String name, Boolean defaultValue) {
        return delegate.getBoolean(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Boolean getOrInsertBoolean(String name, Boolean defaultValue) {
        return delegate.getOrInsertBoolean(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putBoolean(String name, Boolean value) {
        delegate.putBoolean(getPrefixedName(name), value);////
    }

    @Override
    public final Boolean[] getBooleanArray(String name) {
        return delegate.getBooleanArray(getPrefixedName(name));////
    }

    @Override
    public final Boolean[] getBooleanArray(String name, Boolean[] defaultValue) {
        return delegate.getBooleanArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Boolean[] getOrInsertBooleanArray(String name, Boolean[] defaultValue) {
        return delegate.getOrInsertBooleanArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putBooleanArray(String name, Boolean[] value) {
        delegate.putBooleanArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToBooleanArray(String name, Boolean value) {
        delegate.appendToBooleanArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToBooleanArray(String name, Boolean value, boolean unique) {
        delegate.appendToBooleanArray(getPrefixedName(name), value, unique);////
    }

    @Override
    public Integer getInteger(String name) {
        return delegate.getInteger(getPrefixedName(name));////
    }

    @Override
    public final Integer getInteger(String name, Integer defaultValue) {
        return delegate.getInteger(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Integer getOrInsertInteger(String name, Integer defaultValue) {
        return delegate.getOrInsertInteger(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putInteger(String name, Integer value) {
        delegate.putInteger(getPrefixedName(name), value);////
    }

    @Override
    public final Integer[] getIntegerArray(String name) {
        return delegate.getIntegerArray(getPrefixedName(name));////
    }

    @Override
    public final Integer[] getIntegerArray(String name, Integer[] defaultValue) {
        return delegate.getIntegerArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Integer[] getOrInsertIntegerArray(String name, Integer[] defaultValue) {
        return delegate.getOrInsertIntegerArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putIntegerArray(String name, Integer[] value) {
        delegate.putIntegerArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToIntegerArray(String name, Integer value) {
        delegate.appendToIntegerArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToIntegerArray(String name, Integer value, boolean unique) {
        delegate.appendToIntegerArray(getPrefixedName(name), value, unique);////
    }

    @Override
    public Long getLong(String name) {
        return delegate.getLong(getPrefixedName(name));////
    }

    @Override
    public final Long getLong(String name, Long defaultValue) {
        return delegate.getLong(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Long getOrInsertLong(String name, Long defaultValue) {
        return delegate.getOrInsertLong(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putLong(String name, Long value) {
        delegate.putLong(getPrefixedName(name), value);////
    }

    @Override
    public final Long[] getLongArray(String name) {
        return delegate.getLongArray(getPrefixedName(name));////
    }

    @Override
    public final Long[] getLongArray(String name, Long[] defaultValue) {
        return delegate.getLongArray(getPrefixedName(name), defaultValue); //////
    }

    @Override
    public final Long[] getOrInsertLongArray(String name, Long[] defaultValue) {
        return delegate.getOrInsertLongArray(getPrefixedName(name), defaultValue); /////
    }

    @Override
    public final void putLongArray(String name, Long[] value) {
        delegate.putLongArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToLongArray(String name, Long value) {
        delegate.appendToLongArray(getPrefixedName(name), value);/////
    }

    @Override
    public final void appendToLongArray(String name, Long value, boolean unique) {
        delegate.appendToLongArray(getPrefixedName(name), value, unique);////
    }

    @Override
    public Double getDouble(String name) {
        return delegate.getDouble(getPrefixedName(name));////
    }

    @Override
    public final Double getDouble(String name, double defaultValue) {
        return delegate.getDouble(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Double getOrInsertDouble(String name, double defaultValue) {
        return delegate.getOrInsertDouble(getPrefixedName(name), defaultValue); ////
    }

    @Override
    public final void putDouble(String name, Double value) {
        delegate.putDouble(getPrefixedName(name), value);////
    }

    @Override
    public final Double[] getDoubleArray(String name) {
        return delegate.getDoubleArray(getPrefixedName(name));////
    }

    @Override
    public final Double[] getDoubleArray(String name, Double[] defaultValue) {
        return delegate.getDoubleArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final Double[] getOrInsertDoubleArray(String name, Double[] defaultValue) {
        return delegate.getOrInsertDoubleArray(getPrefixedName(name), defaultValue);////
    }

    @Override
    public final void putDoubleArray(String name, Double[] value) {
        delegate.putDoubleArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToDoubleArray(String name, Double value) {
        delegate.appendToDoubleArray(getPrefixedName(name), value);////
    }

    @Override
    public final void appendToDoubleArray(String name, Double value, boolean unique) {
        delegate.appendToDoubleArray(getPrefixedName(name), value, unique);////
    }

    @Override
    public Object getObject(String name) {
        return delegate.getObject(getPrefixedName(name));
    }

    @Override
    public Object getObject(String name, Object defaultValue) {
        return delegate.getObject(getPrefixedName(name), defaultValue);
    }

    @Override
    public Object getOrInsertObject(String name, Object defaultValue) {
        return delegate.getOrInsertObject(getPrefixedName(name), defaultValue);
    }

    @Override
    public void putObject(String name, Object value) {
        delegate.putObject(getPrefixedName(name), value);
    }

    @Override
    public Object[] getObjectArray(String name) {
        return delegate.getObjectArray(getPrefixedName(name));
    }

    @Override
    public Object[] getObjectArray(String name, Object[] defaultValue) {
        return delegate.getObjectArray(getPrefixedName(name), defaultValue);
    }

    @Override
    public Object[] getOrInsertObjectArray(String name, Object[] defaultValue) {
        return delegate.getOrInsertObjectArray(getPrefixedName(name), defaultValue);
    }

    @Override
    public void putObjectArray(String name, Object[] value) {
        delegate.putObjectArray(getPrefixedName(name), value);
    }

    @Override
    public void appendToObjectArray(String name, Object value) {
        delegate.appendToObjectArray(getPrefixedName(name), value);
    }

    @Override
    public void appendToObjectArray(String name, Object value, boolean unique) {
        delegate.appendToObjectArray(getPrefixedName(name), value, unique);
    }

    @Override
    public <T> T getObject(String name, Class<T> clazz) {
        return delegate.getObject(getPrefixedName(name), clazz);
    }

    @Override
    public <T> T getObject(String name, Class<T> clazz, T defaultValue) {
        return delegate.getObject(getPrefixedName(name), clazz, defaultValue);
    }

    @Override
    public <T> T getOrInsertObject(String name, Class<T> clazz, T defaultValue) {
        return delegate.getOrInsertObject(getPrefixedName(name), clazz, defaultValue);
    }

    @Override
    public <T> T[] getObjectArray(String name, Class<T> clazz) {
        return delegate.getObjectArray(getPrefixedName(name), clazz);
    }

    @Override
    public <T> T[] getObjectArray(String name, Class<T> clazz, T[] defaultValue) {
        return delegate.getObjectArray(getPrefixedName(name), clazz, defaultValue);
    }

    @Override
    public <T> T[] getOrInsertObjectArray(String name, Class<T> clazz, T[] defaultValue) {
        return delegate.getOrInsertObjectArray(getPrefixedName(name), clazz, defaultValue);
    }

    /**
     * Special DataWrapper implementation that deals directly with a
     * DataStoreConnector, rather than with a delegate DataWrapper
     */
    static class ConnectorWrapper implements DataWrapper {

        private final DataStoreConnector connector;

        ConnectorWrapper(DataStoreConnector connector) {
            this.connector = connector;
        }

        @Override
        public DataStoreConnector getConnector() {
            return connector;
        }

        @Override
        public String getPrefix() {
            return null;
        }

        @Override
        public boolean contains(String name) {
            return doGet(name, Object.class) != null;
        }

        @Override
        public void commit() throws DataWrapperException {
            connector.commit();
        }

        @Override
        public void remove(String name) {
            doRemove(name);
        }

        private <T> T doGet(String name, Class<T> clazz) {
            return connector.get(name, clazz);
        }

        private void doPut(String name, Object value) {
            connector.put(name, value);
        }

        private void doRemove(String name) {
            connector.remove(name);
        }

        @Override
        public String getString(String name) {
            return doGet(name, String.class);
        }

        @Override
        public final String getString(String name, String defaultValue) {
            return doGetString(name, defaultValue, false);
        }

        @Override
        public final String getOrInsertString(String name, String defaultValue) {
            return doGetString(name, defaultValue, true);
        }

        private String doGetString(String name, String defaultValue, boolean insert) {
            if (contains(name)) {
                return getString(name);
            }

            if (insert) {
                putString(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public final void putString(String name, String value) {
            doPut(name, value);
        }

        @Override
        public final String[] getStringArray(String name) {
            return doGet(name, String[].class);
        }

        @Override
        public final String[] getStringArray(String name, String[] defaultValue) {
            return doGetStringArray(name, defaultValue, false);
        }

        @Override
        public final String[] getOrInsertStringArray(String name, String[] defaultValue) {
            return doGetStringArray(name, defaultValue, true);
        }

        private String[] doGetStringArray(String name, String[] defaultValue, boolean insert) {
            if (contains(name)) {
                return getStringArray(name);
            }

            if (insert) {
                putStringArray(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public final void putStringArray(String name, String[] value) {
            doPut(name, value);
        }

        @Override
        public final void appendToStringArray(String name, String value) {
            appendToStringArray(name, value, false);
        }

        @Override
        public final void appendToStringArray(String name, String value, boolean unique) {
            String[] array = getStringArray(name);

            if (array == null) {
                array = new String[0];
            }

            if (unique && Arrays.asList(array).contains(value)) {
                return;
            }

            String[] newArray = new String[array.length + 1];

            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;

            putStringArray(name, newArray);
        }

        @Override
        public Boolean getBoolean(String name) {
            return doGet(name, Boolean.class);
        }

        @Override
        public final Boolean getBoolean(String name, Boolean defaultValue) {
            return doGetBoolean(name, defaultValue, false);
        }

        @Override
        public final Boolean getOrInsertBoolean(String name, Boolean defaultValue) {
            return doGetBoolean(name, defaultValue, true);
        }

        private Boolean doGetBoolean(String name, Boolean defaultValue, boolean insert) {
            if (contains(name)) {
                return getBoolean(name);
            }

            if (insert) {
                putBoolean(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public final void putBoolean(String name, Boolean value) {
            doPut(name, value);
        }

        @Override
        public final Boolean[] getBooleanArray(String name) {
            return doGet(name, Boolean[].class);
        }

        @Override
        public final Boolean[] getBooleanArray(String name, Boolean[] defaultValue) {
            if (contains(name)) {
                return getBooleanArray(name);
            }

            return defaultValue;
        }

        @Override
        public final Boolean[] getOrInsertBooleanArray(String name, Boolean[] defaultValue) {
            if (contains(name)) {
                return getBooleanArray(name);
            }

            putBooleanArray(name, defaultValue);
            return getBooleanArray(name);
        }

        @Override
        public final void putBooleanArray(String name, Boolean[] value) {
            doPut(name, value);
        }

        @Override
        public final void appendToBooleanArray(String name, Boolean value) {
            appendToBooleanArray(name, value, false);
        }

        @Override
        public final void appendToBooleanArray(String name, Boolean value, boolean unique) {
            Boolean[] array = getBooleanArray(name);

            if (array == null) {
                array = new Boolean[0];
            }

            if (unique && Arrays.asList(array).contains(value)) {
                return;
            }

            Boolean[] newArray = new Boolean[array.length + 1];

            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;

            putBooleanArray(name, newArray);
        }

        @Override
        public Integer getInteger(String name) {
            return doGet(name, Number.class).intValue();
        }

        @Override
        public final Integer getInteger(String name, Integer defaultValue) {
            return doGetInteger(name, defaultValue, false);
        }

        @Override
        public final Integer getOrInsertInteger(String name, Integer defaultValue) {
            return doGetInteger(name, defaultValue, true);
        }

        private Integer doGetInteger(String name, Integer defaultValue, boolean insert) {
            if (contains(name)) {
                return getInteger(name);
            }

            if (insert) {
                putInteger(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public final void putInteger(String name, Integer value) {
            doPut(name, value);
        }

        @Override
        public final Integer[] getIntegerArray(String name) {
            Number[] numbers = doGet(name, Number[].class);

            Integer[] ints = new Integer[numbers.length];

            for (int i = 0; i < numbers.length; i++) {
                ints[i] = numbers[i].intValue();
            }

            return ints;
        }

        @Override
        public final Integer[] getIntegerArray(String name, Integer[] defaultValue) {
            if (contains(name)) {
                return getIntegerArray(name);
            }

            return defaultValue;
        }

        @Override
        public final Integer[] getOrInsertIntegerArray(String name, Integer[] defaultValue) {
            if (contains(name)) {
                return getIntegerArray(name);
            }

            putIntegerArray(name, defaultValue);
            return getIntegerArray(name);
        }

        @Override
        public final void putIntegerArray(String name, Integer[] value) {
            doPut(name, value);
        }

        @Override
        public final void appendToIntegerArray(String name, Integer value) {
            appendToIntegerArray(name, value, false);
        }

        @Override
        public final void appendToIntegerArray(String name, Integer value, boolean unique) {
            Integer[] array = getIntegerArray(name);

            if (array == null) {
                array = new Integer[0];
            }

            if (unique && Arrays.asList(array).contains(value)) {
                return;
            }

            Integer[] newArray = new Integer[array.length + 1];

            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;

            putIntegerArray(name, newArray);
        }

        @Override
        public Long getLong(String name) {
            return doGet(name, Number.class).longValue();
        }

        @Override
        public final Long getLong(String name, Long defaultValue) {
            return doGetLong(name, defaultValue, false);
        }

        @Override
        public final Long getOrInsertLong(String name, Long defaultValue) {
            return doGetLong(name, defaultValue, true);
        }

        private Long doGetLong(String name, Long defaultValue, boolean insert) {
            if (contains(name)) {
                return getLong(name);
            }

            if (insert) {
                putLong(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public final void putLong(String name, Long value) {
            doPut(name, value);
        }

        @Override
        public final Long[] getLongArray(String name) {
            Number[] numbers = doGet(name, Number[].class);

            Long[] longs = new Long[numbers.length];

            for (int i = 0; i < numbers.length; i++) {
                longs[i] = numbers[i].longValue();
            }

            return longs;
        }

        @Override
        public final Long[] getLongArray(String name, Long[] defaultValue) {
            if (contains(name)) {
                return getLongArray(name);
            }

            return defaultValue;
        }

        @Override
        public final Long[] getOrInsertLongArray(String name, Long[] defaultValue) {
            if (contains(name)) {
                return getLongArray(name);
            }

            putLongArray(name, defaultValue);
            return getLongArray(name);
        }

        @Override
        public final void putLongArray(String name, Long[] value) {
            doPut(name, value);
        }

        @Override
        public final void appendToLongArray(String name, Long value) {
            appendToLongArray(name, value, false);
        }

        @Override
        public final void appendToLongArray(String name, Long value, boolean unique) {
            Long[] array = getLongArray(name);

            if (array == null) {
                array = new Long[0];
            }

            if (unique && Arrays.asList(array).contains(value)) {
                return;
            }

            Long[] newArray = new Long[array.length + 1];

            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;

            putLongArray(name, newArray);
        }

        @Override
        public Double getDouble(String name) {
            return doGet(name, Number.class).doubleValue();
        }

        @Override
        public final Double getDouble(String name, double defaultValue) {
            return doGetDouble(name, defaultValue, false);
        }

        @Override
        public final Double getOrInsertDouble(String name, double defaultValue) {
            return doGetDouble(name, defaultValue, true);
        }

        private Double doGetDouble(String name, double defaultValue, boolean insert) {
            if (contains(name)) {
                return getDouble(name);
            }

            if (insert) {
                putDouble(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public final void putDouble(String name, Double value) {
            doPut(name, value);
        }

        @Override
        public final Double[] getDoubleArray(String name) {
            Number[] numbers = doGet(name, Number[].class);

            Double[] doubles = new Double[numbers.length];

            for (int i = 0; i < numbers.length; i++) {
                doubles[i] = numbers[i].doubleValue();
            }

            return doubles;
        }

        @Override
        public final Double[] getDoubleArray(String name, Double[] defaultValue) {
            if (contains(name)) {
                return getDoubleArray(name);
            }

            return defaultValue;
        }

        @Override
        public final Double[] getOrInsertDoubleArray(String name, Double[] defaultValue) {
            if (contains(name)) {
                return getDoubleArray(name);
            }

            putDoubleArray(name, defaultValue);
            return getDoubleArray(name);
        }

        @Override
        public final void putDoubleArray(String name, Double[] value) {
            doPut(name, value);
        }

        @Override
        public final void appendToDoubleArray(String name, Double value) {
            appendToDoubleArray(name, value, false);
        }

        @Override
        public final void appendToDoubleArray(String name, Double value, boolean unique) {
            Double[] array = getDoubleArray(name);

            if (array == null) {
                array = new Double[0];
            }

            if (unique && Arrays.asList(array).contains(value)) {
                return;
            }

            Double[] newArray = new Double[array.length + 1];

            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;

            putDoubleArray(name, newArray);
        }

        @Override
        public Object getObject(String name) {
            return connector.get(name);
        }

        @Override
        public Object getObject(String name, Object defaultValue) {
            return doGetObject(name, defaultValue, false);
        }

        @Override
        public Object getOrInsertObject(String name, Object defaultValue) {
            return doGetObject(name, defaultValue, true);
        }

        private Object doGetObject(String name, Object defaultValue, boolean insert) {
            if (contains(name)) {
                return getObject(name);
            }

            if (insert) {
                putObject(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public void putObject(String name, Object value) {
            doPut(name, value);
        }

        @Override
        public Object[] getObjectArray(String name) {
            return doGet(name, Object[].class);
        }

        @Override
        public Object[] getObjectArray(String name, Object[] defaultValue) {
            return doGetObjectArray(name, defaultValue, false);
        }

        @Override
        public Object[] getOrInsertObjectArray(String name, Object[] defaultValue) {
            return doGetObjectArray(name, defaultValue, true);
        }

        private Object[] doGetObjectArray(String name, Object[] defaultValue, boolean insert) {
            if (contains(name)) {
                return getObjectArray(name);
            }

            if (insert) {
                putObjectArray(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public void putObjectArray(String name, Object[] value) {
            doPut(name, value);
        }

        @Override
        public void appendToObjectArray(String name, Object value) {
            appendToObjectArray(name, value, false);
        }

        @Override
        public void appendToObjectArray(String name, Object value, boolean unique) {
            Object[] array = getObjectArray(name);

            if (array == null) {
                array = new Object[0];
            }

            if (unique && Arrays.asList(array).contains(value)) {
                return;
            }

            Object[] newArray = new Object[array.length + 1];

            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[array.length] = value;

            putObjectArray(name, newArray);
        }

        @Override
        public <T> T getObject(String name, Class<T> clazz) {
            return doGet(name, clazz);
        }

        @Override
        public <T> T getObject(String name, Class<T> clazz, T defaultValue) {
            return doGetObject(name, clazz, defaultValue, false);
        }

        @Override
        public <T> T getOrInsertObject(String name, Class<T> clazz, T defaultValue) {
            return doGetObject(name, clazz, defaultValue, true);
        }

        private <T> T doGetObject(String name, Class<T> clazz, T defaultValue, boolean insert) {
            if (contains(name)) {
                return getObject(name, clazz);
            }

            if (insert) {
                putObject(name, defaultValue);
            }

            return defaultValue;
        }

        @Override
        public <T> T[] getObjectArray(String name, Class<T> clazz) {
            Object[] objectArray = getObjectArray(name);

            T[] newArray = (T[]) Array.newInstance(clazz, objectArray.length);

            System.arraycopy(objectArray, 0, newArray, 0, objectArray.length);

            return newArray;
        }

        @Override
        public <T> T[] getObjectArray(String name, Class<T> clazz, T[] defaultValue) {
            return doGetObjectArray(name, clazz, defaultValue, false);
        }

        @Override
        public <T> T[] getOrInsertObjectArray(String name, Class<T> clazz, T[] defaultValue) {
            return doGetObjectArray(name, clazz, defaultValue, true);
        }

        private <T> T[] doGetObjectArray(String name, Class<T> clazz, T[] defaultValue, boolean insert) {
            if (contains(name)) {
                return getObjectArray(name, clazz);
            }

            if (insert) {
                putObjectArray(name, defaultValue);
            }

            return defaultValue;
        }
    }
}
