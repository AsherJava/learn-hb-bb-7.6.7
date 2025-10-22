/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.config;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorSchemeConfigDao
extends IDbSqlGenericDAO<MonitorSchemeEO, String> {
    public Boolean checkMonitorSchemeCode(String var1);

    public List<MonitorSchemeEO> loadAllBySortOrder();

    public List<MonitorSchemeEO> findByGroupId(String var1);

    public void deleteByGroupId(String var1);
}

