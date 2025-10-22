/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportdatasync.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataParamsSyncSchemeEO;
import java.util.List;

public interface ReportDataParamsSyncSchemeDao
extends IDbSqlGenericDAO<ReportDataParamsSyncSchemeEO, String> {
    public List<ReportDataParamsSyncSchemeEO> listAllParamSyncScheme();

    public List<ReportDataParamsSyncSchemeEO> queryByParamTitle(String var1);
}

