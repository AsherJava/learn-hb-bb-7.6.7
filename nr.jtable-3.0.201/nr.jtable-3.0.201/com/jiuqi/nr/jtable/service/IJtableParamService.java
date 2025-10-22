/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormFoldingDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.jtable.params.base.CStyleFile;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaConditionFile;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionNumber;
import com.jiuqi.nr.jtable.params.base.RegionSettingData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.base.TableData;
import com.jiuqi.nr.jtable.params.input.FieldQueryInfo;
import com.jiuqi.nr.jtable.params.input.FormulaQueryInfo;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import com.jiuqi.nr.jtable.params.output.FormTableFields;
import com.jiuqi.nr.jtable.params.output.FormTables;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface IJtableParamService {
    public FormData getReport(String var1, String var2);

    public String getReportType(String var1);

    public Grid2Data getGridData(String var1);

    public String getSurveyData(String var1, String var2, String var3);

    public String getSurveyCardStyle(String var1, String var2, String var3, String var4, String var5);

    public List<FormData> getAllReportsByFormScheme(String var1);

    public Map<String, Map<String, FormFoldingDefine>> getFormFoldData(String var1);

    public List<RegionData> getRegions(String var1);

    public RegionData getRegion(String var1);

    public List<RegionTab> getRegionTabs(String var1);

    public RegionSettingData getRegionSetting(String var1);

    @Deprecated
    public List<RegionNumber> getRegionNumbers(String var1);

    public RegionNumber getRegionNumber(String var1);

    public List<LinkData> getLinks(String var1);

    public LinkData getLink(String var1);

    public EntityViewData getEntity(String var1);

    public EntityViewData getDwEntity(String var1);

    public EntityViewData getDwEntity(String var1, boolean var2);

    public EntityViewData getDataTimeEntity(String var1);

    public List<EntityViewData> getDimEntityList(String var1);

    public List<EntityViewData> getEntityList(String var1);

    public List<EntityViewData> getEntityList(List<String> var1);

    public FormulaSchemeData getFormulaScheme(String var1);

    public List<FormulaSchemeData> getFormulaSchemeDatasByFormScheme(String var1);

    public FormulaSchemeData getSoluctionByDimensions(JtableContext var1);

    public String getCalculateFormulaJs(String var1, String var2);

    public List<FormulaData> getFormulaList(FormulaQueryInfo var1);

    public HashMap<String, Integer> getFormulaListSize(FormulaQueryInfo var1);

    public HashMap<String, String> getFormulaMeanings(String var1, String var2, String var3, String var4) throws Exception;

    public String getCurFormula(FormulaQueryInfo var1);

    public FormTableFields getForm(String var1, String var2);

    public FieldData getField(String var1);

    public TableData getTable(String var1);

    public TableData queryTableDefineByCode(String var1);

    public List<FieldData> getALLFileField(String var1);

    public IFMDMAttribute getFmdmParentField(String var1, String var2);

    public List<String> getCalcDataLinks(JtableContext var1);

    public List<String> getExtractDataLinkList(JtableContext var1);

    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext var1, String var2);

    public List<TableData> getAllTableInRegion(String var1);

    public FieldData getFieldByCodeInTable(String var1, String var2);

    public FormTables formfields(FieldQueryInfo var1);

    public String getDeployUpdate(String var1);

    public HashSet<String> getConditionFieldsByFormScheme(String var1);

    public CStyleFile getStyleFormulaJs(String var1, String var2);

    public int getDefaultDecimal(String var1);

    public FormulaConditionFile getFormulaConditionJs(String var1, String var2, String var3);

    public FormulaConditionFile getFormulaConditionJs(JtableContext var1);
}

