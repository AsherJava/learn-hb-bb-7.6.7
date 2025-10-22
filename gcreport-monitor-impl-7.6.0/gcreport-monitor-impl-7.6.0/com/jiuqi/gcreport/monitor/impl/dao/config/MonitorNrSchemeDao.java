/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.config;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorNrSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorNrSchemeDao
extends IDbSqlGenericDAO<MonitorNrSchemeEO, String> {
    public List<MonitorNrSchemeEO> getNrSchemeByMonitorId(String var1);

    public MonitorNrSchemeEO findByNrIdAndDataTime(String var1, String var2);

    public MonitorNrSchemeEO findByFormSchemeId(String var1);

    public boolean checkDateRange(String var1, String var2, String var3, String var4);

    public void deleteNrSchemeByMonitorId(String var1);
}

