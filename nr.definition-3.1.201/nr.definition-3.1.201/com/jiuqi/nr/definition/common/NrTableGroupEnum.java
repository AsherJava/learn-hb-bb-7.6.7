/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

public enum NrTableGroupEnum {
    SYSTEM("SYSTEMGROUP", "\u7cfb\u7edf\u5206\u7ec4"),
    ENUM("ENUMGROUP", "\u679a\u4e3e\u5206\u7ec4"),
    DATA("DATAGROUP", "\u6570\u636e\u8868\u5206\u7ec4");

    private String title;
    private String code;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private NrTableGroupEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

