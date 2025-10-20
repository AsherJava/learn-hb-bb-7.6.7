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
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorSchemeConfigDaoImpl
extends GcDbSqlGenericDAO<MonitorSchemeEO, String>
implements MonitorSchemeConfigDao {
    public MonitorSchemeConfigDaoImpl() {
        super(MonitorSchemeEO.class);
    }

    @Override
    public Boolean checkMonitorSchemeCode(String code) {
        String sql = "  select t.id from GC_MONITORSCHEME  t \n  where t.schemecode=?\n";
        int queryCountBySql = this.count(sql, new Object[]{code});
        return queryCountBySql <= 0;
    }

    @Override
    public List<MonitorSchemeEO> loadAllBySortOrder() {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORSCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORSCHEME" + "  t \n  order by t.sortorder\n";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public List<MonitorSchemeEO> findByGroupId(String groupId) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORSCHEME", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORSCHEME" + "  t \n where t.group_id=?\n";
        return this.selectEntity(sql, new Object[]{groupId});
    }

    @Override
    public void deleteByGroupId(String groupId) {
        String sql = "  delete from GC_MONITORSCHEME   \n where group_id= ? \n";
        this.execute(sql, new Object[]{groupId});
    }
}

