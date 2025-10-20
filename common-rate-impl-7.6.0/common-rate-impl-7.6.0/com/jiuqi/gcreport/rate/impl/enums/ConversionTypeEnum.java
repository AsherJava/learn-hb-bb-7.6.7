/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.enums;

public enum ConversionTypeEnum {
    DIRECT("01", "\u76f4\u63a5\u6298\u7b97"),
    TYPE_LJ("02", "\u7d2f\u8ba1\u5206\u6bb5"),
    TYPE_BN("03", "\u672c\u5e74\u5206\u6bb5");

    private static final long ID = 1L;
    private String code;
    private String name;

    private ConversionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ConversionTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ConversionTypeEnum type : ConversionTypeEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }
}

