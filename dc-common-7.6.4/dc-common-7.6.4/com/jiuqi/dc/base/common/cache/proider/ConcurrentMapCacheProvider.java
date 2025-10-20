/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.dc.base.common.cache.proider;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import com.jiuqi.dc.base.common.cache.proider.BaseCacheProvider;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public abstract class ConcurrentMapCacheProvider<E extends CacheEntity>
extends BaseCacheProvider<E> {
    private final Map<String, E> store = new ConcurrentHashMap<String, E>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Comparator<E> comparator = new Comparator<E>(){

        @Override
        public int compare(E o1, E o2) {
            return o1.getCacheKey().compareTo(o2.getCacheKey());
        }
    };

    @Override
    public void syncCurrCache() {
        Lock lock = null;
        boolean isLock = false;
        try {
            lock = this.readWriteLock.writeLock();
            lock.lock();
            isLock = true;
            this.reloadCache(true);
            lock.unlock();
            isLock = false;
        }
        finally {
            if (lock != null && isLock) {
                lock.unlock();
            }
        }
    }

    private synchronized void reloadCache(boolean forceReload) {
        if (!forceReload && !this.store.isEmpty()) {
            return;
        }
        this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u540c\u6b65\u5f00\u59cb", (Object)this.getCacheDefine().getCode(), (Object)this.getCacheDefine().getName());
        this.store.clear();
        Collection caches = this.loadCache();
        if (CollectionUtils.isEmpty(caches)) {
            return;
        }
        for (CacheEntity e : caches) {
            this.store.put(e.getCacheKey(), e);
        }
        this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u540c\u6b65\u7ed3\u675f", (Object)this.getCacheDefine().getCode(), (Object)this.getCacheDefine().getName());
    }

    @Override
    public void cleanCache() {
        Lock lock = null;
        boolean isLock = false;
        try {
            lock = this.readWriteLock.writeLock();
            lock.lock();
            isLock = true;
            this.store.clear();
            lock.unlock();
            isLock = false;
        }
        finally {
            if (lock != null && isLock) {
                lock.unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<E> list() {
        Lock lock = null;
        boolean isLock = false;
        try {
            lock = this.readWriteLock.readLock();
            lock.lock();
            isLock = true;
            if (CollectionUtils.isEmpty(this.store.values())) {
                this.reloadCache(false);
            }
            Collection<E> values = this.store.values();
            if (this.getComparator() == null) {
                List list = values.stream().collect(Collectors.toList());
                return list;
            }
            lock.unlock();
            isLock = false;
            List list = values.stream().sorted(this.getComparator()).collect(Collectors.toList());
            return list;
        }
        finally {
            if (lock != null && isLock) {
                lock.unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public E get(String cacheKey) {
        Lock lock = null;
        boolean isLock = false;
        try {
            lock = this.readWriteLock.readLock();
            lock.lock();
            isLock = true;
            if (CollectionUtils.isEmpty(this.store.values())) {
                this.reloadCache(false);
            }
            lock.unlock();
            isLock = false;
            CacheEntity cacheEntity = (CacheEntity)this.store.get(cacheKey);
            return (E)cacheEntity;
        }
        finally {
            if (lock != null && isLock) {
                lock.unlock();
            }
        }
    }

    public Comparator<E> getComparator() {
        return this.comparator;
    }
}

