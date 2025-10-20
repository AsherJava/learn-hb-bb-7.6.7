/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import java.sql.Connection;
import java.sql.SQLException;

public interface ISQLQueryProvider {
    public Connection getConnection(String var1) throws SQLException;

    default public String getContextUserId() {
        return null;
    }

    public SQLQueryExecutor createQueryExecutor(Connection var1, String var2, String var3) throws SQLQueryException;
}

