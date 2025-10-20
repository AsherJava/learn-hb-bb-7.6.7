/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public class EnumItemVO {
    private String title;
    private String value;

    public EnumItemVO(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "EnumItemVO{title='" + this.title + '\'' + ", value='" + this.value + '\'' + '}';
    }
}

