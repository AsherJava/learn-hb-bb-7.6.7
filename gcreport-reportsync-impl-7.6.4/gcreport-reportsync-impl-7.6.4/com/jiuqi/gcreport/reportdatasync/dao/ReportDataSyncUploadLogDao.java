/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO
 */
package com.jiuqi.gcreport.reportdatasync.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO;

public interface ReportDataSyncUploadLogDao
extends IDbSqlGenericDAO<ReportDataSyncUploadLogEO, String> {
    public PageInfo<ReportDataSyncUploadLogEO> listUploadLogsBySchemeId(ReportDataSyncUploadLogQueryParamVO var1);

    public ReportDataSyncUploadLogEO queryFirstUploadLogByTaskCodeAndPeriodStr(String var1, String var2, String var3);
}

