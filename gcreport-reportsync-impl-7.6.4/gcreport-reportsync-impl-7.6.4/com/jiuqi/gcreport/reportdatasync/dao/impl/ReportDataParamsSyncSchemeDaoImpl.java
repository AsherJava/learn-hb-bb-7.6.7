/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataParamsSyncSchemeDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataParamsSyncSchemeEO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataParamsSyncSchemeDaoImpl
extends GcDbSqlGenericDAO<ReportDataParamsSyncSchemeEO, String>
implements ReportDataParamsSyncSchemeDao {
    public ReportDataParamsSyncSchemeDaoImpl() {
        super(ReportDataParamsSyncSchemeEO.class);
    }

    @Override
    public List<ReportDataParamsSyncSchemeEO> listAllParamSyncScheme() {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_SCHEME", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select ").append(allFieldsSQL).append(" from ").append("GC_PARAMSYNC_SCHEME").append(" t \n");
        sql.append(" order by ordinal asc");
        List reportDataSyncMergeParamsEOS = this.selectEntity(sql.toString(), new Object[0]);
        if (CollectionUtils.isEmpty((Collection)reportDataSyncMergeParamsEOS)) {
            return new ArrayList<ReportDataParamsSyncSchemeEO>();
        }
        return reportDataSyncMergeParamsEOS;
    }

    @Override
    public List<ReportDataParamsSyncSchemeEO> queryByParamTitle(String paramPackageTitle) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_PARAMSYNC_SCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_PARAMSYNC_SCHEME" + " t   where   PARAMPACKAGETITLE = ? \n";
        List result = this.selectEntity(sql, new Object[]{paramPackageTitle});
        return result;
    }
}

