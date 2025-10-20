/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.DataSourceProxyException;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.RepeatableReadResultSetProxyLogic;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogic;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogicFactory;

public class RepeatableReadResultSetProxyLogicFactory
implements ResultSetProxyLogicFactory {
    @Override
    public ResultSetProxyLogic create(ResultSet resultSet, ConnectionInfo connectionInfo, ProxyConfig proxyConfig) {
        Map<String, Integer> columnNameToIndex = this.columnNameToIndex(resultSet);
        return RepeatableReadResultSetProxyLogic.Builder.create().resultSet(resultSet).connectionInfo(connectionInfo).proxyConfig(proxyConfig).columnNameToIndex(columnNameToIndex).columnCount(columnNameToIndex.size()).build();
    }

    private Map<String, Integer> columnNameToIndex(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            HashMap<String, Integer> columnNameToIndex = new HashMap<String, Integer>();
            for (int i = 1; i <= columnCount; ++i) {
                columnNameToIndex.put(metaData.getColumnLabel(i).toUpperCase(), i);
            }
            return columnNameToIndex;
        }
        catch (SQLException e) {
            throw new DataSourceProxyException("Failed to obtain resultset metadata", e);
        }
    }
}

