/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.common;

public class OrderUtils {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static long stringToOrder(String orderStr) {
        long order = 0L;
        for (int i = 0; i < orderStr.length(); ++i) {
            char ch = orderStr.charAt(i);
            int index = CHARS.indexOf(ch);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character in order string: " + ch);
            }
            order = order * 36L + (long)index;
        }
        return order;
    }
}

