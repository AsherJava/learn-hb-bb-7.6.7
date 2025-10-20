/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.datastructures;

import java.util.HashMap;
import java.util.Map;

public final class BiMap<K, V> {
    private final Map<K, V> map = new HashMap();
    private final Map<V, K> inverseMap = new HashMap<V, K>();

    public void put(K k, V v) {
        this.map.put(k, v);
        this.inverseMap.put(v, k);
    }

    public V getByKey(K value) {
        return this.map.get(value);
    }

    public K getByValue(V key) {
        return this.inverseMap.get(key);
    }

    public void removeByKey(K k) {
        V v = this.map.remove(k);
        if (v != null) {
            this.inverseMap.remove(v);
        }
    }

    public void removeByValue(V v) {
        K k = this.inverseMap.remove(v);
        if (k != null) {
            this.map.remove(k);
        }
    }

    public int size() {
        return this.map.size();
    }

    public void clear() {
        this.map.clear();
        this.inverseMap.clear();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean containsKey(K k) {
        return this.map.containsKey(k);
    }

    public boolean containsValue(V v) {
        return this.inverseMap.containsKey(v);
    }
}

