/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.util.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E ... items) {
        ArrayList<E> result = CollectionUtils.newArrayList();
        if (items != null) {
            for (E item : items) {
                result.add(item);
            }
        }
        return result;
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> Hashtable<K, V> newHashTable() {
        return new Hashtable();
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static <E> HashSet<E> newHashSet(Collection values) {
        HashSet result = new HashSet();
        if (null != values) {
            result.addAll(values);
        }
        return result;
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static <T> Collection<T> diff(Collection<T> c1, Collection<T> c2) {
        if (c1 == null || c1.size() == 0 || c2 == null || c2.size() == 0) {
            return c1;
        }
        ArrayList<T> difference = CollectionUtils.newArrayList();
        for (T item : c1) {
            if (c2.contains(item)) continue;
            difference.add(item);
        }
        return difference;
    }

    public static <T> boolean isEmpty(Collection<T> c) {
        if (c == null || c.size() == 0) {
            return true;
        }
        for (T item : c) {
            if (item == null) continue;
            return false;
        }
        return true;
    }

    public static <T> boolean isEmpty(T[] c) {
        if (c == null || c.length == 0) {
            return true;
        }
        return CollectionUtils.isEmpty(Arrays.asList(c));
    }

    public static <T> T getItem(List<T> list, int index) {
        return index < 0 || index >= list.size() ? null : (T)list.get(index);
    }

    public static <T> T getItem(List<T> list, int index, T defaultValue) {
        T result = CollectionUtils.getItem(list, index);
        return result == null ? defaultValue : result;
    }

    public static <T> T getItem(T[] array, int index) {
        return index < 0 || index >= array.length ? null : (T)array[index];
    }

    public static <T> T getItem(T[] array, int index, T defaultValue) {
        T result = CollectionUtils.getItem(array, index);
        return result == null ? defaultValue : result;
    }

    public static <T> boolean contains(T[] list, T item) {
        Assert.isNotNull(item);
        Assert.isNotNull(list);
        return CollectionUtils.indexOf(list, item) != -1;
    }

    public static <T> int indexOf(T[] list, T item) {
        Assert.isNotNull(item);
        Assert.isNotNull(list);
        for (int i = 0; i < list.length; ++i) {
            if (list[i] != item) continue;
            return i;
        }
        return -1;
    }

    public static <T> String toString(List<T> list) {
        return CollectionUtils.toString(list, ",");
    }

    public static <T> String toString(List<T> list, String splitter) {
        StringBuilder sb = new StringBuilder();
        for (T item : list) {
            if (item != null) {
                sb.append(item.toString());
            }
            if (splitter == null) continue;
            sb.append(splitter);
        }
        if (splitter != null && sb.length() > 0) {
            sb.delete(sb.length() - splitter.length(), sb.length());
        }
        return sb.toString();
    }

    public static <T> String toString(T[] array) {
        return CollectionUtils.toString(array, ",");
    }

    public static <T> String toString(T[] array, String splitter) {
        return CollectionUtils.toString(CollectionUtils.newArrayList(array), splitter);
    }
}

