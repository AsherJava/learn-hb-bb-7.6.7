/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.config;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorGroupEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorGroupConfigDao
extends IDbSqlGenericDAO<MonitorGroupEO, String> {
    public Boolean checkMonitorGroupCode(String var1);

    public List<MonitorGroupEO> loadAllBySortOrder();

    public List<MonitorGroupEO> findByGroupId(String var1);
}

