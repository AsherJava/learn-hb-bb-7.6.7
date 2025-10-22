/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncIssuedLogItemDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogItemEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncIssuedLogItemDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncIssuedLogItemEO, String>
implements ReportDataSyncIssuedLogItemDao {
    public ReportDataSyncIssuedLogItemDaoImpl() {
        super(ReportDataSyncIssuedLogItemEO.class);
    }

    @Override
    public List<ReportDataSyncIssuedLogItemEO> listXfLogItemsByLogId(String logId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_XF_LOG_ITEM", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_PARAMSYNC_XF_LOG_ITEM").append(" t \n");
        sql.append(" where ");
        sql.append(" xfTaskId='").append(logId).append("'");
        sql.append(" order by status asc,syncTime desc");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public PageInfo<ReportDataSyncIssuedLogItemEO> listXfLogItemsByLogId(String logId, Integer pageSize, Integer pageNum) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_XF_LOG_ITEM", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_PARAMSYNC_XF_LOG_ITEM").append(" t \n");
        sql.append(" where ");
        sql.append(" xfTaskId='").append(logId).append("'");
        sql.append(" order by status asc,syncTime desc");
        String querySql = sql.toString();
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List serverListEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)serverListEOS, (int)count);
    }

    @Override
    public List<ReportDataSyncIssuedLogItemEO> listXfLogItemsByLogItemIds(List<String> logItemIds) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_XF_LOG_ITEM", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_PARAMSYNC_XF_LOG_ITEM").append(" t \n");
        sql.append(" where ");
        sql.append(SqlBuildUtil.getStrInCondi((String)"id", logItemIds));
        sql.append(" order by status asc,syncTime desc");
        return this.selectEntity(sql.toString(), new Object[0]);
    }
}

