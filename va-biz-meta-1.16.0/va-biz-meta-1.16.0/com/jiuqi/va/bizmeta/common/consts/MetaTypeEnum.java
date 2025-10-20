/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.common.consts;

public enum MetaTypeEnum {
    BILL("bill", "\u5355\u636e", "B"),
    BILLLIST("billlist", "\u5355\u636e\u5217\u8868", "L"),
    WORKFLOW("workflow", "\u5de5\u4f5c\u6d41", "W");

    private String name;
    private String title;
    private String code;

    private MetaTypeEnum(String name, String title, String code) {
        this.name = name;
        this.title = title;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }
}

