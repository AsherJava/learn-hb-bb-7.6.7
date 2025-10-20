/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.enums;

public enum OrientEnum {
    DEBIT(1, "\u501f"),
    CREDIT(-1, "\u8d37");

    private Integer code;
    private String name;

    private OrientEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static OrientEnum fromCode(Integer code) {
        for (OrientEnum type : OrientEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }

    public static OrientEnum fromName(String name) {
        for (OrientEnum type : OrientEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

