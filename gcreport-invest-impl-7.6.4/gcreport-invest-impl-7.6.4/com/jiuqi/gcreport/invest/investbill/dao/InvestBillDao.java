/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.investbill.dao;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InvestBillDao {
    public List<Map<String, Object>> listInvestBillsByPaging(Map<String, Object> var1);

    public List<Map<String, Object>> listInvests(Map<String, Object> var1);

    public int countInvestBills(Map<String, Object> var1);

    public String getIdByUnitAndYear(String var1, String var2, int var3);

    public Map<String, Object> getByUnitAndYear(String var1, String var2, int var3);

    public List<Map<String, Object>> listInvestBillsByIds(List<String> var1);

    public List<Map<String, Object>> getInvestBillItemByBillCode(String var1);

    public void batchDeleteByIdList(List<String> var1);

    public Map<String, Object> getInvestBillById(String var1);

    public void updateFairValueAdjustFlag(String var1, String var2, Integer var3, int var4);

    public void updateOffsetStatus(String var1, int var2);

    public void updateNumberFieldValue(String var1, String var2, Object var3);

    public void updateFairValueOffsetStatus(String var1, int var2);

    public void updateDisPoseDate(Date var1, Set<String> var2);

    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> var1, Set<String> var2, int var3, int var4);

    public List<DefaultTableEntity> getInvestmentItemsByMastId(String var1);

    public List<Map<String, Object>> getByYear(int var1, int var2, List<String> var3);

    public List<Map<String, Object>> getByYear(int var1, int var2, int var3, List<String> var4);

    public int deleteByYearAndUnit(String var1, int var2, int var3, List<String> var4);

    public int deleteInvestItemByMasterId(Set<String> var1);

    public Double sumInvestNumber(String var1, Integer var2, String var3, String var4, Date var5);

    public List<Map<String, Object>> listInvestedCompreEquityRatio(int var1);

    public List<Map<String, Object>> listByWhere(String[] var1, Object[] var2);

    public List<String> getInvestIdsBySrcIdAndBeginPeriod(String var1, int var2);

    public Map<String, Object> getInvestBySrcIdAndPeriod(String var1, int var2);

    public Set<String> listInvestIdsBySrcIdsAndExactPeriod(Collection<String> var1, int var2);

    public List<Map<String, Object>> listSubItemsOfManual(int var1, List<String> var2);

    public int deleteBySrcIds(Collection<String> var1, Integer var2);

    public Map<String, Object> getInvestBySrcIdAndYearAndPeriod(String var1, int var2, int var3);
}

