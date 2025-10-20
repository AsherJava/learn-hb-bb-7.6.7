/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogic;

public class CachedRowSetResultSetProxyLogic
extends ProxyLogicSupport
implements ResultSetProxyLogic {
    private ResultSet resultSet;
    private ResultSet cachedRowSet;
    private ConnectionInfo connectionInfo;
    private ProxyConfig proxyConfig;
    protected boolean supportIsClosedMethod = true;
    protected boolean isClosed;

    public CachedRowSetResultSetProxyLogic(ResultSet resultSet, ResultSet cachedRowSet, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        this.resultSet = resultSet;
        this.cachedRowSet = cachedRowSet;
        this.connectionInfo = connectionInfo;
        this.proxyConfig = proxyConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.proceedMethodExecution(this.proxyConfig, this.resultSet, this.connectionInfo, proxy, method, args);
    }

    @Override
    protected Object performProxyLogic(Object proxy, Method method, Object[] args, MethodExecutionContext methodContext) throws Throwable {
        String methodName = method.getName();
        if (this.isCommonMethod(methodName)) {
            return this.handleCommonMethod(methodName, this.resultSet, this.proxyConfig, args);
        }
        if ("close".equals(methodName)) {
            this.isClosed = true;
        } else if ("getTarget".equals(methodName)) {
            return this.resultSet;
        }
        if (this.supportIsClosedMethod && "isClosed".equals(methodName)) {
            return this.isClosed;
        }
        try {
            return this.proceedExecution(method, this.cachedRowSet, args);
        }
        catch (Throwable throwable) {
            if (throwable instanceof SQLException) {
                throw throwable;
            }
            String reason = String.format("CachedRowSet threw exception: %s", throwable);
            throw new SQLException(reason, throwable);
        }
    }
}

