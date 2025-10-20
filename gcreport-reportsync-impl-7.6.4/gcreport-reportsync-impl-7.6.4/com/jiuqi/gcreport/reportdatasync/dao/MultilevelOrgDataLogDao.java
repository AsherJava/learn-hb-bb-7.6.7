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
import com.jiuqi.gcreport.reportdatasync.entity.MultilevelOrgDataSyncLogEO;

public interface MultilevelOrgDataLogDao
extends IDbSqlGenericDAO<MultilevelOrgDataSyncLogEO, String> {
    public PageInfo<MultilevelOrgDataSyncLogEO> listOrgDataSyncLogs(int var1, int var2);
}

