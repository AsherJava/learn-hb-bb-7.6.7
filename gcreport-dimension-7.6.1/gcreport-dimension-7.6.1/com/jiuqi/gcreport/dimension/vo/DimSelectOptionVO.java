/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

public class DimSelectOptionVO {
    private Object value;
    private String label;

    public DimSelectOptionVO() {
    }

    public DimSelectOptionVO(Object value, String label) {
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

