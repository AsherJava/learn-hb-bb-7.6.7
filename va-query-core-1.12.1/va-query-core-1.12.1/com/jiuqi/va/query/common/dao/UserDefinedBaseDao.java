/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common.dao;

import java.sql.Connection;

public interface UserDefinedBaseDao {
    public Connection getConnection();

    public void closeConnection(Connection var1);
}

