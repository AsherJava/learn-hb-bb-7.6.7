/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.enumerate;

public enum DisplayFormatEnum {
    DEFAULT("default"),
    CURRENCY("currency"),
    PERCENT("percent"),
    AUTO_PERCENT("auto_percent"),
    DATE("date", "yyyy-MM-dd"),
    CHINESE_DATE("chineseDate", "yyyy\u5e74MM\u6708dd\u65e5"),
    TIMESTAMP_SIMPLE("timestampSimple", "yyyy-MM-dd HH:mm:ss"),
    TIMESTAMP("timestamp", "yyyy-MM-dd HH:mm:ss:SSS"),
    CHINESE_TIMESTAMP_SIMPLE("chineseTimestampSimple", "yyyy\u5e74MM\u6708dd\u65e5 HH:mm:ss"),
    CHINESE_TIMESTAMP("chineseTimestamp", "yyyy\u5e74MM\u6708dd\u65e5 HH:mm:ss:SSS"),
    SIMPLE_BOOLEAN("simpleBoolean", "\u662f/\u5426"),
    BASE_DATA_DEFAULT("baseDataDefault"),
    CODE("code"),
    TITLE("title");

    private String typeName;
    private String formatString;

    private DisplayFormatEnum(String typeName) {
        this.typeName = typeName;
    }

    private DisplayFormatEnum(String typeName, String formatString) {
        this.typeName = typeName;
        this.formatString = formatString;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getFormatString() {
        return this.formatString;
    }

    public static DisplayFormatEnum val(String typeName) {
        for (DisplayFormatEnum paramType : DisplayFormatEnum.values()) {
            if (!paramType.getTypeName().equals(typeName)) continue;
            return paramType;
        }
        return null;
    }
}

