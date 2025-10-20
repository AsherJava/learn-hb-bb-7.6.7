/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemTempEO;

public interface GcRelatedItemTempDao
extends IBaseSqlGenericDAO<GcRelatedItemTempEO> {
    public void deleteItemTempsByBatchId(String var1);
}

