/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadDataTaskDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadDataTaskEO;
import com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncUploadDataTaskDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncUploadDataTaskEO, String>
implements ReportDataSyncUploadDataTaskDao {
    public ReportDataSyncUploadDataTaskDaoImpl() {
        super(ReportDataSyncUploadDataTaskEO.class);
    }

    @Override
    public PageInfo<ReportDataSyncUploadDataTaskEO> listAllExecutingTask(Integer pageSize, Integer pageNum) {
        StringBuilder whereSql = new StringBuilder();
        whereSql.append(" t.status in('").append(TaskStatusEnum.EXECUTING.getCode()).append("','");
        whereSql.append(TaskStatusEnum.WAIT.getCode()).append("','");
        whereSql.append(TaskStatusEnum.ERROR.getCode()).append("','");
        whereSql.append(TaskStatusEnum.STOP.getCode()).append("')");
        whereSql.append(" or t.status is null");
        return this.listDataUploadTasksByWhereSqlAndPage(whereSql.toString(), pageSize, pageNum);
    }

    @Override
    public List<ReportDataSyncUploadDataTaskEO> listDataUploadTasksByWhereSql(String whereSql) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_RECEIVE", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append(" select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_RECEIVE").append("  t");
        sql.append(" where ").append(whereSql);
        sql.append(" order by uploadTime desc, syncTime desc ");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    public PageInfo<ReportDataSyncUploadDataTaskEO> listDataUploadTasksByWhereSqlAndPage(String whereSql, Integer pageSize, Integer pageNum) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_RECEIVE", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append(" select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_RECEIVE").append("  t");
        sql.append(" where ").append(whereSql);
        sql.append(" order by uploadTime desc, syncTime desc ");
        String querySql = sql.toString();
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List serverListEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)serverListEOS, (int)count);
    }

    @Override
    public PageInfo<ReportDataSyncUploadDataTaskEO> listAllFinishedTask(Integer pageSize, Integer pageNum) {
        StringBuilder whereSql = new StringBuilder();
        whereSql.append(" t.status in('").append(TaskStatusEnum.SUCCESS.getCode()).append("','");
        whereSql.append(TaskStatusEnum.ERROR.getCode()).append("','");
        whereSql.append(TaskStatusEnum.REJECTED.getCode()).append("')");
        return this.listDataUploadTasksByWhereSqlAndPage(whereSql.toString(), pageSize, pageNum);
    }

    @Override
    public int updateTaskStatusByIds(String taskStatus, List<String> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append(" update ").append("GC_DATASYNC_RECEIVE").append("  t set status='").append(taskStatus).append("' where \n");
        sql.append(SqlUtils.getConditionOfIdsUseOr(ids, (String)"id"));
        return this.execute(sql.toString());
    }
}

