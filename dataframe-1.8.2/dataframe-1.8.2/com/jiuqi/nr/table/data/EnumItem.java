/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

public class EnumItem {
    public static final String SPERATE = "|";
    private String code;
    private String title;

    public EnumItem() {
    }

    public EnumItem(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return this.code + SPERATE + this.title;
    }
}

