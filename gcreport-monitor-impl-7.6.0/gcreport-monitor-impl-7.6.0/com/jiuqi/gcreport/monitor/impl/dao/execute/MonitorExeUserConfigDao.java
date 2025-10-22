/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.execute;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorExeUserConfigEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorExeUserConfigDao
extends IDbSqlGenericDAO<MonitorExeUserConfigEO, String> {
    public List<MonitorExeUserConfigEO> findNodesByUserId(String var1);
}

