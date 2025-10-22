/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.sql.Connection;
import java.sql.SQLException;

public interface TempTableProvider {
    public ITableEntity createTempTable(Connection var1, SummaryScheme var2) throws SQLException;

    public void insertTempTableData(Connection var1, ITableEntity var2, TargetDimProvider var3, SummaryScheme var4, String var5);

    public void clearTempTableData(Connection var1, ITableEntity var2);

    public void dropTemTable(Connection var1, ITableEntity var2);
}

