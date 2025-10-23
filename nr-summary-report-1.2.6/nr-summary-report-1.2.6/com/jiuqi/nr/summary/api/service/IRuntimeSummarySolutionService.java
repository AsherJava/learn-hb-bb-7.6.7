/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api.service;

import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import java.util.List;

public interface IRuntimeSummarySolutionService {
    public SummarySolution getSummarySolutionDefine(String var1);

    public SummarySolutionModel getSummarySolutionModel(String var1);

    public SummaryReport getSummaryReportDefine(String var1);

    public List<SummaryReport> getSummaryReportDefines(List<String> var1);

    public SummaryReportModel getSummaryReportModel(String var1);

    public List<SummaryReport> getSummaryReportDefinesBySolu(String var1);

    public List<SummaryReport> getSummaryReportDefinesBySolus(List<String> var1);

    public List<SummaryReportModel> getSummaryReportModelsBySolu(String var1);

    public SummarySolutionGroup getSummarySolutionGroup(String var1);

    public List<SummarySolutionGroup> getSummarySolutionGroupByGroup(String var1);

    public List<SummarySolution> getSummarySolutionDefinesByGroup(String var1);

    public List<SummarySolution> getSummarySolutionDefinesByGroups(List<String> var1);

    public Formula getFormulaByKey(String var1);

    public List<Formula> getFormulasByReport(String var1);

    public List<Formula> getBJFormulasBySolution(String var1);

    public List<Formula> getFormulasBySolution(String var1);
}

