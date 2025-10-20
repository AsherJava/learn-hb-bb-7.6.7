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
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogItemEO;
import java.util.List;

public interface ReportDataSyncIssuedLogItemDao
extends IDbSqlGenericDAO<ReportDataSyncIssuedLogItemEO, String> {
    public List<ReportDataSyncIssuedLogItemEO> listXfLogItemsByLogId(String var1);

    public PageInfo<ReportDataSyncIssuedLogItemEO> listXfLogItemsByLogId(String var1, Integer var2, Integer var3);

    public List<ReportDataSyncIssuedLogItemEO> listXfLogItemsByLogItemIds(List<String> var1);
}

