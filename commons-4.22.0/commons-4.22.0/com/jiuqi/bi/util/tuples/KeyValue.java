/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;
import java.util.Map;

public class KeyValue<K, V>
extends Tuple
implements Map.Entry<K, V> {
    private static final long serialVersionUID = 1L;

    public KeyValue(K key, V value) {
        super(key, value);
    }

    public static <K, V> KeyValue<K, V> with(K key, V value) {
        return new KeyValue<K, V>(key, value);
    }

    @Override
    public K getKey() {
        return (K)this.get(0);
    }

    @Override
    public V getValue() {
        return (V)this.get(1);
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("\u5143\u7ec4KV\u4e0d\u652f\u6301\u4fee\u6539\u64cd\u4f5c");
    }

    @Override
    public String toString() {
        return "[" + this.getKey() + " = " + this.getValue() + "]";
    }
}

