/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class IdMutexProvider {
    private final Map<Mutex, WeakReference<Mutex>> mutexMap = new WeakHashMap<Mutex, WeakReference<Mutex>>();

    public Mutex getMutex(String id) {
        if (id == null) {
            throw new NullPointerException();
        }
        MutexImpl key = new MutexImpl(id);
        return this.getMutex(key);
    }

    public Mutex getMutex(Set<String> ids) {
        MutexSetImpl key = new MutexSetImpl(ids);
        return this.getMutex(key);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Mutex getMutex(Mutex key) {
        Map<Mutex, WeakReference<Mutex>> map = this.mutexMap;
        synchronized (map) {
            WeakReference<Mutex> ref = this.mutexMap.get(key);
            if (ref == null) {
                this.mutexMap.put(key, new WeakReference<Mutex>(key));
                return key;
            }
            Mutex mutex = (Mutex)ref.get();
            if (mutex == null) {
                this.mutexMap.put(key, new WeakReference<Mutex>(key));
                return key;
            }
            return mutex;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getMutexCount() {
        Map<Mutex, WeakReference<Mutex>> map = this.mutexMap;
        synchronized (map) {
            return this.mutexMap.size();
        }
    }

    private static class MutexSetImpl
    implements Mutex {
        private final Set<String> ids;

        public MutexSetImpl(Set<String> ids) {
            if (ids == null) {
                throw new NullPointerException();
            }
            if (ids.isEmpty()) {
                throw new NullPointerException();
            }
            for (String id : ids) {
                if (id != null) continue;
                throw new NullPointerException();
            }
            this.ids = ids;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            MutexSetImpl mutexSet = (MutexSetImpl)o;
            for (String next : this.ids) {
                if (!mutexSet.ids.contains(next)) continue;
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.ids != null ? this.ids.hashCode() : 0;
        }
    }

    private static class MutexImpl
    implements Mutex {
        private final String id;

        protected MutexImpl(String id) {
            if (id == null) {
                throw new NullPointerException();
            }
            this.id = id;
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this.getClass() == o.getClass()) {
                return this.id.equals(o.toString());
            }
            return false;
        }

        public int hashCode() {
            return this.id.hashCode();
        }

        public String toString() {
            return this.id;
        }
    }

    public static interface Mutex {
    }
}

