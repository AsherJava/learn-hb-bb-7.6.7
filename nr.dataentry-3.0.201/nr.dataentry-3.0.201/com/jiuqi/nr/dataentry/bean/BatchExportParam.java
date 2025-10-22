/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nr.dataentry.bean.RegionFilterListInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.UUID;

public class BatchExportParam
implements IExportFacade {
    private JtableContext context;
    private boolean label;
    private boolean background;
    private boolean allForm;
    private boolean onlyStyle;
    private String sheetName;
    private String type;
    private boolean printCatalog;
    private String printSchemeKey;
    private boolean arithmeticBackground;
    private boolean arithmeticFormula;
    private boolean sumData;
    private boolean exportEmptyTable;
    private List<RegionFilterListInfo> regionFilterListInfo;

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
    public UUID getSyncTaskID() {
        return null;
    }

    @Override
    public List<String> getTabs() {
        return null;
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
        return this.arithmeticFormula;
    }

    @Override
    public boolean isExportAllLable() {
        return false;
    }

    public void setArithmeticFormula(boolean arithmeticFormula) {
        this.arithmeticFormula = arithmeticFormula;
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

