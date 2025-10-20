/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public interface MapContainer<K, V> {
    public Stream<K> kStream();

    public Stream<V> vStream();

    public int size();

    public void forEach(BiConsumer<K, V> var1);

    public V find(K var1);

    public V get(K var1);
}

