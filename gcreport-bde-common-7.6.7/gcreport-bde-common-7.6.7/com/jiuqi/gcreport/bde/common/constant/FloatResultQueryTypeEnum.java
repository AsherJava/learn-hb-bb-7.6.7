/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.common.constant;

@Deprecated
public enum FloatResultQueryTypeEnum {
    CUSTOM_SQL("CUSTOM_SQL", "\u81ea\u5b9a\u4e49SQL"),
    VA_QUERY("VA_QUERY", "\u81ea\u5b9a\u4e49\u67e5\u8be2"),
    SIMPLE_FETCHSOURCE("SIMPLE_FETCHSOURCE", "\u6807\u51c6\u4e1a\u52a1\u6a21\u578b"),
    SENIOR_FETCHSOURCE("SENIOR_FETCHSOURCE", "\u9ad8\u7ea7\u4e1a\u52a1\u6a21\u578b"),
    CUSTOM_FETCHSOURCE("CUSTOM_FETCHSOURCE", "\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b");

    private String code;
    private String name;

    private FloatResultQueryTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FloatResultQueryTypeEnum getEnumByCode(String code) {
        for (FloatResultQueryTypeEnum floatResultQueryTypeEnum : FloatResultQueryTypeEnum.values()) {
            if (!floatResultQueryTypeEnum.getCode().equals(code)) continue;
            return floatResultQueryTypeEnum;
        }
        return null;
    }

    public static FloatResultQueryTypeEnum getEnumByName(String name) {
        for (FloatResultQueryTypeEnum floatResultQueryTypeEnum : FloatResultQueryTypeEnum.values()) {
            if (!floatResultQueryTypeEnum.getName().equals(name)) continue;
            return floatResultQueryTypeEnum;
        }
        return null;
    }
}

