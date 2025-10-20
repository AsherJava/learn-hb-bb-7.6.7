/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

public enum VaParamSyncGroupTypeEnum {
    MODULE(0, "\u6a21\u5757"),
    GROUP(1, "\u5206\u7ec4"),
    DEFINE(2, "\u5b9a\u4e49");

    private int value;
    private String title;

    private VaParamSyncGroupTypeEnum(int value, String title) {
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

