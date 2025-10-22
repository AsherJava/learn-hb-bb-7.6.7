/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportdatasync.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveTaskEO;
import java.util.List;

public interface ReportDataSyncReceiveTaskDao
extends IDbSqlGenericDAO<ReportDataSyncReceiveTaskEO, String> {
    public PageInfo<ReportDataSyncReceiveTaskEO> listAllReceiveTask(Integer var1, Integer var2);

    public List<String> listAllReceiveTaskIds();

    public List<ReportDataSyncReceiveTaskEO> listReceiveTaskBySyncVersion(String var1, String var2);

    public List<ReportDataSyncReceiveTaskEO> listReceiveTaskByTaskId(String var1, String var2);

    public String getAvailable(String var1);
}

