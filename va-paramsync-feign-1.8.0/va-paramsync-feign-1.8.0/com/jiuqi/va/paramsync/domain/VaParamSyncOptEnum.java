/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

public enum VaParamSyncOptEnum {
    ADD(1, "\u65b0\u589e"),
    MODIFY(2, "\u4fee\u6539"),
    UNCHANGE(3, "\u65e0\u53d8\u5316");

    private int value;
    private String title;

    private VaParamSyncOptEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

