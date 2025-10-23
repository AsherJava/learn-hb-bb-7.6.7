/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.HashCacheValue
 *  com.jiuqi.nr.datascheme.api.core.Basic
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.np.cache.HashCacheValue;
import com.jiuqi.nr.datascheme.api.core.Basic;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cache.Cache;

public interface IBaseSchemeCache<T extends Basic> {
    public void put(T var1);

    public void puts(Collection<T> var1);

    public Cache.ValueWrapper get(String var1);

    public Map<String, Cache.ValueWrapper> gets(Collection<String> var1);

    public void putHashIndex(String var1, Map<?, ?> var2);

    public void putHashIndexEntry(String var1, Object var2, Object var3);

    public HashCacheValue<Cache.ValueWrapper> getHashIndexValue(String var1, Object var2);

    public HashCacheValue<List<Cache.ValueWrapper>> mGetHashIndexValue(String var1, List<Object> var2);

    public HashCacheValue<Map<Object, Object>> mGetHashIndexValue(String var1);

    public HashCacheValue<Set<Object>> getHashIndexKeys(String var1);

    public void putKVIndex(String var1, Object var2);

    public void mPutKVIndex(Map<String, T> var1);

    public Cache.ValueWrapper getKVIndexValue(String var1);

    public List<Cache.ValueWrapper> mGetKVIndexValue(List<String> var1);

    public void removeIndexes(Collection<String> var1);

    public void removeIndexEntry(String var1, Object ... var2);
}

