/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveTaskDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveTaskEO;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncReceiveTaskDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncReceiveTaskEO, String>
implements ReportDataSyncReceiveTaskDao {
    public ReportDataSyncReceiveTaskDaoImpl() {
        super(ReportDataSyncReceiveTaskEO.class);
    }

    @Override
    public PageInfo<ReportDataSyncReceiveTaskEO> listAllReceiveTask(Integer pageSize, Integer pageNum) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_RECEIVE", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_RECEIVE" + "  t order by xfTime desc,syncTime desc";
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List serverListEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)serverListEOS, (int)count);
    }

    @Override
    public List<String> listAllReceiveTaskIds() {
        String querySql = "  select distinct t.taskId from GC_PARAMSYNC_RECEIVE  t";
        List receiveTaskIds = this.selectFirstList(String.class, querySql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)receiveTaskIds)) {
            return Collections.emptyList();
        }
        return receiveTaskIds;
    }

    @Override
    public List<ReportDataSyncReceiveTaskEO> listReceiveTaskBySyncVersion(String taskId, String syncVersion) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_RECEIVE", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_RECEIVE" + "  t where t.taskId=? and t.syncVersion=?";
        return this.selectEntity(querySql, new Object[]{taskId, syncVersion});
    }

    @Override
    public List<ReportDataSyncReceiveTaskEO> listReceiveTaskByTaskId(String taskId, String id) {
        ReportDataSyncReceiveTaskEO currTaskEO = (ReportDataSyncReceiveTaskEO)this.get((Serializable)((Object)id));
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_RECEIVE", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_RECEIVE" + " t where t.taskId=? and t.taskId is not null  and t.id<>? and (t.available= 1 or t.available is null) and t.syncVersion<=? order by t.syncVersion desc";
        return this.selectEntity(querySql, new Object[]{taskId, id, currTaskEO.getSyncVersion()});
    }

    @Override
    public String getAvailable(String receiveTaskId) {
        String querySql = "  select AVAILABLE from GC_PARAMSYNC_RECEIVE t where t.id =  '" + receiveTaskId + "' ";
        String availables = (String)this.selectFirst(String.class, querySql, new Object[0]);
        return availables;
    }
}

