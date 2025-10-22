/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntityData;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableEntity;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface ITableDBUtil {
    public Connection createConnection();

    public void releaseConnection(Connection var1);

    public void createTable(Connection var1, ITableEntity var2);

    public void dropTable(Connection var1, ITableEntity var2);

    public void insertTableData(Connection var1, ITableEntity var2, ITableEntityData var3);

    public void insertTableData(Connection var1, ITableEntityData var2, String var3, List<String> var4);

    public void implementSQL(Connection var1, String var2);

    public ITableEntityData selectSQLImplement(Connection var1, BatchSummarySqlBuilder var2);

    public IPowerTableEntity selectSQLImplement(Connection var1, String var2, List<String> var3, Map<String, Object> var4);
}

