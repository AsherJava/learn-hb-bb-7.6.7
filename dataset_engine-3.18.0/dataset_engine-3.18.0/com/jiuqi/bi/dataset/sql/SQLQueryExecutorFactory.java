/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import java.sql.Connection;

public abstract class SQLQueryExecutorFactory {
    public abstract SQLQueryExecutor createQueryExecutor(Connection var1, String var2, String var3);
}

