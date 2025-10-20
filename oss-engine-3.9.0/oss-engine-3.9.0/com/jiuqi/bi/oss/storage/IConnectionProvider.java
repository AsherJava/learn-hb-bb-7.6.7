/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection openConnection() throws SQLException;
}

