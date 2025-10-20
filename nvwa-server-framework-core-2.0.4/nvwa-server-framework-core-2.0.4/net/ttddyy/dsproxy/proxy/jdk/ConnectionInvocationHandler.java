/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.proxy.ConnectionProxyLogic;
import net.ttddyy.dsproxy.proxy.ProxyConfig;

public class ConnectionInvocationHandler
implements InvocationHandler {
    private ConnectionProxyLogic delegate;

    public ConnectionInvocationHandler(Connection connection, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        this.delegate = new ConnectionProxyLogic(connection, connectionInfo, proxyConfig);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.delegate.invoke(proxy, method, args);
    }
}

