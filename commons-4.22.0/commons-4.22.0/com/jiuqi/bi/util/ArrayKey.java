/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ArrayKey
implements Cloneable,
Comparable<ArrayKey> {
    private final Object[] arr;
    private static final ArrayKey EMPTY_KEY = new ArrayKey(new Object[0]);

    public ArrayKey(Object ... arr) {
        this.arr = arr;
    }

    public ArrayKey(Collection<?> arr) {
        this.arr = arr.toArray();
    }

    public static ArrayKey of(Object ... keys) {
        return new ArrayKey(keys);
    }

    public static ArrayKey of(Collection<?> keys) {
        return new ArrayKey(keys);
    }

    public static ArrayKey copyOf(Object ... keys) {
        return ArrayKey.of((Object[])keys.clone());
    }

    public static ArrayKey emptyKey() {
        return EMPTY_KEY;
    }

    public int size() {
        return this.arr.length;
    }

    public Object get(int index) {
        return this.arr[index];
    }

    public List<?> getValues() {
        return Arrays.asList(this.arr);
    }

    public ArrayKey append(Object key) {
        Object[] keys = new Object[this.arr.length + 1];
        System.arraycopy(this.arr, 0, keys, 0, this.arr.length);
        keys[this.arr.length] = key;
        return new ArrayKey(keys);
    }

    public ArrayKey append(Object ... keys) {
        Object[] newKeys = new Object[this.arr.length + keys.length];
        System.arraycopy(this.arr, 0, newKeys, 0, this.arr.length);
        System.arraycopy(keys, 0, newKeys, this.arr.length, keys.length);
        return new ArrayKey(newKeys);
    }

    public ArrayKey append(Collection<?> keys) {
        Object[] newKeys = new Object[this.arr.length + keys.size()];
        System.arraycopy(this.arr, 0, newKeys, 0, this.arr.length);
        int p = this.arr.length;
        Iterator<?> i = keys.iterator();
        while (i.hasNext()) {
            newKeys[p] = i.next();
            ++p;
        }
        return new ArrayKey(newKeys);
    }

    public int hashCode() {
        return Arrays.hashCode(this.arr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArrayKey)) {
            return false;
        }
        ArrayKey key = (ArrayKey)obj;
        return Arrays.equals(this.arr, key.arr);
    }

    public String toString() {
        return Arrays.toString(this.arr);
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(ArrayKey arr) {
        int len = Math.min(this.size(), arr.size());
        for (int i = 0; i < len; ++i) {
            Object v2;
            Object v1 = this.get(i);
            if (v1 == (v2 = arr.get(i))) continue;
            if (v1 == null) {
                return -1;
            }
            if (v2 == null) {
                return 1;
            }
            int cmp = ((Comparable)v1).compareTo(v2);
            if (cmp == 0) continue;
            return cmp;
        }
        if (len < this.size()) {
            return 1;
        }
        if (len < arr.size()) {
            return -1;
        }
        return 0;
    }
}

