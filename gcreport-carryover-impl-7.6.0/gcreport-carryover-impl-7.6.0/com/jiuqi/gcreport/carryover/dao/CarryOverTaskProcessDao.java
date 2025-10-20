/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.carryover.dao;

import com.jiuqi.gcreport.carryover.entity.CarryOverTaskProcessEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;

public interface CarryOverTaskProcessDao
extends IDbSqlGenericDAO<CarryOverTaskProcessEO, String> {
    public void updateProcess(long var1, double var3, String var5);
}

