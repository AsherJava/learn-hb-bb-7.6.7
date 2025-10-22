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
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveLogEO;

public interface ReportDataSyncReceiveLogDao
extends IDbSqlGenericDAO<ReportDataSyncReceiveLogEO, String> {
    public PageInfo<ReportDataSyncReceiveLogEO> listReceiveTaskLogs(Integer var1, Integer var2);

    public ReportDataSyncReceiveLogEO queryLatestSyncSuccessReceiveTaskByTaskId(String var1);
}

