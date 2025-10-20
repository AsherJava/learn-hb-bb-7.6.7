/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.common;

public class OrderGenerator {
    private static long oldOrder = 0L;
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private OrderGenerator() {
    }

    private static String newOrder() {
        long order;
        for (order = System.currentTimeMillis(); order <= oldOrder; ++order) {
        }
        String suffix = String.valueOf(order).substring(3);
        oldOrder = order;
        StringBuffer rt = new StringBuffer(10);
        while (order > 0L) {
            rt.insert(0, chars.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString() + "-" + suffix;
    }

    public static synchronized String newSubjectOrder() {
        return OrderGenerator.newOrder();
    }

    public static synchronized String newOrderShort() {
        long order;
        for (order = System.currentTimeMillis(); order <= oldOrder; ++order) {
        }
        oldOrder = order;
        StringBuffer rt = new StringBuffer(10);
        while (order > 0L) {
            rt.insert(0, chars.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString();
    }
}

