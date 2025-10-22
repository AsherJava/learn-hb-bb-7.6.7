/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common;

public class SelectOptionVO {
    private Object value;
    private String label;

    public SelectOptionVO() {
    }

    public SelectOptionVO(Object value, String label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        return "SelectOptionVO [value=" + this.value + ", label=" + this.label + "]";
    }
}

