/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils;

import java.util.Map;
import java.util.function.Function;

public interface ConvertMap<K, V>
extends Map<K, V> {
    @Override
    public V put(K var1, V var2);

    public <KK, VV> void put(K var1, V var2, Function<KK, VV> var3);

    public <KK, VV> Function<KK, VV> getFunction(K var1);

    @Override
    public V get(Object var1);
}

