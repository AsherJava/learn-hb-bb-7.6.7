/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.consts;

public enum RateNodeTypeEnum {
    PERIOD("period"),
    GROUP("group"),
    CURRENCY("currency");

    private String value;

    private RateNodeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static RateNodeTypeEnum getInstance(String value) {
        for (RateNodeTypeEnum typeEnum : RateNodeTypeEnum.values()) {
            if (!typeEnum.getValue().equals(value)) continue;
            return typeEnum;
        }
        return null;
    }
}

