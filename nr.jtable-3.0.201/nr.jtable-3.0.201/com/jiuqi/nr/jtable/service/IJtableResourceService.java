/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportImportDataSet;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.AnalysisFormulaInfo;
import com.jiuqi.nr.jtable.params.input.CardInputInit;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.params.input.PasteFormatDataInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.output.CardInputInfo;
import com.jiuqi.nr.jtable.params.output.CellDataSet;
import com.jiuqi.nr.jtable.params.output.FMDMCheckFailNodeInfoExtend;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodDataSet;
import com.jiuqi.nr.jtable.params.output.PasteFormatReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReportDataSet;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface IJtableResourceService {
    @Deprecated
    public ReportDataSet queryReportData(ReportDataQueryInfo var1);

    @Deprecated
    public SaveResult saveReportData(ReportDataCommitSet var1);

    @Deprecated
    public SaveResult saveFmdmReportData(ReportDataCommitSet var1);

    @Deprecated
    public ReturnInfo clearReportData(ReportDataQueryInfo var1);

    @Deprecated
    public RegionDataSet queryRegionData(RegionQueryInfo var1);

    @Deprecated
    public List<String> queryRegionIds(RegionQueryInfo var1);

    @Deprecated
    public RegionSingleDataSet queryRegionSingleData(RegionQueryInfo var1);

    @Deprecated
    public RegionDataCount regionDataCount(RegionQueryInfo var1);

    @Deprecated
    public SaveResult saveRegionData(RegionDataCommitSet var1);

    @Deprecated
    public CellDataSet getCellValues(CellValueQueryInfo var1);

    public PasteFormatReturnInfo pasteFormatData(PasteFormatDataInfo var1);

    @Deprecated
    public PagerInfo floatDataLocate(RegionQueryInfo var1);

    @Deprecated
    public IReportExportDataSet getReportExportData(JtableContext var1);

    @Deprecated
    public IReportImportDataSet getReportImportData(JtableContext var1);

    public ReturnInfo calculateForm(JtableContext var1);

    public ReturnInfo calculateFormBetween(JtableContext var1);

    public FormulaCheckReturnInfo checkForm(JtableContext var1);

    public FormulaCheckReturnInfo checkFormBetween(JtableContext var1);

    @Deprecated
    public ReturnInfo dataSumForm(JtableContext var1);

    @Deprecated
    public boolean isFormCondition(JtableContext var1);

    public Map<String, String> analysisFormula(AnalysisFormulaInfo var1);

    public void previewFileByTaskKey(String var1, String var2, HttpServletResponse var3);

    public void previewFileByDataSchemeCode(String var1, String var2, HttpServletResponse var3);

    @Deprecated
    public CardInputInfo cardInputInit(CardInputInit var1);

    public Map<String, String> analysisFormulaByGridScript(JtableContext var1);

    @Deprecated
    public MultiPeriodDataSet queryMultiPeriodData(JtableContext var1);

    @Deprecated
    public SaveResult deleteFMDM(JtableContext var1);

    @Deprecated
    public RegionDataSet getFmdmData(RegionData var1, RegionQueryInfo var2);

    @Deprecated
    public RegionDataSet getFmdmData(RegionData var1, RegionQueryInfo var2, boolean var3);

    @Deprecated
    public Map<String, IFMDMAttribute> getFMDMAttribute(String var1, String var2);

    @Deprecated
    public SaveResult saveFmdmData(ReportDataCommitSet var1, RegionData var2, boolean var3);

    @Deprecated
    public List<FMDMCheckFailNodeInfoExtend> checkFMDMLinks(FMDMDataDTO var1, RegionData var2, JtableContext var3, boolean var4);
}

