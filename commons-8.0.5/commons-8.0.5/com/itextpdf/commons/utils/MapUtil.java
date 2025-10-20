/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public final class MapUtil {
    private static final int HASH_MULTIPLIER = 31;

    private MapUtil() {
    }

    public static <K, V> boolean equals(Map<K, V> m1, Map<K, V> m2) {
        if (m1 == m2) {
            return true;
        }
        if (m1 == null || m2 == null) {
            return false;
        }
        if (!m1.getClass().equals(m2.getClass())) {
            return false;
        }
        if (m1.size() != m2.size()) {
            return false;
        }
        for (Map.Entry<K, V> entry : m1.entrySet()) {
            V obj1 = entry.getValue();
            V obj2 = m2.get(entry.getKey());
            if (m2.containsKey(entry.getKey()) && Objects.equals(obj1, obj2)) continue;
            return false;
        }
        return true;
    }

    public static <K, V> void merge(Map<K, V> destination, Map<K, V> source, BiFunction<V, V, V> valuesMerger) {
        if (destination == source) {
            return;
        }
        for (Map.Entry<K, V> entry : source.entrySet()) {
            V value = destination.get(entry.getKey());
            if (value == null) {
                destination.put(entry.getKey(), entry.getValue());
                continue;
            }
            destination.put(entry.getKey(), valuesMerger.apply(value, entry.getValue()));
        }
    }

    public static <K, V> int getHashCode(Map<K, V> m1) {
        if (null == m1) {
            return 0;
        }
        int hash = 0;
        for (Map.Entry<K, V> entry : m1.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            hash = 31 * hash + (key == null ? 0 : key.hashCode());
            hash = 31 * hash + (value == null ? 0 : value.hashCode());
        }
        return hash;
    }

    public static <K, V> void putIfNotNull(Map<K, V> map, K key, V value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}

