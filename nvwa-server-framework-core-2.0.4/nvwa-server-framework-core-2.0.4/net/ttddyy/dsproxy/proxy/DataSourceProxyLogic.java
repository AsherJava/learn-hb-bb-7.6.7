/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.Method;
import java.sql.Connection;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ConnectionIdManager;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;

public class DataSourceProxyLogic
extends ProxyLogicSupport {
    private DataSource dataSource;
    private ProxyConfig proxyConfig;

    public DataSourceProxyLogic(DataSource dataSource, ProxyConfig proxyConfig) {
        this.dataSource = dataSource;
        this.proxyConfig = proxyConfig;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.proceedMethodExecution(this.proxyConfig, this.dataSource, null, proxy, method, args);
    }

    @Override
    protected Object performProxyLogic(Object proxy, Method method, Object[] args, MethodExecutionContext methodContext) throws Throwable {
        JdbcProxyFactory jdbcProxyFactory = this.proxyConfig.getJdbcProxyFactory();
        ConnectionIdManager connectionIdManager = this.proxyConfig.getConnectionIdManager();
        String methodName = method.getName();
        if (this.isCommonMethod(methodName)) {
            return this.handleCommonMethod(methodName, this.dataSource, this.proxyConfig, args);
        }
        Object retVal = this.proceedExecution(method, this.dataSource, args);
        if ("getConnection".equals(methodName)) {
            Connection conn = (Connection)retVal;
            String connId = connectionIdManager.getId(conn);
            ConnectionInfo connectionInfo = new ConnectionInfo();
            connectionInfo.setConnectionId(connId);
            connectionInfo.setDataSourceName(this.proxyConfig.getDataSourceName());
            if (this.proxyConfig.isRetrieveIsolationLevel()) {
                connectionInfo.setIsolationLevel(conn.getTransactionIsolation());
            }
            methodContext.setConnectionInfo(connectionInfo);
            return jdbcProxyFactory.createConnection((Connection)retVal, connectionInfo, this.proxyConfig);
        }
        return retVal;
    }
}

