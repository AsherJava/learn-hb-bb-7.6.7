/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.bus;

import java.math.BigDecimal;
import java.util.Map;

public class ConversionRateSyncBusParam {
    private String rateSchemeCode;
    private Integer year;
    private Integer period;
    private String srcCurrency;
    private String targetCurrency;
    private Map<String, BigDecimal> item;

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public String getSrcCurrency() {
        return this.srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }

    public String getTargetCurrency() {
        return this.targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Map<String, BigDecimal> getItem() {
        return this.item;
    }

    public void setItem(Map<String, BigDecimal> item) {
        this.item = item;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}

