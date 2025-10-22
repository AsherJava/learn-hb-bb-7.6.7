/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.common;

import java.math.BigDecimal;

public class GcConversionIndexRateInfo {
    private String rateSchemeCode;
    private String taskId;
    private String schemeId;
    private String formId;
    private String indexId;
    private String sourceCurrecyCode;
    private String targeCurrencyCode;
    private String rateTypeCode;
    private String rateFormula;
    private BigDecimal rateValue;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getIndexId() {
        return this.indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getSourceCurrecyCode() {
        return this.sourceCurrecyCode;
    }

    public void setSourceCurrecyCode(String sourceCurrecyCode) {
        this.sourceCurrecyCode = sourceCurrecyCode;
    }

    public String getTargeCurrencyCode() {
        return this.targeCurrencyCode;
    }

    public void setTargeCurrencyCode(String targeCurrencyCode) {
        this.targeCurrencyCode = targeCurrencyCode;
    }

    public String getRateTypeCode() {
        return this.rateTypeCode;
    }

    public void setRateTypeCode(String rateTypeCode) {
        this.rateTypeCode = rateTypeCode;
    }

    public String getRateFormula() {
        return this.rateFormula;
    }

    public void setRateFormula(String rateFormula) {
        this.rateFormula = rateFormula;
    }

    public BigDecimal getRateValue() {
        return this.rateValue;
    }

    public void setRateValue(BigDecimal rateValue) {
        this.rateValue = rateValue;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}

