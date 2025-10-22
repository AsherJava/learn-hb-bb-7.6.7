/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.wb.format;

public class FormatUtils {
    public static final String CURRENCY_SYMBOL_CNY = "[$\u00a5-804]";
    public static final String CURRENCY_SYMBOL_USD = "[$$-409]";
    public static final String CURRENCY_SYMBOL_EUR = "[$\u20ac-407]";

    private FormatUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static StringBuilder getBasicNumberFormat(int decimal, boolean thousands) {
        StringBuilder stringBuilder = new StringBuilder();
        if (thousands) {
            stringBuilder.append("#,##");
        }
        stringBuilder.append("0");
        if (decimal > 0) {
            stringBuilder.append(".");
            for (int i = 0; i < decimal; ++i) {
                stringBuilder.append("0");
            }
        }
        return stringBuilder;
    }

    public static String getActPlaceHolder(int decimal) {
        StringBuilder result = new StringBuilder();
        if (decimal <= 0) {
            return result.toString();
        }
        for (int i = 0; i < decimal; ++i) {
            result.append("?");
        }
        return result.toString();
    }
}

