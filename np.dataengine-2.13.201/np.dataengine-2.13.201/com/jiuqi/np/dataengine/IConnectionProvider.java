/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine;

import java.sql.Connection;

public interface IConnectionProvider {
    public Connection getConnection();

    public void closeConnection(Connection var1);
}

