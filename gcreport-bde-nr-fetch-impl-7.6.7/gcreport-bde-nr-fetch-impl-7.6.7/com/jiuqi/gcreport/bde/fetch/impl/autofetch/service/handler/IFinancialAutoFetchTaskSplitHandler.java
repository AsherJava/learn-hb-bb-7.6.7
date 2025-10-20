/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRangResult
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.handler;

import com.jiuqi.bde.common.dto.fetch.init.FetchRangResult;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchCondition;
import java.util.List;
import java.util.Set;

public interface IFinancialAutoFetchTaskSplitHandler {
    public String getFinancialTableName();

    public List<String> getPreTaskNames();

    public Set<String> getComputationSet();

    public List<String> getMainCodes(FetchRangCondition var1);

    public FetchRangResult getFetchRange(FinancialFetchCondition var1);
}

