/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.rate.client.vo;

import java.util.List;

public class RateQueryParam {
    private String rateSchemeCode;
    private String periodStrStart;
    private String periodStrEnd;
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private int pageSize;
    private int pageNum;
    private int year;
    private List<String> subjects;

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
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

    public void setPeriodStrEnd(String periodStrEnd) {
        this.periodStrEnd = periodStrEnd;
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

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}

