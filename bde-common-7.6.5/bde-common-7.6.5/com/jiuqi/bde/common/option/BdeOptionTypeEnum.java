/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.option;

public enum BdeOptionTypeEnum {
    SYSTEM("BDE_SYSTEM_OPTION", "\u53d6\u6570\u914d\u7f6e"),
    OTHER("BDE_OTHER_OPTION", "\u5176\u4ed6\u914d\u7f6e");

    private final String code;
    private final String name;

    private BdeOptionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static BdeOptionTypeEnum fromCode(String code) {
        for (BdeOptionTypeEnum type : BdeOptionTypeEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }
}

