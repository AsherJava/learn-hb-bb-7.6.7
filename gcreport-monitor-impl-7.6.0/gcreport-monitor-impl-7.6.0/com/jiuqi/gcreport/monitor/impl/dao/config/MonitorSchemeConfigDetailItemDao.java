/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.config;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailItemEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorSchemeConfigDetailItemDao
extends IDbSqlGenericDAO<MonitorConfigDetailItemEO, String> {
    public List<MonitorConfigDetailItemEO> getConfigDetailItemByConfigId(String var1);

    public MonitorConfigDetailItemEO getItemByConfigIdAndNodeState(String var1, String var2);

    public void deleteConfigDetailItemByConfigId(String var1);
}

