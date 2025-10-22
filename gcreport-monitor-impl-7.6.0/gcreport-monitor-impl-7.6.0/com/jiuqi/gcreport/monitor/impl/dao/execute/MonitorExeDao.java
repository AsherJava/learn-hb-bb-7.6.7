/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.monitor.impl.dao.execute;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorExeSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorExeDao
extends IDbSqlGenericDAO<MonitorExeSchemeEO, String> {
    public Boolean checkCode(String var1);

    public List<MonitorExeSchemeEO> loadAllBySortOrder();
}

