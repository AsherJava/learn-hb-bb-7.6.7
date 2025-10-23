/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FormatType {
    GENERAL(1, "\u5e38\u89c4"),
    NUMBER(2, "\u6570\u503c"),
    CURRENCY(4, "\u8d27\u5e01"),
    ACCOUNTING(8, "\u4f1a\u8ba1\u4e13\u7528"),
    DATE(16, "\u65e5\u671f"),
    DATE_TIME(128, "\u65e5\u671f\u65f6\u95f4"),
    PERCENTAGE(32, "\u767e\u5206\u6bd4"),
    TEXT(64, "\u6587\u672c");

    private final int value;
    private final String name;
    private static final Map<Integer, FormatType> VALUE_MAP;

    private FormatType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static FormatType getByValue(String value) {
        if (value == null) {
            return null;
        }
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = Arrays.stream(FormatType.values()).collect(Collectors.toMap(FormatType::getValue, r -> r));
    }
}

