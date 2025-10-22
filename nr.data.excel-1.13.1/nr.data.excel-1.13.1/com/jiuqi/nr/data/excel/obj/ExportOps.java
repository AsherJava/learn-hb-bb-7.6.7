/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nr.data.excel.obj.CustomGridCellStyle;
import com.jiuqi.nr.data.excel.obj.ExpFormFolding;
import com.jiuqi.nr.data.excel.obj.ExportMeasureSetting;
import com.jiuqi.nr.data.excel.param.CellQueryInfo;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportOps
implements Serializable {
    private static final long serialVersionUID = -2500950528630142576L;
    private boolean et;
    private boolean expCellBColor;
    private boolean onlyStyle;
    private boolean emptyForm;
    private boolean exp0Form;
    private boolean sumData;
    private String printSchemeKey;
    private String formulaSchemeKey;
    private boolean expFml;
    private ExportMeasureSetting exportMeasureSetting;
    private boolean expAllTabs;
    private Map<String, List<String>> tabs = new HashMap<String, List<String>>();
    private Map<String, List<RowFilter>> rowFilter = new HashMap<String, List<RowFilter>>();
    private Map<String, List<LinkSort>> linkSort = new HashMap<String, List<LinkSort>>();
    private Map<String, List<CellQueryInfo>> conditions = new HashMap<String, List<CellQueryInfo>>();
    private Map<String, RegionGradeInfo> gradeInfos = new HashMap<String, RegionGradeInfo>();
    private TitleShowSetting titleShowSetting;
    private boolean label;
    private boolean expExcelDirSheet;
    private Boolean thousands;
    private Integer displayDecimalPlaces;
    private Map<String, List<ExpFormFolding>> expFormFoldings;
    private Boolean expEnumDropDown;

    @Deprecated
    public Map<String, CustomGridCellStyle> getCellStyles() {
        return null;
    }

    @Deprecated
    public void setCellStyles(Map<String, CustomGridCellStyle> cellStyles) {
    }

    public boolean isExpFml() {
        return this.expFml;
    }

    public void setExpFml(boolean expFml) {
        this.expFml = expFml;
    }

    public Map<String, List<LinkSort>> getLinkSort() {
        return this.linkSort == null ? Collections.emptyMap() : this.linkSort;
    }

    public void setLinkSort(Map<String, List<LinkSort>> linkSort) {
        this.linkSort = linkSort;
    }

    public boolean isEt() {
        return this.et;
    }

    public void setEt(boolean et) {
        this.et = et;
    }

    public boolean isOnlyStyle() {
        return this.onlyStyle;
    }

    public void setOnlyStyle(boolean onlyStyle) {
        this.onlyStyle = onlyStyle;
    }

    public boolean isEmptyForm() {
        return this.emptyForm;
    }

    public void setEmptyForm(boolean emptyForm) {
        this.emptyForm = emptyForm;
    }

    public boolean isSumData() {
        return this.sumData;
    }

    public void setSumData(boolean sumData) {
        this.sumData = sumData;
    }

    public Map<String, List<String>> getTabs() {
        return this.tabs;
    }

    public void setTabs(Map<String, List<String>> tabs) {
        this.tabs = tabs;
    }

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public ExportMeasureSetting getExportMeasureSetting() {
        return this.exportMeasureSetting;
    }

    public void setExportMeasureSetting(ExportMeasureSetting exportMeasureSetting) {
        this.exportMeasureSetting = exportMeasureSetting;
    }

    public Map<String, List<RowFilter>> getRowFilter() {
        return this.rowFilter == null ? Collections.emptyMap() : this.rowFilter;
    }

    public void setRowFilter(Map<String, List<RowFilter>> rowFilter) {
        this.rowFilter = rowFilter;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Map<String, List<CellQueryInfo>> getConditions() {
        return this.conditions;
    }

    public void setConditions(Map<String, List<CellQueryInfo>> conditions) {
        this.conditions = conditions;
    }

    public boolean isExpCellBColor() {
        return this.expCellBColor;
    }

    public void setExpCellBColor(boolean expCellBColor) {
        this.expCellBColor = expCellBColor;
    }

    public boolean isExpAllTabs() {
        return this.expAllTabs;
    }

    public void setExpAllTabs(boolean expAllTabs) {
        this.expAllTabs = expAllTabs;
    }

    public TitleShowSetting getTitleShowSetting() {
        return this.titleShowSetting;
    }

    public void setTitleShowSetting(TitleShowSetting titleShowSetting) {
        this.titleShowSetting = titleShowSetting;
    }

    public boolean isLabel() {
        return this.label;
    }

    public void setLabel(boolean label) {
        this.label = label;
    }

    public boolean isExpExcelDirSheet() {
        return this.expExcelDirSheet;
    }

    public void setExpExcelDirSheet(boolean expExcelDirSheet) {
        this.expExcelDirSheet = expExcelDirSheet;
    }

    public Boolean getThousands() {
        return this.thousands;
    }

    public void setThousands(Boolean thousands) {
        this.thousands = thousands;
    }

    public Map<String, RegionGradeInfo> getGradeInfos() {
        return this.gradeInfos;
    }

    public void setGradeInfos(Map<String, RegionGradeInfo> gradeInfos) {
        this.gradeInfos = gradeInfos;
    }

    public Map<String, List<ExpFormFolding>> getExpFormFoldings() {
        return this.expFormFoldings;
    }

    public void setExpFormFoldings(Map<String, List<ExpFormFolding>> expFormFoldings) {
        this.expFormFoldings = expFormFoldings;
    }

    public boolean isExp0Form() {
        return this.exp0Form;
    }

    public void setExp0Form(boolean exp0Form) {
        this.exp0Form = exp0Form;
    }

    public Integer getDisplayDecimalPlaces() {
        return this.displayDecimalPlaces;
    }

    public void setDisplayDecimalPlaces(Integer displayDecimalPlaces) {
        this.displayDecimalPlaces = displayDecimalPlaces;
    }

    public Boolean getExpEnumDropDown() {
        return this.expEnumDropDown;
    }

    public void setExpEnumDropDown(Boolean expEnumDropDown) {
        this.expEnumDropDown = expEnumDropDown;
    }
}

