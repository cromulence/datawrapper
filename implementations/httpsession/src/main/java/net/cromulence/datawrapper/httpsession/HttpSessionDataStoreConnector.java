package net.cromulence.datawrapper.httpsession;

import net.cromulence.datawrapper.AbstractDataStoreConnector;

import javax.servlet.http.HttpSession;

/**
 * A DataStoreConnector for storing values inside an HttpSession
 */
public class HttpSessionDataStoreConnector  extends AbstractDataStoreConnector {
    private final HttpSession session;

    public HttpSessionDataStoreConnector(HttpSession session) {
        this.session = session;
    }

    @Override
    public Object get(String name) {
        return session.getAttribute(name);
    }

    @Override
    public void put(String name, Object value)
    {
        session.setAttribute(name, value);
    }

    @Override
    public void remove(String name)
    {
        session.removeAttribute(name);
    }
}
