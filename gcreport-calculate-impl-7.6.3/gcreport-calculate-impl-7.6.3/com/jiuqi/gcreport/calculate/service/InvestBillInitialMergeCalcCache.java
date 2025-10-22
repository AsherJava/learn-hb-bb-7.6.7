/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import java.util.Set;

public interface InvestBillInitialMergeCalcCache {
    public Set<String> getInitialMergeCache(GcCalcArgmentsDTO var1);

    public Set<String> getPriorMastIdSetCache(GcCalcArgmentsDTO var1);

    public void removeCacheBySn(String var1);
}

