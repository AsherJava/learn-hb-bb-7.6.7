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
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadSchemeDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncUploadSchemeDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncUploadSchemeEO, String>
implements ReportDataSyncUploadSchemeDao {
    public ReportDataSyncUploadSchemeDaoImpl() {
        super(ReportDataSyncUploadSchemeEO.class);
    }

    @Override
    public List<ReportDataSyncUploadSchemeEO> listAllUploadScheme() {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_UPLOAD_SCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_DATASYNC_UPLOAD_SCHEME" + "  t order by createTime desc";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public List<ReportDataSyncUploadSchemeEO> queryByTitle(String title) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_UPLOAD_SCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_DATASYNC_UPLOAD_SCHEME" + " t   where  title = ? \n";
        List result = this.selectEntity(sql, new Object[]{title});
        return result;
    }
}

