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
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncIssuedLogDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogEO;
import com.jiuqi.gcreport.reportdatasync.enums.ParamPackageStatus;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncIssuedLogDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncIssuedLogEO, String>
implements ReportDataSyncIssuedLogDao {
    public ReportDataSyncIssuedLogDaoImpl() {
        super(ReportDataSyncIssuedLogEO.class);
    }

    @Override
    public PageInfo<ReportDataSyncIssuedLogEO> listXfLogs(String paramSyncSchemeId, Integer pageSize, Integer pageNum) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_XF_LOG", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_XF_LOG" + "  t where syncSchemeId='" + paramSyncSchemeId + "' order by syncTime desc";
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List serverListEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)serverListEOS, (int)count);
    }

    @Override
    public List<ReportDataSyncIssuedLogEO> listXfStartedLogs() {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_XF_LOG", (String)"t");
        String querySql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_XF_LOG" + "  t where paramStatus = '" + (Object)((Object)ParamPackageStatus.STARTED) + "' order by syncTime desc";
        List serverListEOS = this.selectEntity(querySql, new Object[0]);
        if (serverListEOS == null) {
            return Collections.emptyList();
        }
        return serverListEOS;
    }

    @Override
    public ReportDataSyncIssuedLogEO queryLatestSyncParamTaskByTaskId(String taskId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_XF_LOG", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_XF_LOG" + "  t where taskId='" + taskId + "'order by syncTime desc";
        List logEOs = this.selectEntity(sql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)logEOs)) {
            return null;
        }
        return (ReportDataSyncIssuedLogEO)((Object)logEOs.get(0));
    }

    @Override
    public void deprecatedHistoryIssuedLog(String taskId, String syncVersion) {
        String sql = "  update GC_PARAMSYNC_XF_LOG set paramStatus = '" + ParamPackageStatus.DEPRECATED.getCode() + "' where taskId='" + taskId + "' and syncVersion < '" + syncVersion + "'";
        this.execute(sql);
    }
}

