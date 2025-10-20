/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.nodekeeper;

import com.jiuqi.bi.core.nodekeeper.IConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;

public class GlobalConnectionProviderManager {
    private static IConnectionProvider connectionProvider;

    public static void setConnectionProvider(IConnectionProvider provider) {
        connectionProvider = provider;
    }

    public static IConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    public static Connection getConnection() throws SQLException {
        return connectionProvider.openConnection();
    }

    public static Connection getHostedConnection() throws SQLException {
        return connectionProvider.openHostedConnection();
    }
}

