/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

public class VersionUtils {
    public static String getVersionCodeByPeriod(String period) {
        if (period == null) {
            return null;
        }
        return String.format("V%s", period.substring(0, 4));
    }

    public static String getYearByPeriod(String period) {
        if (period == null) {
            return null;
        }
        return String.format("%s\u5e74", period.substring(0, 4));
    }
}

