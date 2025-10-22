/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.data.excel.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.data.excel.param.CellQueryInfo;
import com.jiuqi.nr.data.excel.param.DataExcelContext;
import com.jiuqi.nr.data.excel.param.PrintFilterInfo;
import com.jiuqi.nr.data.excel.param.RegionFilterListInfo;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataExportParam {
    private static final long serialVersionUID = 1L;
    private UUID syncTaskID;
    private DataExcelContext context;
    private boolean label;
    private boolean background;
    private boolean allCorp;
    private boolean onlyStyle;
    private boolean sumData;
    private boolean exportEmptyTable;
    private boolean exportETFile;
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
    private boolean exportAllLable = false;
    private PrintFilterInfo printFilterInfo;
    private List<RegionFilterListInfo> regionFilterListInfo;

    public String getSplitMark() {
        return this.splitMark;
    }

    public void setSplitMark(String splitMark) {
        this.splitMark = splitMark;
    }

    public UUID getSyncTaskID() {
        return this.syncTaskID;
    }

    public void setSyncTaskID(UUID syncTaskID) {
        this.syncTaskID = syncTaskID;
    }

    public DataExcelContext getContext() {
        return this.context;
    }

    public void setContext(DataExcelContext context) {
        this.context = context;
    }

    public boolean isLabel() {
        return this.label;
    }

    public void setLabel(boolean label) {
        this.label = label;
    }

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

    public boolean isOnlyStyle() {
        return this.onlyStyle;
    }

    public void setOnlyStyle(boolean onlyStyle) {
        this.onlyStyle = onlyStyle;
    }

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

    public boolean isPrintCatalog() {
        return this.printCatalog;
    }

    public void setPrintCatalog(boolean printCatalog) {
        this.printCatalog = printCatalog;
    }

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

    public boolean isArithmeticBackground() {
        return this.arithmeticBackground;
    }

    public void setArithmeticBackground(boolean arithmeticBackground) {
        this.arithmeticBackground = arithmeticBackground;
    }

    public boolean isArithmeticFormula() {
        return this.arithmeticFormula;
    }

    public void setArithmeticFormula(boolean arithmeticFormula) {
        this.arithmeticFormula = arithmeticFormula;
    }

    public Map<String, List<CellQueryInfo>> getConditions() {
        return this.conditions;
    }

    public void setConditions(Map<String, List<CellQueryInfo>> conditions) {
        this.conditions = conditions;
    }

    public boolean isSumData() {
        return this.sumData;
    }

    public void setSumData(boolean sumData) {
        this.sumData = sumData;
    }

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

    public List<RegionFilterListInfo> getRegionFilterListInfo() {
        return this.regionFilterListInfo;
    }

    public void setRegionFilterListInfo(List<RegionFilterListInfo> regionFilterListInfo) {
        this.regionFilterListInfo = regionFilterListInfo;
    }
}

