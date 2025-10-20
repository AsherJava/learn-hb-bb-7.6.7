/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

public interface InvestBillService {
    public PageInfo<Map<String, Object>> listInvestBills(Map<String, Object> var1);

    public List<Map<String, Object>> listInvests(Map<String, Object> var1);

    @Transactional(rollbackFor={Exception.class})
    public void batchDelete(List<String> var1);

    public String getIdByUnitAndYear(String var1, String var2, int var3);

    public void updateFairValueAdjustFlag(String var1, String var2, Integer var3, int var4);

    public Map<String, Object> checkInvestBillOffset(String var1, String var2, String var3, String var4);

    public Map<String, Object> getByUnitAndYear(String var1, String var2, int var3);

    public void updateOffsetStatus(String var1, int var2);

    public void updateFairValueOffsetStatus(String var1, int var2);

    public void updateDisPoseDate(Date var1, List<String> var2);

    public List<DefaultTableEntity> getMastByInvestAndInvestedUnit(Set<String> var1, Set<String> var2, int var3, int var4);

    public Map<String, Double> listInvestedCompreEquityRatio(int var1);

    public Map<String, Object> queryHistoryChangeRecord(Map<String, Object> var1);

    public List<Map<String, Object>> listByWhere(String[] var1, Object[] var2);
}

