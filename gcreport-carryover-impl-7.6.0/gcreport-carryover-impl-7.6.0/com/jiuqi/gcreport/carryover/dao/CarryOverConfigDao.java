/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.carryover.dao;

import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface CarryOverConfigDao
extends IDbSqlGenericDAO<CarryOverConfigEO, String> {
    public List<CarryOverConfigEO> listAllConfigEO();
}

