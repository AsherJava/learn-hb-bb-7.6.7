/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotEmpty(String s) {
        return !StringUtils.isEmpty(s);
    }

    public static String firstLetterUpper(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}

