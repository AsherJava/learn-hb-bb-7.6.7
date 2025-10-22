/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.check.enums;

public enum BBLXEnum {
    MERGE(9, "\u5408\u5e76\u6237"),
    DEFF(1, "\u5dee\u989d\u6237"),
    SINGLE(0, "\u5355\u6237");

    private int id;
    private String title;

    private BBLXEnum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }
}

