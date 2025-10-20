/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveLogDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveLogEO;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class ReportDataSyncReceiveLogDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncReceiveLogEO, String>
implements ReportDataSyncReceiveLogDao {
    public ReportDataSyncReceiveLogDaoImpl() {
        super(ReportDataSyncReceiveLogEO.class);
    }

    @Override
    public PageInfo<ReportDataSyncReceiveLogEO> listReceiveTaskLogs(Integer pageSize, Integer pageNum) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_RECEIVE_LOG", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_RECEIVE_LOG" + "  t order by syncTime desc,xfTime desc";
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List serverListEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)serverListEOS, (int)count);
    }

    @Override
    public ReportDataSyncReceiveLogEO queryLatestSyncSuccessReceiveTaskByTaskId(String taskId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_RECEIVE_LOG", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_RECEIVE_LOG" + "  t  where taskId = '" + taskId + "' and syncStatus = 1  order by xfTime desc";
        List receiveLogEOS = this.selectEntity(sql, new Object[0]);
        if (CollectionUtils.isEmpty(receiveLogEOS)) {
            return null;
        }
        return (ReportDataSyncReceiveLogEO)((Object)receiveLogEOS.get(0));
    }
}

