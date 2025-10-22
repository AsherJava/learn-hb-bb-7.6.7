/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class AdjustPeriodRow {
    private String dataSchemeKey;
    private String period;
    private String periodTitle;
    private String code;
    private String title;
    private String nReCode;

    public AdjustPeriodRow(String dataSchemeKey, String period, String code, String title) {
        this.dataSchemeKey = dataSchemeKey;
        this.period = period;
        this.code = code;
        this.title = title;
    }

    public AdjustPeriodRow() {
    }

    public String getnReCode() {
        return this.nReCode;
    }

    public void setnReCode(String nReCode) {
        this.nReCode = nReCode;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

