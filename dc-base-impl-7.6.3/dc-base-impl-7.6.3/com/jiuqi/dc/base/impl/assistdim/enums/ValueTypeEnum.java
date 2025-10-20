/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.assistdim.enums;

public enum ValueTypeEnum {
    BASEDATA("BASEDATA", "\u57fa\u7840\u6570\u636e\u578b"),
    STRING("STRING", "\u5b57\u7b26\u578b"),
    NUMBERIC("NUMBERIC", "\u6570\u503c\u578b"),
    INT("INT", "\u6574\u6570\u578b"),
    DATE("DATE", "\u65e5\u671f\u578b");

    private String code;
    private String name;

    private ValueTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ValueTypeEnum fromCode(String code) {
        for (ValueTypeEnum type : ValueTypeEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }

    public static ValueTypeEnum fromName(String name) {
        for (ValueTypeEnum type : ValueTypeEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

