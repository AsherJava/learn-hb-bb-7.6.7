/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.carryover.dao;

import com.jiuqi.gcreport.carryover.entity.CarryOverTaskRelEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;
import java.util.Map;

public interface CarryOverTaskRelDao
extends IDbSqlGenericDAO<CarryOverTaskRelEO, String> {
    public List<CarryOverTaskRelEO> listByGroupIdAndState(String var1, String var2);

    public Map<String, Integer> getAsyncTaskId2State(String var1);
}

