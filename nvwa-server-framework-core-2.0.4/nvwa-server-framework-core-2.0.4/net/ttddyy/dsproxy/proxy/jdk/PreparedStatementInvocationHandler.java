/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.StatementType;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.StatementProxyLogic;

public class PreparedStatementInvocationHandler
implements InvocationHandler {
    private StatementProxyLogic delegate;

    public PreparedStatementInvocationHandler(PreparedStatement ps, String query, ConnectionInfo connectionInfo, Connection proxyConnection, ProxyConfig proxyConfig, boolean generateKey) {
        this.delegate = StatementProxyLogic.Builder.create().statement(ps, StatementType.PREPARED).query(query).connectionInfo(connectionInfo).proxyConnection(proxyConnection).proxyConfig(proxyConfig).generateKey(generateKey).build();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.delegate.invoke(proxy, method, args);
    }
}

