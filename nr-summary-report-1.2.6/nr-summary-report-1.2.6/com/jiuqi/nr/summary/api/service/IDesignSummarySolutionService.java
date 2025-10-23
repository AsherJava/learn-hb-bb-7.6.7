/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.api.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.vo.ReportCopyRequestParam;
import java.util.List;

public interface IDesignSummarySolutionService {
    public void insertSummarySolutionGroup(SummarySolutionGroup var1) throws SummaryCommonException;

    public void updateSummarySolutionGroup(SummarySolutionGroup var1) throws SummaryCommonException;

    public void deleteSummarySolutionGroup(String var1) throws SummaryCommonException;

    public void deleteSummarySolutionGroups(List<String> var1) throws SummaryCommonException;

    public SummarySolutionGroup getSummarySolutionGroup(String var1);

    public List<SummarySolutionGroup> getSummarySolutionGroupByGroup(String var1);

    public void insertSummarySolutionModel(SummarySolutionModel var1) throws SummaryCommonException;

    public List<SummarySolution> getSummarySolutionsByGroup(String var1);

    public SummarySolutionModel getSummarySolutionModel(String var1);

    public SummarySolution getSummarySolutionDefine(String var1);

    public void deleteSummarySolution(String var1) throws SummaryCommonException;

    public void batchDeleteSummarySolutions(List<String> var1) throws SummaryCommonException;

    public SummaryReport getSummaryReport(String var1);

    public SummaryReportModel getSummaryReportModel(String var1) throws SummaryCommonException;

    public List<SummaryReport> getSummaryReportsBySolution(String var1);

    public List<SummaryReport> getNotEmptySummaryReportsBySolution(String var1);

    public List<SummaryReportModel> getSummaryReportModelsBySolution(String var1);

    public void copySummaryReport(ReportCopyRequestParam var1) throws SummaryCommonException;

    public List<DataCell> getDataCellsByReport(String var1);

    public boolean isDeployed(String var1);

    public boolean isReportDeployed(String var1);

    public void insertFormula(Formula var1) throws DBParaException;

    public void batchInsertFormula(List<Formula> var1) throws DBParaException;

    public void updateFormula(Formula var1) throws DBParaException;

    public void batchUpdateFormula(List<Formula> var1) throws DBParaException;

    public void deleteFormulaByKey(String var1) throws DBParaException;

    public void deleteFormulaByKeys(List<String> var1) throws DBParaException;

    public Formula getFormulaByKey(String var1);

    public List<Formula> getFormulasByReport(String var1);

    public List<Formula> getBJFormulasBySolution(String var1);

    public List<Formula> getFormulasBySolution(String var1);
}

