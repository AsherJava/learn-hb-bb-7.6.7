/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportdatasync.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerInfoEO;

public interface ReportDataSyncServerInfoDao
extends IDbSqlGenericDAO<ReportDataSyncServerInfoEO, String> {
    public ReportDataSyncServerInfoEO queryServerInfo();
}

