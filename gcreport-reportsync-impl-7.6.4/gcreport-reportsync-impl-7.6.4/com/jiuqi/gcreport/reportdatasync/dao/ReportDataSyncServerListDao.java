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
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerListEO;
import java.util.List;

public interface ReportDataSyncServerListDao
extends IDbSqlGenericDAO<ReportDataSyncServerListEO, String> {
    public List<ReportDataSyncServerListEO> listServerInfos();

    public PageInfo<ReportDataSyncServerListEO> listServerInfosByPage(String var1, Integer var2, Integer var3);

    public ReportDataSyncServerListEO queryServerInfoByOrgCode(String var1);

    public List<ReportDataSyncServerListEO> queryServerInfoByOrgCodes(List<String> var1);

    public List<ReportDataSyncServerListEO> listServerInfoStateByIds(List<String> var1);
}

