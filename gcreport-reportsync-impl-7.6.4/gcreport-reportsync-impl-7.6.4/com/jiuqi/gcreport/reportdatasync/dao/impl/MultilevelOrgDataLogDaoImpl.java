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
import com.jiuqi.gcreport.reportdatasync.dao.MultilevelOrgDataLogDao;
import com.jiuqi.gcreport.reportdatasync.entity.MultilevelOrgDataSyncLogEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MultilevelOrgDataLogDaoImpl
extends GcDbSqlGenericDAO<MultilevelOrgDataSyncLogEO, String>
implements MultilevelOrgDataLogDao {
    public MultilevelOrgDataLogDaoImpl() {
        super(MultilevelOrgDataSyncLogEO.class);
    }

    @Override
    public PageInfo<MultilevelOrgDataSyncLogEO> listOrgDataSyncLogs(int pageNum, int pageSize) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULTILEVEL_ORGDATA_LOG", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_MULTILEVEL_ORGDATA_LOG").append(" t \n");
        sql.append(" order by uploadTime desc \n");
        String querySql = sql.toString();
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List reportDataSyncUploadLogEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)reportDataSyncUploadLogEOS, (int)count);
    }
}

