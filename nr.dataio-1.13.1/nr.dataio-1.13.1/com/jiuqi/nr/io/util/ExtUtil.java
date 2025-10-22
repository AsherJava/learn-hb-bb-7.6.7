/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.util;

public class ExtUtil {
    public static String trimStart(String inStr, String prefix) {
        if (inStr.startsWith(prefix) || inStr.startsWith(prefix.toUpperCase())) {
            return inStr.substring(prefix.length());
        }
        return inStr;
    }

    public static String trimEnd(String inStr, String suffix) {
        if (inStr.endsWith(suffix) || inStr.endsWith(suffix.toUpperCase())) {
            return inStr.substring(0, inStr.length() - suffix.length());
        }
        return inStr;
    }
}

