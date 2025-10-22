/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.common;

public class GcConversionRate {
    private String taskId;
    private String taskCode;
    private String formCode;
    private String formKey;
    private String formulaSchemeKeys;
    private String formFieldCode;
    private String formFieldKey;
    private String rateTypeCode;
    private Double rateValue;
    private String rateFormula;

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormFieldKey() {
        return this.formFieldKey;
    }

    public void setFormFieldKey(String formFieldKey) {
        this.formFieldKey = formFieldKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormFieldCode() {
        return this.formFieldCode;
    }

    public void setFormFieldCode(String formFieldCode) {
        this.formFieldCode = formFieldCode;
    }

    public String getRateTypeCode() {
        return this.rateTypeCode;
    }

    public void setRateTypeCode(String rateTypeCode) {
        this.rateTypeCode = rateTypeCode;
    }

    public Double getRateValue() {
        return this.rateValue;
    }

    public void setRateValue(Double rateValue) {
        this.rateValue = rateValue;
    }

    public String getRateFormula() {
        return this.rateFormula;
    }

    public void setRateFormula(String rateFormula) {
        this.rateFormula = rateFormula;
    }
}

