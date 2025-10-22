/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

public class EntityInfo {
    private int maxFiscalMonth;
    private int minFiscalMonth;
    private int minYear;
    private int maxYear;

    public EntityInfo(int maxFiscalMonth, int minFiscalMonth, int minYear, int maxYear) {
        this.maxFiscalMonth = maxFiscalMonth;
        this.minFiscalMonth = minFiscalMonth;
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }

    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    public int getMinYear() {
        return this.minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return this.maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }
}

