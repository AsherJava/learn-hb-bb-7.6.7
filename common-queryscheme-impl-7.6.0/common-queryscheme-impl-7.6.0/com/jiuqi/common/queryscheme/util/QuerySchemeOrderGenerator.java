/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.queryscheme.util;

public class QuerySchemeOrderGenerator {
    private static long oldOrder = 0L;
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private QuerySchemeOrderGenerator() {
    }

    private static String newOrder() {
        long order;
        for (order = System.currentTimeMillis(); order <= oldOrder; ++order) {
        }
        String suffix = String.valueOf(order).substring(3);
        oldOrder = order;
        StringBuffer rt = new StringBuffer(10);
        while (order > 0L) {
            rt.insert(0, CHARS.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString() + "-" + suffix;
    }

    public static synchronized String newSubjectOrder() {
        return QuerySchemeOrderGenerator.newOrder();
    }

    public static synchronized String newOrderShort() {
        long order;
        for (order = System.currentTimeMillis(); order <= oldOrder; ++order) {
        }
        oldOrder = order;
        StringBuffer rt = new StringBuffer(10);
        while (order > 0L) {
            rt.insert(0, CHARS.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString();
    }

    public static synchronized String newOrderShort(long timestamp) {
        long ts = timestamp;
        if (ts > oldOrder) {
            oldOrder = ts;
        }
        StringBuilder rt = new StringBuilder(10);
        while (ts > 0L) {
            rt.insert(0, CHARS.charAt((int)(ts % 36L)));
            ts /= 36L;
        }
        return rt.toString();
    }

    public static synchronized long reverseOrderShort(String orderShort) {
        if (orderShort == null || orderShort.isEmpty()) {
            return -1L;
        }
        long order = 0L;
        for (int i = 0; i < orderShort.length(); ++i) {
            order = order * 36L + (long)CHARS.indexOf(orderShort.charAt(i));
        }
        return order;
    }
}

