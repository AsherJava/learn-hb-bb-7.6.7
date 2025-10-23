/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.framework.connection;

import java.sql.Connection;

public interface ConnectionManager {
    public Connection getConnection();

    public void releaseConnection(Connection var1);
}

