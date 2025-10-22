/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.customentry.define;

public class DataFormatInfo {
    private String rowCaption;
    private String code;
    private String title;

    public DataFormatInfo() {
    }

    public DataFormatInfo(String rowCaption, String code, String title) {
        this.rowCaption = rowCaption;
        this.code = code;
        this.title = title;
    }

    public String getRowCaption() {
        return this.rowCaption;
    }

    public void setRowCaption(String rowCaption) {
        this.rowCaption = rowCaption;
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
}

