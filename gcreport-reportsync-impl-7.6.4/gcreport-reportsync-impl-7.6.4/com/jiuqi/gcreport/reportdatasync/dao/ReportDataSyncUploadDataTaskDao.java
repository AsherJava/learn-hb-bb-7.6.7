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
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadDataTaskEO;
import java.util.List;

public interface ReportDataSyncUploadDataTaskDao
extends IDbSqlGenericDAO<ReportDataSyncUploadDataTaskEO, String> {
    public PageInfo<ReportDataSyncUploadDataTaskEO> listAllExecutingTask(Integer var1, Integer var2);

    public PageInfo<ReportDataSyncUploadDataTaskEO> listAllFinishedTask(Integer var1, Integer var2);

    public List<ReportDataSyncUploadDataTaskEO> listDataUploadTasksByWhereSql(String var1);

    public int updateTaskStatusByIds(String var1, List<String> var2);
}

