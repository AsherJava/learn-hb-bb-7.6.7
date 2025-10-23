/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.period.select.common.KeyType;
import com.jiuqi.nr.period.select.common.RunType;

public class ModuleObj {
    private String key;
    private KeyType keyType = KeyType.TASK;
    private RunType runType = RunType.RUNTIME;
    private boolean fillingDue = false;
    private String periodType;
    private String year;
    private String month;

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public boolean isFillingDue() {
        return this.fillingDue;
    }

    public void setFillingDue(boolean fillingDue) {
        this.fillingDue = fillingDue;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public KeyType getKeyType() {
        return this.keyType;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    public RunType getRunType() {
        return this.runType;
    }

    public void setRunType(RunType runType) {
        this.runType = runType;
    }
}

