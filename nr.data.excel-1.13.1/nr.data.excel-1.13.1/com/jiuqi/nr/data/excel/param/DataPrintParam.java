/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import java.util.List;
import java.util.Map;

public class DataPrintParam
implements IPrintParamBase {
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private DimensionValueSet dimensionValueSet;
    private String formulaSchemeKey;
    private boolean printPageNum;
    private List<String> tabs;
    private boolean label;
    private Map<String, String> meassureMap;
    private String decimal;
    private boolean exportEmptyTable;
    private boolean emptyTable;
    private String secretLevelTitle;

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public boolean isPrintPageNum() {
        return this.printPageNum;
    }

    public List<String> getTabs() {
        return this.tabs;
    }

    public boolean isLabel() {
        return this.label;
    }

    public void setLabel(boolean label) {
        this.label = label;
    }

    public Map<String, String> getMeasureMap() {
        return this.meassureMap;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public boolean isExportEmptyTable() {
        return this.exportEmptyTable;
    }

    public void setEmptyTable(boolean emptyTable) {
        this.emptyTable = emptyTable;
    }

    public boolean isEmptyTable() {
        return this.emptyTable;
    }

    public String getSecretLevelTitle() {
        return this.secretLevelTitle;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public void setPrintPageNum(boolean printPageNum) {
        this.printPageNum = printPageNum;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    public void setMeassureMap(Map<String, String> meassureMap) {
        this.meassureMap = meassureMap;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public void setExportEmptyTable(boolean exportEmptyTable) {
        this.exportEmptyTable = exportEmptyTable;
    }

    public void setSecretLevelTitle(String secretLevelTitle) {
        this.secretLevelTitle = secretLevelTitle;
    }
}

