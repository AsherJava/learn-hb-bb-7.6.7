/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offsetvchr.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import java.util.List;
import java.util.Map;

public interface GcRelatedOffsetVoucherItemQueryDao
extends IBaseSqlGenericDAO<GcRelatedOffsetVoucherItemEO> {
    public List<GcRelatedOffsetVoucherItemEO> queryByOffsetCondition(BalanceCondition var1);

    public List<Map<String, Object>> queryByCheckGroupId(String var1);
}

