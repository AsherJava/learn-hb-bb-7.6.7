/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.StatementType;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.StatementProxyLogic;

public class CallableStatementInvocationHandler
implements InvocationHandler {
    private StatementProxyLogic delegate;

    public CallableStatementInvocationHandler(CallableStatement cs, String query, ConnectionInfo connectionInfo, Connection proxyConnection, ProxyConfig proxyConfig) {
        this.delegate = StatementProxyLogic.Builder.create().statement(cs, StatementType.CALLABLE).query(query).connectionInfo(connectionInfo).proxyConnection(proxyConnection).proxyConfig(proxyConfig).build();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.delegate.invoke(proxy, method, args);
    }
}

