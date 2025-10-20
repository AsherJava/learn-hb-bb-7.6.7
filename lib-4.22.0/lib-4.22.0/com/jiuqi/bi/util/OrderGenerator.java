/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.util.concurrent.atomic.AtomicLong;

public class OrderGenerator {
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final OrderGenerator globalGenerator = new OrderGenerator();
    private final AtomicLong prevOrder = new AtomicLong();

    public static final OrderGenerator getGlobalGenerator() {
        return globalGenerator;
    }

    public static String newOrder() {
        long order = OrderGenerator.newOrderID();
        return OrderGenerator.orderToString(order);
    }

    private static String orderToString(long order) {
        StringBuilder rt = new StringBuilder(10);
        while (order > 0L) {
            rt.insert(0, chars.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString();
    }

    public static long newOrderID() {
        return OrderGenerator.getGlobalGenerator().generateID();
    }

    public long generateID() {
        long prev;
        long order = System.currentTimeMillis();
        if (order <= (prev = this.prevOrder.get()) || !this.prevOrder.compareAndSet(prev, order)) {
            order = this.prevOrder.incrementAndGet();
        }
        return order;
    }
}

