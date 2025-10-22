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
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorGroupConfigDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorGroupEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorGroupConfigDaoImpl
extends GcDbSqlGenericDAO<MonitorGroupEO, String>
implements MonitorGroupConfigDao {
    public MonitorGroupConfigDaoImpl() {
        super(MonitorGroupEO.class);
    }

    @Override
    public Boolean checkMonitorGroupCode(String code) {
        String sql = "  select t.id from GC_MONITORGROUP  t \n  where t.groupcode=?\n";
        int queryCountBySql = this.count(sql, new Object[]{code});
        return queryCountBySql <= 0;
    }

    @Override
    public List<MonitorGroupEO> loadAllBySortOrder() {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORGROUP", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORGROUP" + "  t \n  order by t.sortorder\n";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public List<MonitorGroupEO> findByGroupId(String groupId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MONITORGROUP", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORGROUP" + "  t \n where t.GROUP_ID=?\n";
        return this.selectEntity(sql, new Object[]{groupId});
    }
}

