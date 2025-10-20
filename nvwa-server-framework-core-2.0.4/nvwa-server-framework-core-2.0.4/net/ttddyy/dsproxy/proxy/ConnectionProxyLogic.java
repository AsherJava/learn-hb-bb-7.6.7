/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.proxy.GeneratedKeysUtils;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ObjectArrayUtils;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;

public class ConnectionProxyLogic
extends ProxyLogicSupport {
    private final Connection connection;
    private final ConnectionInfo connectionInfo;
    private final ProxyConfig proxyConfig;

    public ConnectionProxyLogic(Connection connection, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        this.connection = connection;
        this.connectionInfo = connectionInfo;
        this.proxyConfig = proxyConfig;
    }

    public Object invoke(Object proxyConnection, Method method, Object[] args) throws Throwable {
        return this.proceedMethodExecution(this.proxyConfig, this.connection, this.connectionInfo, proxyConnection, method, args);
    }

    @Override
    protected Object performProxyLogic(Object proxy, Method method, Object[] args, MethodExecutionContext methodContext) throws Throwable {
        String query;
        Connection proxyConnection = (Connection)proxy;
        String methodName = method.getName();
        JdbcProxyFactory jdbcProxyFactory = this.proxyConfig.getJdbcProxyFactory();
        if (this.isCommonMethod(methodName)) {
            return this.handleCommonMethod(methodName, this.connection, this.proxyConfig, args);
        }
        if ("setTransactionIsolation".equals(methodName)) {
            this.connectionInfo.setIsolationLevel((Integer)args[0]);
        }
        boolean isCloseMethod = "close".equals(method.getName());
        boolean isCommitMethod = "commit".equals(method.getName());
        boolean isRollbackMethod = "rollback".equals(method.getName());
        Object retVal = this.proceedExecution(method, this.connection, args);
        ConnectionInfo connectionInfo = this.connectionInfo;
        if (isCommitMethod) {
            connectionInfo.incrementCommitCount();
        } else if (isRollbackMethod) {
            connectionInfo.incrementRollbackCount();
        } else if (isCloseMethod) {
            connectionInfo.setClosed(true);
            String connId = connectionInfo.getConnectionId();
            this.proxyConfig.getConnectionIdManager().addClosedId(connId);
        }
        if ("createStatement".equals(methodName)) {
            return jdbcProxyFactory.createStatement((Statement)retVal, this.connectionInfo, proxyConnection, this.proxyConfig);
        }
        if ("prepareStatement".equals(methodName)) {
            if (ObjectArrayUtils.isFirstArgString(args)) {
                query = (String)args[0];
                boolean generateKey = GeneratedKeysUtils.isAutoGenerateEnabledParameters(args);
                return jdbcProxyFactory.createPreparedStatement((PreparedStatement)retVal, query, this.connectionInfo, proxyConnection, this.proxyConfig, generateKey);
            }
        } else if ("prepareCall".equals(methodName) && ObjectArrayUtils.isFirstArgString(args)) {
            query = (String)args[0];
            return jdbcProxyFactory.createCallableStatement((CallableStatement)retVal, query, this.connectionInfo, proxyConnection, this.proxyConfig);
        }
        return retVal;
    }
}

