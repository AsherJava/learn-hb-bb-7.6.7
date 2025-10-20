/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListValueHashMap<K, V> {
    private final Map<K, List<V>> targetMap;

    public ListValueHashMap() {
        this(64);
    }

    public ListValueHashMap(int paramInt) {
        this.targetMap = new HashMap<K, List<V>>(paramInt);
    }

    public void add(K key, V value) {
        if (!this.targetMap.containsKey(key)) {
            ArrayList<V> values = new ArrayList<V>(64);
            values.add(value);
            this.targetMap.put(key, values);
            return;
        }
        this.targetMap.get(key).add(value);
    }

    public List<V> get(Object key) {
        return this.targetMap.get(key);
    }

    public Set<K> keySet() {
        return this.targetMap.keySet();
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.targetMap.entrySet();
    }

    public int size() {
        return this.targetMap.size();
    }
}

