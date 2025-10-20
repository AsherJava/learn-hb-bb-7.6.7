/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.sql;

import com.jiuqi.bi.distributeds.IDistributeDsValueProvider;
import com.jiuqi.bi.sql.ConnectionWapper;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {
    public Connection openDefault() throws SQLException;

    public Connection open(String var1) throws SQLException;

    default public ConnectionWapper[] openDistributeConnecion(String connName, IDistributeDsValueProvider dsValueProvider) throws SQLException {
        throw new UnsupportedOperationException();
    }

    default public boolean isMultipleConnection(String connName, IDistributeDsValueProvider dsValueProvider) throws SQLException {
        return false;
    }
}

