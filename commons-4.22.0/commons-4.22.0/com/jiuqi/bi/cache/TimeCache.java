/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.cache;

import com.jiuqi.bi.cache.CacheTimeout;
import com.jiuqi.bi.cache.ICache;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class TimeCache<T>
implements ICache<T>,
Serializable {
    private static final long serialVersionUID = 4635910177179787266L;
    private static final int DEFAULT_CAPACITY = 32;
    private static final int DEFAULT_PACKSIZE = 8;
    private final long timeout;
    private AtomicLong curId;
    private Queue<Entry> entries;
    private Map<Long, Entry> finder;
    private Map<Object, Entry> keyFinder;
    private final int capacity;
    private AtomicInteger count;
    private final int packSize;

    public TimeCache(long timeout) {
        this(32, timeout, 8);
    }

    public TimeCache(int capacity, long timeout, int packSize) {
        this.timeout = timeout;
        this.curId = new AtomicLong(0L);
        this.entries = new ConcurrentLinkedQueue<Entry>();
        this.finder = new ConcurrentHashMap<Long, Entry>();
        this.keyFinder = new ConcurrentHashMap<Object, Entry>();
        this.count = new AtomicInteger(0);
        this.capacity = capacity;
        this.packSize = packSize;
    }

    @Override
    public T get(long id) throws CacheTimeout {
        if (id > this.curId.get()) {
            return null;
        }
        Entry e = this.finder.get(id);
        if (e == null || e.isExpired()) {
            throw new CacheTimeout("\u7f13\u51b2\u5bf9\u8c61\u8d85\u65f6\u6216\u5df2\u4ece\u7f13\u5b58\u4e2d\u6e05\u9664\uff01");
        }
        return e.get();
    }

    @Override
    public T getByKey(Object key) throws CacheTimeout {
        Entry e = this.keyFinder.get(key);
        if (e == null) {
            return null;
        }
        if (e.isExpired()) {
            throw new CacheTimeout("\u7f13\u51b2\u5bf9\u8c61\u8d85\u65f6\u6216\u5df2\u4ece\u7f13\u5b58\u4e2d\u6e05\u9664\uff01");
        }
        return e.get();
    }

    private void pack() {
        if (this.count.get() <= this.packSize) {
            return;
        }
        if (this.timeout > 0L) {
            long curTime = System.currentTimeMillis();
            Iterator<Entry> itr = this.entries.iterator();
            while (itr.hasNext()) {
                Entry e = (Entry)itr.next();
                if (e == null) continue;
                if (e.get() == null) {
                    this.removeItem(itr, e);
                    continue;
                }
                if (!e.isExpired(curTime)) continue;
                this.removeItem(itr, e);
                this.count.decrementAndGet();
            }
        }
        if (this.count.get() >= this.capacity) {
            Entry eldest = null;
            Iterator<Entry> itr = this.entries.iterator();
            while (itr.hasNext()) {
                Entry e = (Entry)itr.next();
                if (e == null) continue;
                if (e.get() == null) {
                    this.removeItem(itr, e);
                    continue;
                }
                if (eldest == null) {
                    eldest = e;
                    continue;
                }
                if (e.lastActive >= eldest.lastActive) continue;
                eldest = e;
            }
            if (eldest != null) {
                this.finder.remove(eldest.id);
                if (eldest.key != null) {
                    this.keyFinder.remove(eldest.key);
                }
                eldest.discard();
                this.count.decrementAndGet();
            }
        }
    }

    private void removeItem(Iterator<Entry> itr, Entry e) {
        this.finder.remove(e.id);
        if (e.key != null) {
            this.keyFinder.remove(e.key);
        }
        itr.remove();
    }

    @Override
    public long put(T item) {
        this.pack();
        long id = this.curId.incrementAndGet();
        Entry e = new Entry(id, item, null);
        this.finder.put(id, e);
        this.entries.offer(e);
        this.count.incrementAndGet();
        return id;
    }

    @Override
    public long put(Object key, T item) {
        this.pack();
        long id = this.curId.incrementAndGet();
        Entry e = new Entry(id, item, key);
        this.finder.put(id, e);
        this.keyFinder.put(key, e);
        this.entries.offer(e);
        this.count.incrementAndGet();
        return id;
    }

    @Override
    public void remove(long id) {
        Entry e = this.finder.remove(id);
        if (e != null) {
            if (e.key != null) {
                this.keyFinder.remove(e.key);
            }
            e.discard();
            this.count.decrementAndGet();
        }
    }

    @Override
    public void removeByKey(String key) {
        Entry e = this.keyFinder.remove(key);
        if (e != null) {
            this.finder.remove(e.id);
            e.discard();
            this.count.decrementAndGet();
        }
    }

    private final class Entry
    implements Serializable {
        private static final long serialVersionUID = 8084240640856082478L;
        public final long id;
        public final Object key;
        private T item;
        private volatile long lastActive;

        public Entry(long id, T item, Object key) {
            this.id = id;
            this.key = key;
            this.item = item;
            if (TimeCache.this.timeout != 0L) {
                this.lastActive = System.currentTimeMillis();
            }
        }

        public T get() {
            if (TimeCache.this.timeout != 0L) {
                this.lastActive = System.currentTimeMillis();
            }
            return this.item;
        }

        public boolean isExpired() {
            return TimeCache.this.timeout > 0L && System.currentTimeMillis() - this.lastActive > TimeCache.this.timeout;
        }

        public boolean isExpired(long curTime) {
            return this.lastActive == 0L ? true : TimeCache.this.timeout > 0L && curTime - this.lastActive > TimeCache.this.timeout;
        }

        public void discard() {
            this.item = null;
            this.lastActive = 0L;
        }
    }
}

