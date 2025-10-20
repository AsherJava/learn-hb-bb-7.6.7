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
import com.jiuqi.gcreport.monitor.impl.dao.config.MonitorSchemeConfigDetailItemDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailItemEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorSchemeConfigDetailItemDaoImpl
extends GcDbSqlGenericDAO<MonitorConfigDetailItemEO, String>
implements MonitorSchemeConfigDetailItemDao {
    public MonitorSchemeConfigDetailItemDaoImpl() {
        super(MonitorConfigDetailItemEO.class);
    }

    @Override
    public List<MonitorConfigDetailItemEO> getConfigDetailItemByConfigId(String configId) {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORCONFIGITEM", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORCONFIGITEM" + "  t \n where t.configId=?\n";
        return this.selectEntity(sql, new Object[]{configId});
    }

    @Override
    public void deleteConfigDetailItemByConfigId(String configId) {
        String sql = "  delete  from GC_MONITORCONFIGITEM  \n where configId=?\n";
        this.execute(sql, new Object[]{configId});
    }

    @Override
    public MonitorConfigDetailItemEO getItemByConfigIdAndNodeState(String recid, String state) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MONITORCONFIGITEM", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORCONFIGITEM" + "  t \n where t.configId=? and t.nodestatecode starts_with ? \n";
        List queryBySql = this.selectEntity(sql, new Object[]{recid, state});
        if (queryBySql.size() > 0) {
            return (MonitorConfigDetailItemEO)((Object)queryBySql.get(0));
        }
        return null;
    }
}

