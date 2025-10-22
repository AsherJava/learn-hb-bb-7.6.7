/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.offsetitem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffsetVchrFlowEO;

public interface OffsetVchrCodeDao
extends IDbSqlGenericDAO<GcOffsetVchrFlowEO, String> {
    public int updateFlow(GcOffsetVchrFlowEO var1);

    public GcOffsetVchrFlowEO getFlowNumberByDimensions(String var1);
}

