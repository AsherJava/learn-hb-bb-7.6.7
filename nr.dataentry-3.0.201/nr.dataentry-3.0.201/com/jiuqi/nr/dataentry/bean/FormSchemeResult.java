/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.dataentry.bean.FormulaVariable;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import java.util.List;
import java.util.Map;

public class FormSchemeResult {
    private FormSchemeData scheme;
    private List<CustomPeriodData> customPeriodDatas;
    private Map<String, DimensionValue> dimensionSet;
    private List<FormulaVariable> formulaVariables;
    private List<String> reportDimensionList;
    private boolean openAdJustPeriod;
    private boolean existCurrencyAttributes;

    public List<FormulaVariable> getFormulaVariables() {
        return this.formulaVariables;
    }

    public void setFormulaVariables(List<FormulaVariable> formulaVariables) {
        this.formulaVariables = formulaVariables;
    }

    public FormSchemeResult(FormSchemeData scheme) {
        this.scheme = scheme;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public FormSchemeData getScheme() {
        return this.scheme;
    }

    public void setScheme(FormSchemeData scheme) {
        this.scheme = scheme;
    }

    public List<CustomPeriodData> getCustomPeriodDatas() {
        return this.customPeriodDatas;
    }

    public void setCustomPeriodDatas(List<CustomPeriodData> customPeriodDatas) {
        this.customPeriodDatas = customPeriodDatas;
    }

    public List<String> getReportDimensionList() {
        return this.reportDimensionList;
    }

    public void setReportDimensionList(List<String> reportDimensionList) {
        this.reportDimensionList = reportDimensionList;
    }

    public boolean isOpenAdJustPeriod() {
        return this.openAdJustPeriod;
    }

    public void setOpenAdJustPeriod(boolean openAdJustPeriod) {
        this.openAdJustPeriod = openAdJustPeriod;
    }

    public boolean isExistCurrencyAttributes() {
        return this.existCurrencyAttributes;
    }

    public void setExistCurrencyAttributes(boolean existCurrencyAttributes) {
        this.existCurrencyAttributes = existCurrencyAttributes;
    }
}

