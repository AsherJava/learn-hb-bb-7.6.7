/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.DataSourceProxyException;
import net.ttddyy.dsproxy.proxy.CachedRowSetResultSetProxyLogic;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogic;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogicFactory;

public class CachedRowSetResultSetProxyLogicFactory
implements ResultSetProxyLogicFactory {
    @Override
    public ResultSetProxyLogic create(ResultSet resultSet, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        ResultSet cachedRowSet = this.getCachedRowSet(resultSet);
        return new CachedRowSetResultSetProxyLogic(resultSet, cachedRowSet, connectionInfo, proxyConfig);
    }

    protected ResultSet getCachedRowSet(ResultSet resultSet) {
        try {
            if (resultSet.getMetaData().getColumnCount() > 0) {
                RowSetFactory rowSetFactory = RowSetProvider.newFactory();
                CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet();
                cachedRowSet.populate(resultSet);
                return cachedRowSet;
            }
            return resultSet;
        }
        catch (SQLException e) {
            throw new DataSourceProxyException("Failed to create CachedRowSet", e);
        }
    }
}

