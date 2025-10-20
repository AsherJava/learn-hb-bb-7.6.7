/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.dc.base.common.cache.proider;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.cache.proider.BaseCacheProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class ArrayListCacheProvider<E>
extends BaseCacheProvider<E> {
    private final List<E> store = Collections.synchronizedList(new ArrayList());
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

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
        Collection entitys = this.loadCache();
        if (CollectionUtils.isEmpty(entitys)) {
            return;
        }
        for (Object e : entitys) {
            this.store.add(e);
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
            if (this.store.isEmpty()) {
                this.reloadCache(false);
            }
            lock.unlock();
            isLock = false;
            List<E> list = Collections.unmodifiableList(this.store);
            return list;
        }
        finally {
            if (lock != null && isLock) {
                lock.unlock();
            }
        }
    }
}

