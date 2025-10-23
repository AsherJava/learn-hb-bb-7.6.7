/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionGroupDTO;
import java.util.List;

public interface ISummarySolutionGroupService {
    public String insertSummarySolutionGroup(SummarySolutionGroupDTO var1) throws SummaryCommonException;

    public void deleteSummarySolutionGroupByKey(String var1) throws SummaryCommonException;

    public void deleteSummarySolutionGroupByKeys(List<String> var1) throws SummaryCommonException;

    public void updateSummarySolutionGroup(SummarySolutionGroupDTO var1) throws SummaryCommonException;

    public SummarySolutionGroup getSummarySolutionGroupByKey(String var1);

    public List<SummarySolutionGroupDTO> getSummarySolutionGroupsByGroup(String var1);
}

