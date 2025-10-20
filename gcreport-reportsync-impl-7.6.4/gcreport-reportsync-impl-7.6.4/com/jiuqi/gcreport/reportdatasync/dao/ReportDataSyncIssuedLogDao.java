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
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogEO;
import java.util.List;

public interface ReportDataSyncIssuedLogDao
extends IDbSqlGenericDAO<ReportDataSyncIssuedLogEO, String> {
    public PageInfo<ReportDataSyncIssuedLogEO> listXfLogs(String var1, Integer var2, Integer var3);

    public List<ReportDataSyncIssuedLogEO> listXfStartedLogs();

    public ReportDataSyncIssuedLogEO queryLatestSyncParamTaskByTaskId(String var1);

    public void deprecatedHistoryIssuedLog(String var1, String var2);
}

