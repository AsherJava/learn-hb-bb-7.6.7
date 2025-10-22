/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import java.util.List;
import java.util.Map;

public interface FloatBalanceDiffService {
    public void batchDeleteAllBalance(String var1, List<String> var2, String var3, GcDiffProcessCondition var4);

    public void addBatchFloatBalanceDiffDatas(Map<String, Object> var1, String var2, List<String> var3);

    public void batchDeleteAllBalanceByOppunitTitle(String var1, String var2, String var3, String var4, GcDiffProcessCondition var5);
}

