/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.enums;

public enum ReScoreOptionEnum {
    RESCORE(0, "\u91cd\u65b0\u8bc4\u5206"),
    SKIP(1, "\u8df3\u8fc7");

    private int code;
    private String title;

    private ReScoreOptionEnum(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

