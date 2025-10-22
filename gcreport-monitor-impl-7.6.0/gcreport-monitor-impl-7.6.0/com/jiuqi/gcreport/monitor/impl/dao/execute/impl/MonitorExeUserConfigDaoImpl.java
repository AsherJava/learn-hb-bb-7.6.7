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
import com.jiuqi.gcreport.monitor.impl.dao.execute.MonitorExeUserConfigDao;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorExeUserConfigEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorExeUserConfigDaoImpl
extends GcDbSqlGenericDAO<MonitorExeUserConfigEO, String>
implements MonitorExeUserConfigDao {
    public MonitorExeUserConfigDaoImpl() {
        super(MonitorExeUserConfigEO.class);
    }

    @Override
    public List<MonitorExeUserConfigEO> findNodesByUserId(String userId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MONITORUSERCONFIG", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MONITORUSERCONFIG" + "  t \n where t.userId=?\n";
        return this.selectEntity(sql, new Object[]{userId});
    }
}

