/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class ArrayMap<K, V>
extends AbstractMap<K, V>
implements SortedMap<K, V>,
Cloneable {
    private final Comparator<? super K> comparator;
    private List<Map.Entry<K, V>> entries;
    private transient EntrySet entrySet;
    private transient Entry<K, V> tmpEntry;
    private transient Comparator<Map.Entry<K, V>> entryComparator;

    ArrayMap(List<Map.Entry<K, V>> entries, Comparator<? super K> comparator) {
        this.entries = entries == null ? new ArrayList() : entries;
        this.comparator = comparator;
        this.tmpEntry = new Entry();
        this.entryComparator = new EntryComparator();
    }

    public ArrayMap() {
        this(null, null);
    }

    public ArrayMap(Comparator<? super K> comparator) {
        this(null, comparator);
    }

    public ArrayMap(Map<? extends K, ? extends V> m) {
        this();
        this.putAll(m);
    }

    public ArrayMap(SortedMap<K, ? extends V> m) {
        this(null, m.comparator());
        for (Map.Entry<K, V> e : m.entrySet()) {
            Entry<K, V> entry = new Entry<K, V>(e.getKey(), e.getValue());
            this.entries.add(entry);
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet es = this.entrySet;
        return es == null ? (this.entrySet = new EntrySet()) : es;
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.comparator;
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        int fromIndex = this.locateKey(fromKey);
        int toIndex = this.locateKey(toKey);
        return this.subMap(fromIndex, fromKey, toIndex, toKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        int toIndex = this.locateKey(toKey);
        return this.subMap(0, null, toIndex, toKey);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        int fromIndex = this.locateKey(fromKey);
        return this.subMap(fromIndex, fromKey, this.entries.size(), null);
    }

    private SortedMap<K, V> subMap(int fromIndex, K fromKey, int toIndex, K toKey) {
        return new SubArrayMap<K, V>(this.entries.subList(fromIndex, toIndex), this.comparator, fromKey, toKey);
    }

    @Override
    public K firstKey() {
        return this.entries.isEmpty() ? null : (K)this.entries.get(0).getKey();
    }

    @Override
    public K lastKey() {
        return this.entries.isEmpty() ? null : (K)this.entries.get(this.entries.size() - 1).getKey();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.searchKey(key) >= 0;
    }

    @Override
    public V get(Object key) {
        int index = this.searchKey(key);
        return index < 0 ? null : (V)this.entries.get(index).getValue();
    }

    @Override
    public V put(K key, V value) {
        V raw = null;
        int index = this.searchKey(key);
        if (index < 0) {
            int p = ArrayMap.getInsertPoint(index);
            this.entries.add(p, new Entry<K, V>(key, value));
        } else {
            Map.Entry<K, V> entry = this.entries.get(index);
            raw = entry.getValue();
            entry.setValue(value);
        }
        return raw;
    }

    @Override
    public V remove(Object key) {
        int index = this.searchKey(key);
        if (index < 0) {
            return null;
        }
        Map.Entry<K, V> entry = this.entries.remove(index);
        return entry.getValue();
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    private int searchKey(K key) {
        this.tmpEntry.setKey(key);
        return Collections.binarySearch(this.entries, this.tmpEntry, this.entryComparator);
    }

    private int locateKey(K key) {
        int index = this.searchKey(key);
        return index >= 0 ? index : ArrayMap.getInsertPoint(index);
    }

    protected int compareKey(K key1, K key2) {
        if (this.comparator != null) {
            return this.comparator.compare(key1, key2);
        }
        return ((Comparable)key1).compareTo(key2);
    }

    private static int getInsertPoint(int index) {
        return -(index + 1);
    }

    private static boolean objectEquals(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public Object clone() {
        return this.clone(true);
    }

    protected Object clone(boolean deepMode) {
        ArrayMap result;
        try {
            result = (ArrayMap)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        if (deepMode) {
            result.entries = new ArrayList<Map.Entry<K, V>>(this.entries.size());
            for (Map.Entry<K, V> entry : this.entries) {
                result.entries.add((Map.Entry<K, V>)((Entry)entry).clone());
            }
        }
        result.entrySet = null;
        result.tmpEntry = new Entry();
        return result;
    }

    private static final class SubArrayMap<K, V>
    extends ArrayMap<K, V> {
        private final K lowKey;
        private final K highKey;

        public SubArrayMap(List<Map.Entry<K, V>> entries, Comparator<? super K> comparator, K lowKey, K highKey) {
            super(entries, comparator);
            this.lowKey = lowKey;
            this.highKey = highKey;
        }

        private boolean inRange(K key) {
            if (this.lowKey != null && this.compareKey(key, this.lowKey) < 0) {
                return false;
            }
            return this.highKey == null || this.compareKey(key, this.highKey) < 0;
        }

        @Override
        public V put(K key, V value) {
            if (!this.inRange(key)) {
                throw new IllegalArgumentException("\u952e\u503c\u8d8a\u754c\uff1a" + key);
            }
            return super.put(key, value);
        }

        @Override
        public V remove(Object key) {
            if (!this.inRange(key)) {
                throw new IllegalArgumentException("\u952e\u503c\u8d8a\u754c\uff1a" + key);
            }
            return super.remove(key);
        }

        @Override
        public Object clone() {
            return this.clone(false);
        }
    }

    private final class EntryComparator
    implements Comparator<Map.Entry<K, V>> {
        private EntryComparator() {
        }

        @Override
        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
            return ArrayMap.this.compareKey(o1.getKey(), o2.getKey());
        }
    }

    private class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return ArrayMap.this.entries.iterator();
        }

        @Override
        public int size() {
            return ArrayMap.this.entries.size();
        }

        @Override
        public boolean contains(Object o) {
            return Collections.binarySearch(ArrayMap.this.entries, (Map.Entry)o, ArrayMap.this.entryComparator) >= 0;
        }

        @Override
        public boolean add(Map.Entry<K, V> e) {
            ArrayMap.this.put(e.getKey(), e.getValue());
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)o;
            int index = Collections.binarySearch(ArrayMap.this.entries, entry, ArrayMap.this.entryComparator);
            if (index < 0) {
                return false;
            }
            if (!ArrayMap.objectEquals(((Map.Entry)ArrayMap.this.entries.get(index)).getValue(), entry.getValue())) {
                return false;
            }
            ArrayMap.this.entries.remove(index);
            return true;
        }

        @Override
        public void clear() {
            ArrayMap.this.entries.clear();
        }
    }

    private static final class Entry<K, V>
    implements Map.Entry<K, V>,
    Cloneable,
    Serializable {
        private static final long serialVersionUID = -1007729318967746871L;
        private K key;
        private V value;

        Entry() {
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        void setKey(K key) {
            this.key = key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V raw = this.value;
            this.value = value;
            return raw;
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)obj;
            return ArrayMap.objectEquals(this.key, entry.getKey()) && ArrayMap.objectEquals(this.value, entry.getValue());
        }

        public String toString() {
            StringBuilder buffer = new StringBuilder();
            buffer.append(this.key).append('=').append(this.value);
            return buffer.toString();
        }

        public Entry<K, V> clone() {
            try {
                return (Entry)super.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

