/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.aidocaudit.dao.impl;

import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditLogDao;
import com.jiuqi.gcreport.aidocaudit.eo.AidocAuditLogEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AidocauditLogDaoImpl
extends GcDbSqlGenericDAO<AidocAuditLogEO, String>
implements IAidocauditLogDao {
    public AidocauditLogDaoImpl() {
        super(AidocAuditLogEO.class);
    }

    @Override
    public List<AidocAuditLogEO> getUnCompletedAuditLog(String userId) {
        String sqlTemplate = "SELECT %s FROM  GC_AIDOCAUDIT_LOG t  WHERE AUDITUSER = ? AND STATUS = 0";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_LOG", (String)"t");
        String sql = String.format(sqlTemplate, columns);
        return this.selectEntity(sql, new Object[]{userId});
    }

    @Override
    public List<AidocAuditLogEO> getAuditLogByTask(String scoreTask) {
        String sqlTemplate = "SELECT %s FROM  GC_AIDOCAUDIT_LOG t  WHERE SCORETASK = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_LOG", (String)"t");
        String sql = String.format(sqlTemplate, columns);
        return this.selectEntity(sql, new Object[]{scoreTask});
    }

    @Override
    public List<AidocAuditLogEO> getCompletedLatestAuditLog(String userId) {
        String sqlTemplate = "SELECT %s FROM GC_AIDOCAUDIT_LOG t WHERE AUDITUSER = ? ORDER BY ENDTIME DESC";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_LOG", (String)"t");
        String sql = String.format(sqlTemplate, columns);
        return this.selectEntity(sql, new Object[]{userId});
    }
}

