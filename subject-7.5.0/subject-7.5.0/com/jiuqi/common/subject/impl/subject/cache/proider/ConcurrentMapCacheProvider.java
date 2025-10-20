/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.common.subject.impl.subject.cache.proider;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.subject.impl.subject.cache.intf.CacheEntity;
import com.jiuqi.common.subject.impl.subject.cache.proider.BaseCacheProvider;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class ConcurrentMapCacheProvider<E extends CacheEntity>
extends BaseCacheProvider<E> {
    private final Map<String, E> store = new ConcurrentHashMap<String, E>();
    private List<E> sortSubjList = new CopyOnWriteArrayList();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Comparator<E> comparator = new Comparator<E>(){

        @Override
        public int compare(E o1, E o2) {
            return o1.getCacheKey().compareTo(o2.getCacheKey());
        }
    };

    @Override
    public void syncCurrCache() {
        try {
            this.readWriteLock.writeLock().lock();
            this.reloadCache(true);
        }
        finally {
            this.readWriteLock.writeLock().unlock();
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
        Collection<E> values = this.store.values();
        this.sortSubjList.clear();
        this.sortSubjList.addAll(values);
        if (this.getComparator() == null) {
            this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u540c\u6b65\u7ed3\u675f", (Object)this.getCacheDefine().getCode(), (Object)this.getCacheDefine().getName());
            return;
        }
        this.sortSubjList.sort(this.getComparator());
        this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u540c\u6b65\u7ed3\u675f", (Object)this.getCacheDefine().getCode(), (Object)this.getCacheDefine().getName());
    }

    @Override
    public void cleanCache() {
        if (!this.store.isEmpty()) {
            try {
                this.readWriteLock.writeLock().lock();
                this.store.clear();
            }
            finally {
                this.readWriteLock.writeLock().unlock();
            }
        }
    }

    @Override
    public List<E> list() {
        try {
            this.readWriteLock.readLock().lock();
            if (CollectionUtils.isEmpty(this.store.values())) {
                this.reloadCache(false);
            }
            List<E> list = this.sortSubjList;
            return list;
        }
        finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    public E get(String cacheKey) {
        try {
            this.readWriteLock.readLock().lock();
            if (CollectionUtils.isEmpty(this.store.values())) {
                this.reloadCache(false);
            }
            CacheEntity cacheEntity = (CacheEntity)this.store.get(cacheKey);
            return (E)cacheEntity;
        }
        finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    public Comparator<E> getComparator() {
        return this.comparator;
    }
}

