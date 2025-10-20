/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.impl.common;

public enum OrientEnum {
    DEBIT(1, "\u501f"),
    CREDIT(-1, "\u8d37"),
    EQUAL(0, "\u5e73");

    private final Integer code;
    private final String title;

    private OrientEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static OrientEnum fromCode(Integer code) {
        for (OrientEnum type : OrientEnum.values()) {
            if (type.getCode().compareTo(code) != 0) continue;
            return type;
        }
        return EQUAL;
    }
}

