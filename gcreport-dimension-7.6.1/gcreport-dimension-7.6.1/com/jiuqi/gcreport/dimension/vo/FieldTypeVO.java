/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

public class FieldTypeVO {
    private int value;
    private String title;

    public FieldTypeVO(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "FieldTypeVO [value=" + this.value + ", title=" + this.title + "]";
    }
}

