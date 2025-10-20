/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class FIFOCache<K, V> {
    private final long maximumSize;
    private final long freeSize;
    private AtomicLong currentSize = new AtomicLong();
    private final Map<K, V> map = new ConcurrentHashMap();
    private final ConcurrentLinkedQueue<K> keyQueue = new ConcurrentLinkedQueue();
    private final Object lock = new Object();

    public FIFOCache(long maximumSize, long freeSize) {
        this.maximumSize = FIFOCache.adjustMaximumSize(maximumSize);
        this.freeSize = FIFOCache.adjustFreeSize(this.maximumSize, freeSize);
    }

    public FIFOCache(long maximumSize) {
        this(maximumSize, 0L);
    }

    private static long adjustMaximumSize(long maximumSize) {
        return Math.max(maximumSize, 2L);
    }

    private static long adjustFreeSize(long maximumSize, long freeSize) {
        if (freeSize > maximumSize) {
            return maximumSize;
        }
        if (freeSize > 0L) {
            return freeSize;
        }
        return FIFOCache.calcDefaultFreeSize(maximumSize);
    }

    private static long calcDefaultFreeSize(long maximumSize) {
        return Math.min(Math.max(maximumSize / 10L, 1L), 100L);
    }

    public V get(Object key) {
        return this.map.get(key);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void put(K key, V value) {
        V oldValue = this.map.putIfAbsent(key, value);
        if (oldValue != null) {
            return;
        }
        this.keyQueue.add(key);
        if (this.currentSize.incrementAndGet() >= this.maximumSize) {
            Object object = this.lock;
            synchronized (object) {
                if (this.currentSize.get() >= this.maximumSize) {
                    long realFreeSize = this.freeSize + ((long)this.keyQueue.size() - this.maximumSize);
                    int i = 0;
                    while ((long)i < realFreeSize) {
                        this.map.remove(this.keyQueue.poll());
                        ++i;
                    }
                    this.currentSize.set(0L);
                }
            }
        }
    }
}

