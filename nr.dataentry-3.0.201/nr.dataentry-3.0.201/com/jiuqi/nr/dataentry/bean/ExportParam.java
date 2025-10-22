/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.CellQueryInfo
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.dataentry.bean.ExportPdfType;
import com.jiuqi.nr.dataentry.bean.ExportRuleSettings;
import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.dataentry.paramInfo.PrintFilterInfo;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ExportParam
extends JtableLog
implements IExportFacade,
INRContext {
    private static final long serialVersionUID = 1L;
    private UUID syncTaskID;
    private JtableContext context;
    private boolean label;
    private boolean background;
    private boolean allCorp;
    private boolean onlyStyle;
    private boolean sumData;
    private boolean exportEmptyTable;
    private boolean exportETFile;
    private boolean exportZero;
    private String exportWorkDir;
    private boolean expExcelDirSheet;
    private String sheetName;
    private String type;
    private boolean printCatalog;
    private List<String> tabs;
    private String configKey;
    private String printSchemeKey;
    private boolean arithmeticBackground;
    private boolean arithmeticFormula;
    private Map<String, List<CellQueryInfo>> conditions;
    private String splitMark;
    private boolean exportAllForms = false;
    private ExportPdfType exportPdfType = ExportPdfType.EXPORT_PDF;
    private boolean exportAllLable = false;
    private PrintFilterInfo printFilterInfo;
    private List<RegionFilterListInfo> regionFilterListInfo;
    private Map<String, RegionGradeInfo> gradeInfos;
    private ExportRuleSettings ruleSettings;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public boolean isExpExcelDirSheet() {
        return this.expExcelDirSheet;
    }

    public void setExpExcelDirSheet(boolean expExcelDirSheet) {
        this.expExcelDirSheet = expExcelDirSheet;
    }

    public List<Variable> getVariables() {
        return this.context.getVariables();
    }

    public String getSplitMark() {
        return this.splitMark;
    }

    public void setSplitMark(String splitMark) {
        this.splitMark = splitMark;
    }

    @Override
    public UUID getSyncTaskID() {
        return this.syncTaskID;
    }

    public void setSyncTaskID(UUID syncTaskID) {
        this.syncTaskID = syncTaskID;
    }

    @Override
    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    @Override
    public boolean isLabel() {
        return this.label;
    }

    public void setLabel(boolean label) {
        this.label = label;
    }

    @Override
    public boolean isBackground() {
        return this.background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public boolean isAllForm() {
        String formKey = this.context.getFormKey();
        return null == formKey || "".equals(formKey);
    }

    @Override
    public boolean isOnlyStyle() {
        return this.onlyStyle;
    }

    public void setOnlyStyle(boolean onlyStyle) {
        this.onlyStyle = onlyStyle;
    }

    @Override
    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isPrintCatalog() {
        return this.printCatalog;
    }

    public void setPrintCatalog(boolean printCatalog) {
        this.printCatalog = printCatalog;
    }

    @Override
    public List<String> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Override
    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public boolean isAllCorp() {
        return this.allCorp;
    }

    public void setAllCorp(boolean allCorp) {
        this.allCorp = allCorp;
    }

    public String getFormKeys() {
        return this.context.getFormKey();
    }

    @Override
    public boolean isArithmeticBackground() {
        return this.arithmeticBackground;
    }

    public void setArithmeticBackground(boolean arithmeticBackground) {
        this.arithmeticBackground = arithmeticBackground;
    }

    @Override
    public boolean isArithmeticFormula() {
        return this.arithmeticFormula;
    }

    public void setArithmeticFormula(boolean arithmeticFormula) {
        this.arithmeticFormula = arithmeticFormula;
    }

    @Override
    public Map<String, List<CellQueryInfo>> getConditions() {
        return this.conditions;
    }

    public void setConditions(Map<String, List<CellQueryInfo>> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean isSumData() {
        return this.sumData;
    }

    public void setSumData(boolean sumData) {
        this.sumData = sumData;
    }

    @Override
    public boolean isExportEmptyTable() {
        return this.exportEmptyTable;
    }

    public void setExportEmptyTable(boolean exportEmptyTable) {
        this.exportEmptyTable = exportEmptyTable;
    }

    public boolean isExportAllForms() {
        return this.exportAllForms;
    }

    public void setExportAllForms(boolean exportAllForms) {
        this.exportAllForms = exportAllForms;
    }

    public boolean isExportETFile() {
        return this.exportETFile;
    }

    public void setExportETFile(boolean exportETFile) {
        this.exportETFile = exportETFile;
    }

    public ExportPdfType getExportPdfType() {
        return this.exportPdfType;
    }

    public void setExportPdfType(ExportPdfType exportPdfType) {
        this.exportPdfType = exportPdfType;
    }

    @Override
    public boolean isExportAllLable() {
        return this.exportAllLable;
    }

    public void setExportAllLable(boolean exportAllLable) {
        this.exportAllLable = exportAllLable;
    }

    public PrintFilterInfo getPrintFilterInfo() {
        return this.printFilterInfo;
    }

    public void setPrintFilterInfo(PrintFilterInfo printFilterInfo) {
        this.printFilterInfo = printFilterInfo;
    }

    @Override
    public List<RegionFilterListInfo> getRegionFilterListInfo() {
        return this.regionFilterListInfo;
    }

    public void setRegionFilterListInfo(List<RegionFilterListInfo> regionFilterListInfo) {
        this.regionFilterListInfo = regionFilterListInfo;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public Map<String, RegionGradeInfo> getGradeInfos() {
        return this.gradeInfos;
    }

    public void setGradeInfos(Map<String, RegionGradeInfo> gradeInfos) {
        this.gradeInfos = gradeInfos;
    }

    public ExportRuleSettings getRuleSettings() {
        return this.ruleSettings;
    }

    public void setRuleSettings(ExportRuleSettings ruleSettings) {
        this.ruleSettings = ruleSettings;
    }

    public boolean isExportZero() {
        return this.exportZero;
    }

    public void setExportZero(boolean exportZero) {
        this.exportZero = exportZero;
    }

    public String getExportWorkDir() {
        return this.exportWorkDir;
    }

    public void setExportWorkDir(String exportWorkDir) {
        this.exportWorkDir = exportWorkDir;
    }
}

