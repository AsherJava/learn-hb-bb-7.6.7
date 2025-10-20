/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.vo;

import java.util.Map;

public class ExportExcelVO {
    public String index;
    public String ruleTitle;
    public String debitOrCredit;
    public String subjectCode;
    public String subjectTitle;
    public String formula;
    private String applyGcUnits;
    public String ruleCondition;
    public String ruleType;
    public String businessType;
    public String unit;
    public String startFlag;
    public String options;
    private Map<String, String> dimensions;

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getDebitOrCredit() {
        return this.debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getRuleCondition() {
        return this.ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String businessTypeCode) {
        this.businessType = businessTypeCode;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Map<String, String> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = dimensions;
    }

    public String getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getApplyGcUnits() {
        return this.applyGcUnits;
    }

    public void setApplyGcUnits(String applyGcUnits) {
        this.applyGcUnits = applyGcUnits;
    }
}

