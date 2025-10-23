/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils;

import com.jiuqi.nr.formula.utils.ConvertItem;
import com.jiuqi.nr.formula.utils.ConvertMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class HashConvertMap
implements ConvertMap<String, String> {
    private final Map<String, ConvertItem<?, ?>> targetMap = new HashMap();

    @Override
    public String put(String target, String source) {
        this.targetMap.put(target, new ConvertItem(source));
        return source;
    }

    @Override
    public <KK, VV> void put(String target, String source, Function<KK, VV> function) {
        this.targetMap.put(target, new ConvertItem<KK, VV>(source, function));
    }

    @Override
    public String get(Object key) {
        ConvertItem<?, ?> convertItem = this.targetMap.get(key);
        if (convertItem != null) {
            return convertItem.getSource();
        }
        return null;
    }

    @Override
    public <KK, VV> Function<KK, VV> getFunction(String key) {
        ConvertItem<?, ?> convertItem = this.targetMap.get(key);
        if (convertItem != null) {
            return convertItem.getSourceToTargetFunction();
        }
        return null;
    }

    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.targetMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.targetMap.keySet();
    }

    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.targetMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.targetMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }
}

