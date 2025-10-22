/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.aidocaudit.dao;

import com.jiuqi.gcreport.aidocaudit.eo.AidocAuditLogEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface IAidocauditLogDao
extends IDbSqlGenericDAO<AidocAuditLogEO, String> {
    public List<AidocAuditLogEO> getUnCompletedAuditLog(String var1);

    public List<AidocAuditLogEO> getAuditLogByTask(String var1);

    public List<AidocAuditLogEO> getCompletedLatestAuditLog(String var1);
}

