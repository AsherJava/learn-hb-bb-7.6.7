/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportdatasync.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import java.util.List;

public interface ReportDataSyncUploadSchemeDao
extends IDbSqlGenericDAO<ReportDataSyncUploadSchemeEO, String> {
    public List<ReportDataSyncUploadSchemeEO> listAllUploadScheme();

    public List<ReportDataSyncUploadSchemeEO> queryByTitle(String var1);
}

