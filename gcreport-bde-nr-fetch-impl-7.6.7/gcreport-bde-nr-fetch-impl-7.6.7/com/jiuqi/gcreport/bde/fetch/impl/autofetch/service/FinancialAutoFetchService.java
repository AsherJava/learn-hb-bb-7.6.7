/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.service;

import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import java.util.List;

public interface FinancialAutoFetchService {
    public List<String> getCashCodesByCf(FetchRangCondition var1);

    public List<String> getSubjectCodesByDim(FetchRangCondition var1);

    public List<String> getSubjectCodesByAging(FetchRangCondition var1);
}

