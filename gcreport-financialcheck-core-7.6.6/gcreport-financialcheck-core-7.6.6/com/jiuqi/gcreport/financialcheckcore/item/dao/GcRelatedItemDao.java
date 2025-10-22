/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GcRelatedItemDao
extends IBaseSqlGenericDAO<GcRelatedItemEO> {
    public List<GcRelatedItemEO> findByCheckIds(Collection<String> var1);

    public List<GcRelatedItemEO> findByCheckIdAndCheckPeriod(String var1, Integer var2);

    public List<GcRelatedItemEO> findByCheckSchemeIds(Collection<String> var1);

    public void deleteByIds(List<String> var1);

    public long updateCheckInfo(List<GcRelatedItemEO> var1, String var2);

    public Set<String> countByIdAndRecordTimestamp(Collection<String> var1, long var2);

    public List<GcRelatedItemEO> queryByIds(Collection<String> var1);

    public String queryLastUpdateTime(int var1);

    public List<GcRelatedItemEO> queryUncheckedItemByUnitsAndYear(Set<String> var1, List<String> var2, int var3);

    public long deleteGcNumber(List<GcRelatedItemEO> var1);

    public List<GcRelatedItemEO> queryByOffsetCondition(BalanceCondition var1);

    public List<GcRelatedItemEO> queryNeedCancelClbrItems(String var1);

    public List<GcRelatedItemEO> listExistRelatedItemsByBatchId(String var1, boolean var2);

    public long updateCheckSchemeInfo(List<GcRelatedItemEO> var1);

    public List<GcRelatedItemEO> listNoSchemeIdByAcctYear(Integer var1);

    public List<GcRelatedItemEO> listBySchemeIdAndCondition(String var1, Integer var2, Integer var3, List<String> var4);

    public long updateItemBaseInfo(List<GcRelatedItemEO> var1);

    public List<GcRelatedItemEO> queryUncheckedItemByUnitAndDataTime(Set<String> var1, List<String> var2, String var3, String var4, Set<String> var5);

    public long updateCheckInfoAndScheme(List<GcRelatedItemEO> var1, String var2);

    public List<GcRelatedItemEO> queryByGcNumberAndUnit(String var1, String var2, String var3, Integer var4);

    public List<GcRelatedItemEO> queryByGcNumber(String var1);
}

