/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

public class ShowItem {
    private final String code;
    private final String title;
    private String rowCaption;

    public ShowItem(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getRowCaption() {
        return this.rowCaption;
    }

    public void setRowCaption(String rowCaption) {
        this.rowCaption = rowCaption;
    }
}

