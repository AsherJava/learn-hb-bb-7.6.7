/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.enums;

public enum AdjustTypeEnum {
    VIRTUAL_TABLE("virtualTable", "\u865a\u62df\u8868");

    private String code;
    private String title;

    private AdjustTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

