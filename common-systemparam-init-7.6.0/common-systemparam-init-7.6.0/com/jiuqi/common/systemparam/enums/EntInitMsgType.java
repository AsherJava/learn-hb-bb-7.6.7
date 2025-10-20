/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.systemparam.enums;

public enum EntInitMsgType {
    INFO("INFO", "[\u4fe1\u606f]"),
    ERROR("ERROR", "[\u9519\u8bef]");

    private String code;
    private String name;

    private EntInitMsgType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

