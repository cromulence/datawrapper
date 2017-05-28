package net.cromulence.datawrapper;

import com.google.gson.Gson;
import org.webpieces.router.impl.params.ObjectTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Decorator class for handling data stores which can only hold Strings (eg properties files)
 */
public abstract class AbstractStringToStringDataStoreConnector extends AbstractDataStoreConnector {

    private final Gson gson = new Gson();

    @Override
    public final <T> T get(String name, Class<T> clazz) {
        Object o = get(name);

        if(o == null) {
            return null;
        }

        if(clazz.isAssignableFrom(o.getClass())) {
            return (T)o;
        }

        throw new ClassCastException(o.getClass().getName() + " cannot be cast to " + clazz.getName());
    }

    @Override
    public final Object get(String name) {
        final String wrappedStringValue = doGet(name);

        if(wrappedStringValue == null || wrappedStringValue.isEmpty()) {
            return null;
        }

        final StringWrappedValue swv;

        try {
            swv = gson.fromJson(wrappedStringValue, StringWrappedValue.class);
        } catch(Throwable t) {
            throw new DataWrapperRuntimeException("Unable to deserialise to " + StringWrappedValue.class.getName() + "\n" + wrappedStringValue, t);
        }

        try {
            final Class<?> aClass = Class.forName(swv.type);

            final Function unmarshaller = ObjectTranslator.getUnmarshaller(aClass);

            if(unmarshaller == null) {
                try {
                    return gson.fromJson(swv.value, aClass);
                } catch(Throwable t) {
                    throw new DataWrapperRuntimeException("Unable to deserialise to " + aClass.getName() + "\n" + swv.value, t);
                }
            } else {
                return unmarshaller.apply(swv.value);
            }
        } catch (ClassNotFoundException e) {
            throw new DataWrapperRuntimeException("Unable to get object of unknown class: " + swv.type, e);
        }
    }

    @Override
    public final void put(String name, Object value)
    {
        final StringWrappedValue swv = new StringWrappedValue();
        swv.type = value.getClass().getName();

        final Function<Object, String> marshaller = ObjectTranslator.getMarshaller(value.getClass());

        if(marshaller == null) {
            swv.value = gson.toJson(value);
        } else {
            swv.value = marshaller.apply(value);
        }

        doPut(name, gson.toJson(swv));
    }

    public abstract String doGet(String name);
    public abstract void doPut(String name, String value);

    private static class StringWrappedValue {
        String type;
        String value;
    }
}
