/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.sql.SQLModel;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection getConnection(SQLModel var1) throws SQLException;
}

