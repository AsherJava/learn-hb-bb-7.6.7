/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.entity;

import java.math.BigDecimal;
import java.util.Map;

public class CommonRateInfoEO {
    public static final String TABLENAME = "MD_ENT_RATE";
    private String id;
    private String code;
    private String name;
    private String rateSchemeCode;
    private String dataTime;
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private Map<String, BigDecimal> rateInfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, BigDecimal> getRateInfo() {
        return this.rateInfo;
    }

    public void setRateInfo(Map<String, BigDecimal> rateInfo) {
        this.rateInfo = rateInfo;
    }
}

