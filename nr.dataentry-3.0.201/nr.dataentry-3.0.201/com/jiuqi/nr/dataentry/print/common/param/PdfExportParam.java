/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.print.common.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PdfExportParam
implements IExportFacade {
    private UUID syncTaskID;
    private JtableContext context;
    private boolean label;
    private boolean background;
    private boolean allForm;
    private boolean onlyStyle;
    private boolean printCatalog;
    private String sheetName;
    private String type;
    private List<String> tabs;
    private String printSchemeKey;
    private boolean arithmeticBackground;
    private boolean sumData;
    private boolean exportEmptyTable = true;
    private List<RegionFilterListInfo> regionFilterListInfo;
    private Map<String, String> measureMap = new HashMap<String, String>();

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
        return this.allForm;
    }

    public void setAllForm(boolean allForm) {
        this.allForm = allForm;
    }

    @Override
    public boolean isOnlyStyle() {
        return this.onlyStyle;
    }

    public void setOnlyStyle(boolean onlyStyle) {
        this.onlyStyle = onlyStyle;
    }

    @Override
    public boolean isPrintCatalog() {
        return this.printCatalog;
    }

    public void setPrintCatalog(boolean printCatalog) {
        this.printCatalog = printCatalog;
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
    public List<String> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    @Override
    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
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
        return false;
    }

    @Override
    public boolean isExportAllLable() {
        return false;
    }

    @Override
    public boolean isSumData() {
        return this.sumData;
    }

    public void setSumData(boolean sumData) {
        this.sumData = sumData;
    }

    public Map<String, String> getMeasureMap() {
        return this.measureMap;
    }

    public void setMeasureMap(Map<String, String> measureMap) {
        this.measureMap = measureMap;
    }

    @Override
    public boolean isExportEmptyTable() {
        return this.exportEmptyTable;
    }

    @Override
    public List<RegionFilterListInfo> getRegionFilterListInfo() {
        return this.regionFilterListInfo;
    }

    public void setRegionFilterListInfo(List<RegionFilterListInfo> regionFilterListInfo) {
        this.regionFilterListInfo = regionFilterListInfo;
    }

    public void setExportEmptyTable(boolean exportEmptyTable) {
        this.exportEmptyTable = exportEmptyTable;
    }
}

