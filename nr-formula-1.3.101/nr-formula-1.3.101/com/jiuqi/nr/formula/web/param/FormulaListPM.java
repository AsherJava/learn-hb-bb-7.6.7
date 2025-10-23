/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.param;

import com.jiuqi.nr.formula.web.param.PageQuery;
import java.util.List;

public class FormulaListPM
extends PageQuery {
    private String unit;
    private String keywords;
    private List<Integer> formulaType;
    private List<Integer> checkType;
    private List<String> conditionCode;
    private String locationCode;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<Integer> getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(List<Integer> formulaType) {
        this.formulaType = formulaType;
    }

    public List<Integer> getCheckType() {
        return this.checkType;
    }

    public void setCheckType(List<Integer> checkType) {
        this.checkType = checkType;
    }

    public List<String> getConditionCode() {
        return this.conditionCode;
    }

    public void setConditionCode(List<String> conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}

