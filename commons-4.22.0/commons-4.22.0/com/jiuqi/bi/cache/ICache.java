/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.cache;

import com.jiuqi.bi.cache.CacheTimeout;

public interface ICache<T> {
    public long put(T var1);

    public long put(Object var1, T var2);

    public T get(long var1) throws CacheTimeout;

    public T getByKey(Object var1) throws CacheTimeout;

    public void remove(long var1);

    public void removeByKey(String var1);
}

