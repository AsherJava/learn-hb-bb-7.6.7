/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.function;

import java.io.Serializable;

public class RateValueFunctionContext
implements Serializable {
    public static final String VARIABLE_NAME = "RateValueFunctionContext";
    private String rateSchemeCode;
    private String schemeId;
    private String beforeCurrencyCode;
    private String afterCurrencyCode;
    private String periodStr;

    public RateValueFunctionContext() {
    }

    public RateValueFunctionContext(String rateSchemeCode, String schemeId, String beforeCurrencyCode, String afterCurrencyCode, String periodStr) {
        this.rateSchemeCode = rateSchemeCode;
        this.schemeId = schemeId;
        this.beforeCurrencyCode = beforeCurrencyCode;
        this.afterCurrencyCode = afterCurrencyCode;
        this.periodStr = periodStr;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getBeforeCurrencyCode() {
        return this.beforeCurrencyCode;
    }

    public void setBeforeCurrencyCode(String beforeCurrencyCode) {
        this.beforeCurrencyCode = beforeCurrencyCode;
    }

    public String getAfterCurrencyCode() {
        return this.afterCurrencyCode;
    }

    public void setAfterCurrencyCode(String afterCurrencyCode) {
        this.afterCurrencyCode = afterCurrencyCode;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}

