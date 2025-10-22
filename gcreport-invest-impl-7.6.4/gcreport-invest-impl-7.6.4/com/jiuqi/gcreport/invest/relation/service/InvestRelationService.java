/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.relation.service;

import java.util.List;
import java.util.Map;

public interface InvestRelationService {
    public List<Map<String, Object>> getDirectInvest(Map<String, Object> var1);

    public List<Map<String, Object>> getIndirectInvest(Map<String, Object> var1);

    public List<Map<String, Object>> getInvestOfTree(Map<String, Object> var1);

    public Map<String, Object> getInvest(Map<String, Object> var1);
}

