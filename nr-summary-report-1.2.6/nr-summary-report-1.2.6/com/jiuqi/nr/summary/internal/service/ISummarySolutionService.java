/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import java.util.List;

public interface ISummarySolutionService {
    public String insertSummarySolution(SummarySolutionDTO var1) throws SummaryCommonException;

    public void deleteSummarySolutionByKey(String var1) throws SummaryCommonException;

    public void batchDeleteSummarySolutions(List<String> var1) throws SummaryCommonException;

    public void updateSummarySolution(SummarySolutionDTO var1) throws SummaryCommonException;

    public SummarySolutionDTO getSummarySolutionByKey(String var1, boolean var2);

    public List<SummarySolutionDTO> getSummarySolutionsByGroup(String var1, boolean var2);

    public List<SummarySolutionDTO> getSummarySolutionsByGroups(List<String> var1, boolean var2);
}

