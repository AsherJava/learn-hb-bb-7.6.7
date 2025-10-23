/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

public class ExcelUtils {
    public static String next(String groupCode) {
        String prefix = groupCode.substring(0, groupCode.lastIndexOf(95) + 1);
        String numberStr = groupCode.substring(groupCode.lastIndexOf(95) + 1);
        int number = Integer.parseInt(numberStr) + 1;
        return prefix + String.format("%0" + numberStr.length() + "d", number);
    }

    public static String child(String groupCode) {
        return groupCode + "00";
    }

    public static String parent(String groupCode) {
        String numberStr = groupCode.substring(groupCode.lastIndexOf(95) + 1);
        if (numberStr.length() == 2) {
            return null;
        }
        String parentNumberStr = numberStr.substring(0, numberStr.length() - 2);
        return groupCode.substring(0, groupCode.lastIndexOf(95) + 1) + parentNumberStr;
    }
}

