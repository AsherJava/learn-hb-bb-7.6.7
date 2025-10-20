/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

public enum DateCommonFormatEnum {
    FULL_DIGIT_BY_NONE("yyyyMMdd"),
    FULL_DIGIT_BY_DASH("yyyy-MM-dd"),
    MONTH_DIGIT_BY_DASH("yyyy-MM"),
    FULL_DIGIT_BY_SPLASH("yyyy/MM/dd"),
    MONTH_DIGIT_BY_SPLASH("yyyy/MM"),
    FULL_CHAR_BY_DASH("yyyy\u5e74MM\u6708dd\u65e5"),
    MONTH_CHAR_BY_DASH("yyyy\u5e74MM\u6708");

    private String format;

    private DateCommonFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }

    public String toString() {
        return this.format;
    }

    public String getName() {
        return this.format;
    }

    public String getTitle() {
        return this.format;
    }
}

