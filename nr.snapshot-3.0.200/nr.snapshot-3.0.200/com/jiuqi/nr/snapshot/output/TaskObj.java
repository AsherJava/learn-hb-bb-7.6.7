/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.output;

import com.jiuqi.nr.snapshot.output.PeriodObj;

public class TaskObj {
    private String key;
    private String title;
    private String dataSchemeKey;
    private String fromPeriod;
    private String toPeriod;
    private PeriodObj periodObj;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public PeriodObj getPeriodObj() {
        return this.periodObj;
    }

    public void setPeriodObj(PeriodObj periodObj) {
        this.periodObj = periodObj;
    }
}

