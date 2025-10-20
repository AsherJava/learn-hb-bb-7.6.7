/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.utils;

import java.util.Map;
import java.util.function.Function;

public abstract class ConcurrentHashMapUtils {
    private static boolean isJdk8;

    public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<? super K, ? extends V> func) {
        if (isJdk8) {
            V v = map.get(key);
            if (v == null) {
                v = map.computeIfAbsent((K)key, func);
            }
            return v;
        }
        return map.computeIfAbsent((K)key, func);
    }

    static {
        try {
            isJdk8 = System.getProperty("java.version").startsWith("1.8.");
        }
        catch (Exception e) {
            isJdk8 = true;
        }
    }
}

