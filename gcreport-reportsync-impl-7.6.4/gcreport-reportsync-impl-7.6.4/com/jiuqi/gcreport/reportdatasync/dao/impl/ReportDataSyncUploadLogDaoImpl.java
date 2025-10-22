/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadLogDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncUploadLogDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncUploadLogEO, String>
implements ReportDataSyncUploadLogDao {
    public ReportDataSyncUploadLogDaoImpl() {
        super(ReportDataSyncUploadLogEO.class);
    }

    @Override
    public PageInfo<ReportDataSyncUploadLogEO> listUploadLogsBySchemeId(ReportDataSyncUploadLogQueryParamVO param) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_UPLOAD_LOG", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_UPLOAD_LOG").append(" t \n");
        sql.append(" where ");
        sql.append(" uploadSchemeId='").append(param.getUploadSchemeId()).append("' order by uploadTime desc");
        String querySql = sql.toString();
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        int pageNum = param.getPageNum();
        int pageSize = param.getPageSize();
        List reportDataSyncUploadLogEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)reportDataSyncUploadLogEOS, (int)count);
    }

    @Override
    public ReportDataSyncUploadLogEO queryFirstUploadLogByTaskCodeAndPeriodStr(String taskCode, String periodValue, String adjustCode) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_UPLOAD_LOG", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_DATASYNC_UPLOAD_LOG").append(" t \n");
        sql.append(" where ");
        sql.append(" taskCode='").append(taskCode).append("'");
        sql.append(" and periodValue='").append(periodValue).append("'");
        if (adjustCode != null) {
            sql.append(" and adjustCode='").append(adjustCode).append("'");
        } else {
            sql.append(" and adjustCode = '0' ");
        }
        sql.append(" order by uploadTime desc");
        List reportDataSyncUploadLogEOS = this.selectEntity(sql.toString(), new Object[0]);
        if (CollectionUtils.isEmpty((Collection)reportDataSyncUploadLogEOS)) {
            return null;
        }
        ReportDataSyncUploadLogEO reportDataSyncUploadLogEO = (ReportDataSyncUploadLogEO)((Object)reportDataSyncUploadLogEOS.get(0));
        return reportDataSyncUploadLogEO;
    }
}

