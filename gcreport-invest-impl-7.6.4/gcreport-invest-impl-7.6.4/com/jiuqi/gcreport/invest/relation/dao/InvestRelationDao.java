/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.relation.dao;

import java.util.List;
import java.util.Map;

public interface InvestRelationDao {
    public List<Map<String, Object>> listInvestBill(Map<String, Object> var1);

    public List<Map<String, Object>> listInvestedBill(Map<String, Object> var1);

    public List<Map<String, Object>> listBill(Map<String, Object> var1);
}

