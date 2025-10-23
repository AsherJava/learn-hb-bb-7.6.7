/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

public class DataSchemeCalcTask {
    private String dataSchemeKey;
    private String startPeriod;
    private String endPeriod;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public DataSchemeCalcTask(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String toString() {
        return "DataSchemeCalcTask [dataSchemeKey=" + this.dataSchemeKey + ", startPeriod=" + this.startPeriod + ", endPeriod=" + this.endPeriod + "]";
    }
}

