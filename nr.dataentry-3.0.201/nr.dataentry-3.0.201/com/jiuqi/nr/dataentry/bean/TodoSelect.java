/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.io.Serializable;
import java.util.List;

public class TodoSelect
implements Serializable {
    private String rangeUnit;
    private List<String> rangeForms;
    private List<String> rangeGroups;

    public String getRangeUnit() {
        return this.rangeUnit;
    }

    public void setRangeUnit(String rangeUnit) {
        this.rangeUnit = rangeUnit;
    }

    public List<String> getRangeForms() {
        return this.rangeForms;
    }

    public void setRangeForms(List<String> rangeForms) {
        this.rangeForms = rangeForms;
    }

    public List<String> getRangeGroups() {
        return this.rangeGroups;
    }

    public void setRangeGroups(List<String> rangeGroups) {
        this.rangeGroups = rangeGroups;
    }
}

