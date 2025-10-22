/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapValueComparator
implements Comparator<Integer> {
    Map<String, String> base;

    public MapValueComparator(Map<String, String> base) {
        this.base = base;
    }

    @Override
    public int compare(Integer a, Integer b) {
        if (a > b) {
            return -1;
        }
        return 1;
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator valueComparator = new Comparator<K>(){

            @Override
            public int compare(K k1, K k2) {
                int compare = ((Comparable)map.get(k2)).compareTo(map.get(k1));
                if (compare == 0) {
                    return 0;
                }
                return compare;
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static Object getMinValue(Map<Integer, String> map) {
        if (map == null) {
            return null;
        }
        Set<Integer> c = map.keySet();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        return obj[0];
    }

    public static Object getMaxValue(Map<Integer, String> map) {
        if (map == null) {
            return null;
        }
        int length = map.size();
        Set<Integer> c = map.keySet();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        return obj[length - 1];
    }
}

