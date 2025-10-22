/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.carryover.dao;

import com.jiuqi.gcreport.carryover.entity.CarryOverLogExtendEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;
import java.util.Map;

public interface CarryOverLogExtendDao
extends IDbSqlGenericDAO<CarryOverLogExtendEO, String> {
    public Map<String, Map<String, Object>> listLogExtendInfoByIds(List<String> var1);
}

