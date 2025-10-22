/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.offset;

public class MdAgingDTO {
    private String code;
    private String title;
    private String periodType;
    private Integer beginPeriod;
    private Integer endPeriod;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBeginPeriod() {
        return this.beginPeriod;
    }

    public void setBeginPeriod(Integer beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public Integer getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Integer endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}

