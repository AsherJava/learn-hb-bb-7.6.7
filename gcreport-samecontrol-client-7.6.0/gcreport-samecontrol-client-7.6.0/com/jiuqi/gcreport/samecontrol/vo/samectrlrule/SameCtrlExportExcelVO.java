/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlrule;

public class SameCtrlExportExcelVO {
    private String index;
    private String ruleTitle;
    private String debitOrCredit;
    private String subjectCode;
    private String subjectTitle;
    private String formula;
    private String ruleCondition;
    private String ruleType;
    private String businessType;
    private String unit;
    private String startFlag;

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

    public String getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public String toString() {
        return "SameCtrlExportExcelVO{index='" + this.index + '\'' + ", ruleTitle='" + this.ruleTitle + '\'' + ", debitOrCredit='" + this.debitOrCredit + '\'' + ", subjectCode='" + this.subjectCode + '\'' + ", subjectTitle='" + this.subjectTitle + '\'' + ", formula='" + this.formula + '\'' + ", ruleCondition='" + this.ruleCondition + '\'' + ", ruleType='" + this.ruleType + '\'' + ", businessType='" + this.businessType + '\'' + ", unit='" + this.unit + '\'' + ", startFlag='" + this.startFlag + '\'' + '}';
    }
}

