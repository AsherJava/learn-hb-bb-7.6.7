/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.common;

public enum FieldFilterTypeEnum {
    EQUAL("equal", "\u7b49\u4e8e"),
    CONTAIN("contain", "\u5305\u542b"),
    IN("in", "\u5728...\u8303\u56f4\u5185");

    private final String code;

    private FieldFilterTypeEnum(String code, String desc) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

