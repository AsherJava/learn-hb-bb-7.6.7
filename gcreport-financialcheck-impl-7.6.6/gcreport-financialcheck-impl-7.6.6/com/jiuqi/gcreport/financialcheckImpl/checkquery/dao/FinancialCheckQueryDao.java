/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.checkquery.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckImpl.checkquery.dto.FinancialCheckQueryDTO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;
import java.util.Map;

public interface FinancialCheckQueryDao
extends IBaseSqlGenericDAO<GcRelatedItemEO> {
    public PageInfo<Map<String, Object>> queryBothCheck(FinancialCheckQueryDTO var1, List<String> var2, ReconciliationModeEnum var3, String var4);

    public PageInfo<Map<String, Object>> querySingleCheck(FinancialCheckQueryDTO var1, List<String> var2, ReconciliationModeEnum var3, String var4);

    public PageInfo<Map<String, Object>> queryOffsetVoucherModeCheck(FinancialCheckQueryDTO var1, List<String> var2, String var3);
}

