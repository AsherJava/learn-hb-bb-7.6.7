/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionWapper {
    public Connection getConn() throws SQLException;

    public String getJndi();

    public String getSchema();
}

