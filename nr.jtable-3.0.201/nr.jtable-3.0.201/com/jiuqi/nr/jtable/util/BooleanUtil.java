/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.util;

public class BooleanUtil {
    public static String[] trueValues = new String[]{"true", "yes", "y", "on", "1", "\u662f", "\u221a"};
    public static String[] falseValues = new String[]{"false", "no", "n", "off", "0", "\u5426", "\u00d7"};

    public static Boolean parseBoolean(String value) {
        for (int i = 0; i < trueValues.length; ++i) {
            if (value.toLowerCase().equals(trueValues[i])) {
                return true;
            }
            if (!value.toLowerCase().equals(falseValues[i])) continue;
            return false;
        }
        return null;
    }
}

