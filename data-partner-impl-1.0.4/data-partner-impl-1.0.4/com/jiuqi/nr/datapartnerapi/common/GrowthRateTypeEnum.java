/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.common;

public enum GrowthRateTypeEnum {
    YOY("year-on-year", "\u540c\u6bd4\u589e\u957f\u7387"),
    POP("period-on-period", "\u73af\u6bd4\u589e\u957f\u7387");

    private final String type;
    private final String name;

    private GrowthRateTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}

