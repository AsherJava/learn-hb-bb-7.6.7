/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.support;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ConnectionIdManager;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.DataSourceProxyException;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;

public class ProxyDataSource
extends ProxyLogicSupport
implements DataSource,
Closeable {
    private static final Method GET_CONNECTION_WITH_NO_ARGS;
    private static final Method GET_CONNECTION_WITH_USER_PASS;
    private static final boolean isAutoCloseablePresent;
    private DataSource dataSource;
    private ProxyConfig proxyConfig = ProxyConfig.Builder.create().build();

    private static boolean isAutoCloseablePresent() {
        try {
            Class.forName("java.lang.AutoCloseable");
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
        return true;
    }

    public ProxyDataSource() {
    }

    public ProxyDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.dataSource.getLogWriter();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getConnectionProxy(GET_CONNECTION_WITH_NO_ARGS, null);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.getConnectionProxy(GET_CONNECTION_WITH_USER_PASS, new Object[]{username, password});
    }

    private Connection getConnectionProxy(Method method, Object[] args) throws SQLException {
        try {
            return (Connection)this.proceedMethodExecution(this.proxyConfig, this.dataSource, null, null, method, args);
        }
        catch (Throwable throwable) {
            if (throwable instanceof SQLException) {
                throw (SQLException)throwable;
            }
            throw new DataSourceProxyException("Failed to perform getConnection", throwable);
        }
    }

    @Override
    protected Object performProxyLogic(Object proxy, Method method, Object[] args, MethodExecutionContext methodContext) throws Throwable {
        String dataSourceName = this.proxyConfig.getDataSourceName();
        ConnectionIdManager connectionIdManager = this.proxyConfig.getConnectionIdManager();
        JdbcProxyFactory jdbcProxyFactory = this.proxyConfig.getJdbcProxyFactory();
        Connection connection = (Connection)this.proceedExecution(method, this.dataSource, args);
        String connectionId = connectionIdManager.getId(connection);
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setConnectionId(connectionId);
        connectionInfo.setDataSourceName(dataSourceName);
        if (this.proxyConfig.isRetrieveIsolationLevel()) {
            connectionInfo.setIsolationLevel(connection.getTransactionIsolation());
        }
        methodContext.setConnectionInfo(connectionInfo);
        return jdbcProxyFactory.createConnection(connection, connectionInfo, this.proxyConfig);
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {
        this.dataSource.setLogWriter(printWriter);
    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {
        this.dataSource.setLoginTimeout(i);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.dataSource.getLoginTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T)this;
        }
        return this.dataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return true;
        }
        return this.dataSource.isWrapperFor(iface);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.dataSource.getParentLogger();
    }

    @Override
    public void close() throws IOException {
        if (this.dataSource instanceof Closeable) {
            ((Closeable)((Object)this.dataSource)).close();
        } else if (isAutoCloseablePresent && this.dataSource instanceof AutoCloseable) {
            try {
                ((AutoCloseable)((Object)this.dataSource)).close();
            }
            catch (Exception ex) {
                throw new IOException(ex);
            }
        }
    }

    public void setListener(QueryExecutionListener listener) {
        this.proxyConfig = ProxyConfig.Builder.from(this.proxyConfig).queryListener(listener).build();
    }

    public void addListener(QueryExecutionListener listener) {
        this.proxyConfig.getQueryListener().addListener(listener);
    }

    public void setDataSourceName(String dataSourceName) {
        this.proxyConfig = ProxyConfig.Builder.from(this.proxyConfig).dataSourceName(dataSourceName).build();
    }

    public String getDataSourceName() {
        return this.proxyConfig.getDataSourceName();
    }

    public ConnectionIdManager getConnectionIdManager() {
        return this.proxyConfig.getConnectionIdManager();
    }

    public void setConnectionIdManager(ConnectionIdManager connectionIdManager) {
        this.proxyConfig = ProxyConfig.Builder.from(this.proxyConfig).connectionIdManager(connectionIdManager).build();
    }

    public ProxyConfig getProxyConfig() {
        return this.proxyConfig;
    }

    public void setProxyConfig(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    static {
        isAutoCloseablePresent = ProxyDataSource.isAutoCloseablePresent();
        try {
            GET_CONNECTION_WITH_NO_ARGS = DataSource.class.getDeclaredMethod("getConnection", new Class[0]);
            GET_CONNECTION_WITH_USER_PASS = DataSource.class.getDeclaredMethod("getConnection", String.class, String.class);
        }
        catch (NoSuchMethodException e) {
            throw new DataSourceProxyException("Failed to find getConnection methods", e);
        }
    }
}

