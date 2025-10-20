/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.item.service;

import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GcRelatedItemQueryService {
    public List<GcRelatedItemEO> queryByOffsetCondition(BalanceCondition var1);

    public List<GcRelatedItemEO> findByCheckIds(Collection<String> var1);

    public List<GcRelatedItemEO> findByCheckSchemeIds(Collection<String> var1);

    public Set<String> countByIdAndRecordTimestamp(Collection<String> var1, long var2);

    public List<GcRelatedItemEO> queryByIds(Collection<String> var1);

    public List<GcRelatedItemEO> queryByGcNumber(String var1);

    public List<GcRelatedItemEO> queryByGcNumberAndUnit(String var1, String var2, String var3, Integer var4);

    public List<GcRelatedItemEO> queryUncheckedItemByUnitsAndYear(Set<String> var1, List<String> var2, int var3);

    public List<GcRelatedItemEO> listExistRelatedItemsByBatchId(String var1, boolean var2);

    public List<GcRelatedItemEO> queryUncheckedItemByUnitAndDataTime(Set<String> var1, List<String> var2, String var3, String var4, Set<String> var5);
}

