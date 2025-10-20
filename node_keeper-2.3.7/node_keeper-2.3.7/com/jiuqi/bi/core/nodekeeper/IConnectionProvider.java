/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.nodekeeper;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection openConnection() throws SQLException;

    public Connection openHostedConnection() throws SQLException;
}

