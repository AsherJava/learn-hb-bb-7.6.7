/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyJdbcObject;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogicFactory;
import net.ttddyy.dsproxy.proxy.jdk.CallableStatementInvocationHandler;
import net.ttddyy.dsproxy.proxy.jdk.ConnectionInvocationHandler;
import net.ttddyy.dsproxy.proxy.jdk.DataSourceInvocationHandler;
import net.ttddyy.dsproxy.proxy.jdk.PreparedStatementInvocationHandler;
import net.ttddyy.dsproxy.proxy.jdk.ResultSetInvocationHandler;
import net.ttddyy.dsproxy.proxy.jdk.StatementInvocationHandler;

public class JdkJdbcProxyFactory
implements JdbcProxyFactory {
    @Override
    public DataSource createDataSource(DataSource dataSource, ProxyConfig proxyConfig) {
        return (DataSource)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, DataSource.class}, (InvocationHandler)new DataSourceInvocationHandler(dataSource, proxyConfig));
    }

    @Override
    public Connection createConnection(Connection connection, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        return (Connection)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, Connection.class}, (InvocationHandler)new ConnectionInvocationHandler(connection, connectionInfo, proxyConfig));
    }

    @Override
    public Statement createStatement(Statement statement, ConnectionInfo connectionInfo, Connection proxyConnection, ProxyConfig proxyConfig) {
        return (Statement)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, Statement.class}, (InvocationHandler)new StatementInvocationHandler(statement, connectionInfo, proxyConnection, proxyConfig));
    }

    @Override
    public PreparedStatement createPreparedStatement(PreparedStatement preparedStatement, String query, ConnectionInfo connectionInfo, Connection proxyConnection, ProxyConfig proxyConfig, boolean generateKey) {
        return (PreparedStatement)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, PreparedStatement.class}, (InvocationHandler)new PreparedStatementInvocationHandler(preparedStatement, query, connectionInfo, proxyConnection, proxyConfig, generateKey));
    }

    @Override
    public CallableStatement createCallableStatement(CallableStatement callableStatement, String query, ConnectionInfo connectionInfo, Connection proxyConnection, ProxyConfig proxyConfig) {
        return (CallableStatement)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, CallableStatement.class}, (InvocationHandler)new CallableStatementInvocationHandler(callableStatement, query, connectionInfo, proxyConnection, proxyConfig));
    }

    @Override
    public ResultSet createResultSet(ResultSet resultSet, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        ResultSetProxyLogicFactory factory = proxyConfig.getResultSetProxyLogicFactory();
        return (ResultSet)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, ResultSet.class}, (InvocationHandler)new ResultSetInvocationHandler(factory, resultSet, connectionInfo, proxyConfig));
    }

    @Override
    public ResultSet createGeneratedKeys(ResultSet resultSet, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        ResultSetProxyLogicFactory factory = proxyConfig.getGeneratedKeysProxyLogicFactory();
        return (ResultSet)Proxy.newProxyInstance(ProxyJdbcObject.class.getClassLoader(), new Class[]{ProxyJdbcObject.class, ResultSet.class}, (InvocationHandler)new ResultSetInvocationHandler(factory, resultSet, connectionInfo, proxyConfig));
    }
}

