/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.bean;

public class PeriodMapping {
    private String key;
    private String msKey;
    private String period;
    private String mapping;

    public PeriodMapping() {
    }

    public PeriodMapping(String key, String msKey, String period, String mapping) {
        this.key = key;
        this.msKey = msKey;
        this.period = period;
        this.mapping = mapping;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
}

