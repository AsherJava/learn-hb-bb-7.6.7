/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.monitor.impl.dao.execute.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.monitor.impl.dao.execute.MonitorExeDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorExeSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorExeDaoImpl
extends GcDbSqlGenericDAO<MonitorExeSchemeEO, String>
implements MonitorExeDao {
    public MonitorExeDaoImpl() {
        super(MonitorExeSchemeEO.class);
    }

    @Override
    public Boolean checkCode(String code) {
        String sql = "  select t.id from GC_MONITORSOLUTION  t \n  where t.code=?\n";
        int queryCountBySql = this.count(sql, new Object[]{code});
        return queryCountBySql <= 0;
    }

    @Override
    public List<MonitorExeSchemeEO> loadAllBySortOrder() {
        String allFieldsSQL = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_MONITORSOLUTION", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORSOLUTION" + "  t \n  order by t.sortorder\n";
        return this.selectEntity(sql, new Object[0]);
    }
}

