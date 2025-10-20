/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.proxy.DataSourceProxyLogic;
import net.ttddyy.dsproxy.proxy.ProxyConfig;

public class DataSourceInvocationHandler
implements InvocationHandler {
    private DataSourceProxyLogic delegate;

    public DataSourceInvocationHandler(DataSource dataSource, ProxyConfig proxyConfig) {
        this.delegate = new DataSourceProxyLogic(dataSource, proxyConfig);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.delegate.invoke(proxy, method, args);
    }
}

