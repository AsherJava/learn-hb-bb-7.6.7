/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.Map;

public interface FinancialCheckImplDao
extends IBaseSqlGenericDAO<GcRelatedItemEO> {
    public PageInfo<GcRelatedItemEO> queryChecked(FinancialCheckQueryConditionDTO var1, String var2);

    public PageInfo<GcRelatedItemEO> queryUncheckedGroupByUnit(FinancialCheckQueryConditionDTO var1, String var2, Map<String, String> var3);

    public PageInfo<GcRelatedItemEO> queryUncheckedGroupByOppUnit(FinancialCheckQueryConditionDTO var1, String var2, Map<String, String> var3);

    public PageInfo<GcRelatedItemEO> queryUncheckedGroupByScheme(FinancialCheckQueryConditionDTO var1, String var2, Map<String, String> var3);

    public Map<String, Object> queryAmtSum(FinancialCheckQueryConditionDTO var1, String var2);
}

