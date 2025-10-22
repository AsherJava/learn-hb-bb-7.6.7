/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.monitor.impl.dao.config.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDetailDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorSchemeConfigDetailDaoImpl
extends GcDbSqlGenericDAO<MonitorConfigDetailEO, String>
implements MonitorSchemeConfigDetailDao {
    public MonitorSchemeConfigDetailDaoImpl() {
        super(MonitorConfigDetailEO.class);
    }

    @Override
    public List<MonitorConfigDetailEO> findConfigDetailByMonitorId(String monitorId) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORCONFIG", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORCONFIG" + "  t \n where t.monitorid=?\n";
        return this.selectEntity(sql, new Object[]{monitorId});
    }

    @Override
    public void deleteBySchemeId(String monitorId) {
        String sql = "  delete  from GC_MONITORCONFIG   \n where monitorid=?\n";
        this.execute(sql, new Object[]{monitorId});
    }

    @Override
    public MonitorConfigDetailEO findConfigDetailByNodecode(String monitorId, String nodeCode) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORCONFIG", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORCONFIG" + "  t \n where t.monitorid=? \n and t.nodeCode=? \n";
        List dataList = this.selectEntity(sql, new Object[]{monitorId, nodeCode});
        if (dataList != null && dataList.size() > 0) {
            return (MonitorConfigDetailEO)((Object)dataList.get(0));
        }
        return null;
    }
}

