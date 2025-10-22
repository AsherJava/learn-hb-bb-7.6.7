/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

import java.util.List;

public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotEmpty(String s) {
        return !StringUtils.isEmpty(s);
    }

    public static boolean isEmpty(List<String> periodKeys) {
        for (String s : periodKeys) {
            StringUtils.isEmpty(s);
        }
        return false;
    }
}

