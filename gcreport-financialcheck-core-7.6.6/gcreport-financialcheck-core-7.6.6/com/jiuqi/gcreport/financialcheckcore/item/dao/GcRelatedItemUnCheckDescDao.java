/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemUnCheckDescEO;
import java.util.Collection;
import java.util.List;

public interface GcRelatedItemUnCheckDescDao
extends IBaseSqlGenericDAO<GcRelatedItemUnCheckDescEO> {
    public List<GcRelatedItemUnCheckDescEO> queryExistUnCheckDesc(Collection<String> var1);

    public void updateExistUnCheckDesc(List<GcRelatedItemUnCheckDescEO> var1);

    public void deleteUnCheckDesc(Collection<String> var1);
}

