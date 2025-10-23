/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.web.vo;

public class SelectOptionVO {
    private String label;
    private String value;

    public SelectOptionVO() {
    }

    public SelectOptionVO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

