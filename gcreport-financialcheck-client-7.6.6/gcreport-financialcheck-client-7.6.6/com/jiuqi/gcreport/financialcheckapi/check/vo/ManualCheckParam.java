/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import java.util.Set;

public class ManualCheckParam {
    private Set<String> itemIds;
    private int manualCheckType;
    private int year;
    private int period;

    public Set<String> getItemIds() {
        return this.itemIds;
    }

    public void setItemIds(Set<String> itemIds) {
        this.itemIds = itemIds;
    }

    public int getManualCheckType() {
        return this.manualCheckType;
    }

    public void setManualCheckType(int manualCheckType) {
        this.manualCheckType = manualCheckType;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}

