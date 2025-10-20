/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.intf.value.MapContainer;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class MapContainerImpl<K, V>
implements MapContainer<K, V> {
    protected final Map<K, V> map;

    public MapContainerImpl(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public Stream<K> kStream() {
        return this.map.keySet().stream();
    }

    @Override
    public Stream<V> vStream() {
        return this.map.values().stream();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public void forEach(BiConsumer<K, V> consumer) {
        this.map.forEach(consumer);
    }

    @Override
    public V find(K key) {
        return this.map.get(key);
    }

    @Override
    public V get(K key) {
        V result = this.map.get(key);
        if (result == null) {
            throw new MissingObjectException(BizBindingI18nUtil.getMessage("va.bizbinding.getobjfailed") + key);
        }
        return result;
    }
}

