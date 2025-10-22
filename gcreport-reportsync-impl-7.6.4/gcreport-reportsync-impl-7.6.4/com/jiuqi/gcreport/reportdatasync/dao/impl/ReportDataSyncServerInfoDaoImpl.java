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
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncServerInfoDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerInfoEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSyncServerInfoDaoImpl
extends GcDbSqlGenericDAO<ReportDataSyncServerInfoEO, String>
implements ReportDataSyncServerInfoDao {
    public ReportDataSyncServerInfoDaoImpl() {
        super(ReportDataSyncServerInfoEO.class);
    }

    @Override
    public ReportDataSyncServerInfoEO queryServerInfo() {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_DATASYNC_SERVERINFO", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_DATASYNC_SERVERINFO" + "  t";
        List serverInfoEOList = this.selectEntity(sql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)serverInfoEOList)) {
            return null;
        }
        return (ReportDataSyncServerInfoEO)((Object)serverInfoEOList.get(0));
    }
}

