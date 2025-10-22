/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.config;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorSchemeConfigDetailDao
extends IDbSqlGenericDAO<MonitorConfigDetailEO, String> {
    public List<MonitorConfigDetailEO> findConfigDetailByMonitorId(String var1);

    public void deleteBySchemeId(String var1);

    public MonitorConfigDetailEO findConfigDetailByNodecode(String var1, String var2);
}

