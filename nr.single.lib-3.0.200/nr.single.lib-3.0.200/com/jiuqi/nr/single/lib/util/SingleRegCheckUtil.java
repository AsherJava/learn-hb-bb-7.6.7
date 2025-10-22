/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.util;

public class SingleRegCheckUtil {
    private static int[] IDC_WEIGHTS = new int[]{3, 7, 9, 10, 5, 8, 4, 2};
    private static int[] IDC_WEIGHTS_REG = new int[]{3, 7, 9, 10, 5, 8, 4, 2, 1, 6, 5, 3, 7, 9, 8, 2};
    private static final String BASECODE = "0123456789ABCDEFGHJKLMNPQRTUWXYIOZSV#";

    private static int getIDCHash(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if ((c = Character.toUpperCase(c)) >= 'A' && c <= 'Z') {
            return c - 65 + 10;
        }
        return c == '#' ? 36 : -1;
    }

    public static String getIDCCode(String unitCode) {
        if (unitCode == null || unitCode.length() < 8) {
            return "#";
        }
        int hash = 0;
        for (int i = 0; i < 8; ++i) {
            int h = SingleRegCheckUtil.getIDCHash(unitCode.charAt(i));
            if (h < 0) {
                return "#";
            }
            hash += h * IDC_WEIGHTS[i];
        }
        if ((hash = 11 - hash % 11) < 10) {
            return String.valueOf((char)(hash + 48));
        }
        if (hash == 10) {
            return "X";
        }
        if (hash == 11) {
            return "0";
        }
        return "#";
    }

    public static String getRegCheckCode(String unitCode) {
        if (unitCode == null || unitCode.length() < 16) {
            return "#";
        }
        int hash = 0;
        for (int i = 0; i < 16; ++i) {
            int h = SingleRegCheckUtil.getIDCHash(unitCode.charAt(i));
            if (h < 0) {
                return "#";
            }
            hash += h * IDC_WEIGHTS_REG[i];
        }
        if ((hash = 31 - hash % 31) < 10) {
            return String.valueOf((char)(hash + 48));
        }
        if (hash >= 31) {
            return "0";
        }
        return String.valueOf(BASECODE.charAt(hash));
    }
}

