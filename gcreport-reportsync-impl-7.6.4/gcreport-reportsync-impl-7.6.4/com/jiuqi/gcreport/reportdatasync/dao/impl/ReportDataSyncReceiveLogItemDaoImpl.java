/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveLogItemDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveLogItemEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncReceiveLogItemDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncReceiveLogItemEO, String>
implements ReportDataSyncReceiveLogItemDao {
    public ReportDataSyncReceiveLogItemDaoImpl() {
        super(ReportDataSyncReceiveLogItemEO.class);
    }

    @Override
    public List<ReportDataSyncReceiveLogItemEO> listReceiveLogItemsByLogId(String logId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_RECEIVE_LOGITEM", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_PARAMSYNC_RECEIVE_LOGITEM").append(" t \n");
        sql.append(" where ");
        sql.append(" receiveTaskLogId='").append(logId).append("'");
        return this.selectEntity(sql.toString(), new Object[0]);
    }
}

