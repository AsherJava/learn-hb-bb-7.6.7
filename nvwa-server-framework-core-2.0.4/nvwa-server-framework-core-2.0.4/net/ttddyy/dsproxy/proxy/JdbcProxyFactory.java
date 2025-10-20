/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.jdk.JdkJdbcProxyFactory;

public interface JdbcProxyFactory {
    public static final JdbcProxyFactory DEFAULT = new JdkJdbcProxyFactory();

    public DataSource createDataSource(DataSource var1, ProxyConfig var2);

    public Connection createConnection(Connection var1, ConnectionInfo var2, ProxyConfig var3);

    public Statement createStatement(Statement var1, ConnectionInfo var2, Connection var3, ProxyConfig var4);

    public PreparedStatement createPreparedStatement(PreparedStatement var1, String var2, ConnectionInfo var3, Connection var4, ProxyConfig var5, boolean var6);

    public CallableStatement createCallableStatement(CallableStatement var1, String var2, ConnectionInfo var3, Connection var4, ProxyConfig var5);

    public ResultSet createResultSet(ResultSet var1, ConnectionInfo var2, ProxyConfig var3);

    public ResultSet createGeneratedKeys(ResultSet var1, ConnectionInfo var2, ProxyConfig var3);
}

