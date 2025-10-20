/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection getConnection() throws SQLException;
}

