/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.sql.SQLQueryExecutorFactory;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import java.sql.Connection;

public class DefaultSQLQueryExecutorFactory
extends SQLQueryExecutorFactory {
    @Override
    public SQLQueryExecutor createQueryExecutor(Connection conn, String userGuid, String dsGuid) {
        SQLQueryExecutor executor = new SQLQueryExecutor(conn);
        return executor;
    }
}

