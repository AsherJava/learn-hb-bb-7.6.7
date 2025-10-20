/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  org.quartz.utils.ConnectionProvider
 */
package com.jiuqi.bi.core.jobs.core.utils;

import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.quartz.utils.ConnectionProvider;

public class JobConnectionProvider
implements ConnectionProvider {
    public Connection getConnection() throws SQLException {
        return GlobalConnectionProviderManager.getConnection();
    }

    public void shutdown() throws SQLException {
    }

    public void initialize() throws SQLException {
    }
}

