/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 */
package com.jiuqi.nr.query.print.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryPrintParam
implements IPrintParamBase {
    private boolean printPageNum;
    private Map<String, String> measureMap = null;
    private String decimal;
    private boolean exportEmptyTable;
    private boolean emptyTable;
    private String formKey;
    private String secretLevelTitle;

    public String getFormSchemeKey() {
        return "";
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String fromKey) {
        this.formKey = fromKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return null;
    }

    public String getFormulaSchemeKey() {
        return "";
    }

    public boolean isPrintPageNum() {
        return this.printPageNum;
    }

    public void setPrintPageNum(boolean printPageNum) {
        this.printPageNum = printPageNum;
    }

    public List<String> getTabs() {
        return new ArrayList<String>();
    }

    public boolean isLabel() {
        return false;
    }

    public void setLabel(boolean label) {
    }

    public Map<String, String> getMeasureMap() {
        return this.measureMap;
    }

    public void setMeasureMap(Map<String, String> measureMap) {
        this.measureMap = measureMap;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getTaskKey() {
        return "";
    }

    public boolean isExportEmptyTable() {
        return this.exportEmptyTable;
    }

    public void setExportEmptyTable(boolean exportEmptyTable) {
        this.exportEmptyTable = exportEmptyTable;
    }

    public boolean isEmptyTable() {
        return this.emptyTable;
    }

    public void setEmptyTable(boolean emptyTable) {
        this.emptyTable = emptyTable;
    }

    public String getSecretLevelTitle() {
        return this.secretLevelTitle;
    }

    public void setSecretLevelTitle(String secretLevelTitle) {
        this.secretLevelTitle = secretLevelTitle;
    }
}

