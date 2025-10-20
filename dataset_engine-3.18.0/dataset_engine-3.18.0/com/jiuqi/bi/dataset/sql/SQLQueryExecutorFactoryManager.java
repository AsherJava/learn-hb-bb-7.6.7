/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.sql.DefaultSQLQueryExecutorFactory;
import com.jiuqi.bi.dataset.sql.SQLQueryExecutorFactory;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import java.sql.Connection;

public class SQLQueryExecutorFactoryManager {
    private static final SQLQueryExecutorFactoryManager _instance = new SQLQueryExecutorFactoryManager();
    private SQLQueryExecutorFactory factory = new DefaultSQLQueryExecutorFactory();

    private SQLQueryExecutorFactoryManager() {
    }

    public static SQLQueryExecutorFactoryManager getInstance() {
        return _instance;
    }

    public void setFactory(SQLQueryExecutorFactory factory) {
        this.factory = factory;
    }

    public SQLQueryExecutor createQueryExecutor(Connection conn, String userGuid, String dsGuid) {
        return this.factory.createQueryExecutor(conn, userGuid, dsGuid);
    }
}

