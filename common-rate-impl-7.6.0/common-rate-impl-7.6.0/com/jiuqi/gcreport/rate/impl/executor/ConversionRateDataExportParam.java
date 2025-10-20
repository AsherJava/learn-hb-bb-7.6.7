/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.executor;

class ConversionRateDataExportParam {
    private Boolean rootFlag;
    private String rateSchemeCode;
    private String periodType;
    private String periodStrStart;
    private String periodStrEnd;
    private String sourceCurrencyCode;
    private String targetCurrencyCode;

    ConversionRateDataExportParam() {
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public Boolean getRootFlag() {
        return this.rootFlag;
    }

    public void setRootFlag(Boolean rootFlag) {
        this.rootFlag = rootFlag;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStrStart() {
        return this.periodStrStart;
    }

    public void setPeriodStrStart(String periodStrStart) {
        this.periodStrStart = periodStrStart;
    }

    public String getPeriodStrEnd() {
        return this.periodStrEnd;
    }

    public void setPeriodStrEnd(String getPeriodStrEnd) {
        this.periodStrEnd = getPeriodStrEnd;
    }

    public String getSourceCurrencyCode() {
        return this.sourceCurrencyCode;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }
}

