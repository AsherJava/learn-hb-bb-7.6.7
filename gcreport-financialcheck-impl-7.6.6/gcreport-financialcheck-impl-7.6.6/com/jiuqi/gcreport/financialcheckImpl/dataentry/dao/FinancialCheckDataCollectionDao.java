/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public interface FinancialCheckDataCollectionDao
extends IBaseSqlGenericDAO<GcRelatedItemEO> {
    public List<GcRelatedItemEO> listSourceCFNotExistItem(String var1, String var2);

    public List<GcRelatedItemEO> listSourceDIMNotExistItem(String var1, String var2);
}

