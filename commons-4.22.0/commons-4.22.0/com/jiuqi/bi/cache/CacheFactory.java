/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.cache;

import com.jiuqi.bi.cache.ICache;
import com.jiuqi.bi.cache.TimeCache;

public class CacheFactory {
    private CacheFactory() {
    }

    public static <T> ICache<T> createTimeCache(long timeout) {
        if (timeout < 0L) {
            throw new IllegalArgumentException("\u8d85\u65f6\u65f6\u95f4\u6570\u4e0d\u80fd\u5c0f\u4e8e0");
        }
        return new TimeCache(timeout);
    }

    public static <T> ICache<T> createTimeCache(long timeout, int capacity, int packSize) {
        if (timeout < 0L) {
            throw new IllegalArgumentException("\u8d85\u65f6\u65f6\u95f4\u6570\u4e0d\u80fd\u5c0f\u4e8e0");
        }
        return new TimeCache(capacity, timeout, packSize);
    }
}

