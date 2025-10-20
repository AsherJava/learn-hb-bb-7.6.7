/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.temp.dto;

public enum MessageTypeEnum {
    WARN("WARN", "\u8b66\u544a"),
    INFO("INFO", "\u4fe1\u606f"),
    ERROR("ERROR", "\u9519\u8bef");

    private String code;
    private String title;

    private MessageTypeEnum(String code, String title) {
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

