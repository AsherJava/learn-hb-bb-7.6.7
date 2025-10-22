/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.investbill.dao;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.List;
import java.util.Map;

public interface FairValueBillDao {
    public String queryFvchBillCode(String var1, String var2, Integer var3);

    public List<DefaultTableEntity> getFvchFixedItemBills(String var1, String var2, String var3, Integer var4);

    public List<DefaultTableEntity> getFvchOtherItemBills(String var1, String var2, String var3, Integer var4);

    public List<Map<String, Object>> queryFvchFixedItemBills(Map<String, Object> var1);

    public List<Map<String, Object>> queryFvchOtherItemBills(Map<String, Object> var1);

    public Map<String, Object> getByUnitAndYear(String var1, String var2, int var3);

    public void deleteMaster(String var1);

    public Map<String, Object> getMasterByYearAndSrcId(int var1, String var2);
}

