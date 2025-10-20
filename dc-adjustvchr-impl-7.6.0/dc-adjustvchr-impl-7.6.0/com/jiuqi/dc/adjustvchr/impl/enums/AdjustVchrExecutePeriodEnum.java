/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.impl.enums;

public enum AdjustVchrExecutePeriodEnum {
    CURRENT_TO_END_PERIOD("CURRENT_TO_END_PERIOD", "\u5f53\u671f\u81f3\u8c03\u6574\u7ed3\u675f\u671f"),
    CURRENT_PERIOD("CURRENT_PERIOD", "\u5f53\u671f");

    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private AdjustVchrExecutePeriodEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AdjustVchrExecutePeriodEnum getPeriodTypeByCode(String code) {
        for (AdjustVchrExecutePeriodEnum periodType : AdjustVchrExecutePeriodEnum.values()) {
            if (!periodType.getCode().equals(code)) continue;
            return periodType;
        }
        return null;
    }
}

