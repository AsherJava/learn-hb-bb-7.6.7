/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.enums;

public enum TemporaryTableTypeEnum {
    PHYSICAL("PHYSICAL", "\u7269\u7406\u8868"),
    TRANSACTION("TRANSACTION", "\u4e8b\u52a1\u4e34\u65f6\u8868"),
    SESSION("SESSION", "\u4f1a\u8bdd\u4e34\u65f6\u8868");

    private String code;
    private String name;

    private TemporaryTableTypeEnum(String code, String name) {
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

