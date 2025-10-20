/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.dc.base.common.utils.SqlExecutorUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionUtil {
    private static Logger logger = LoggerFactory.getLogger(SqlExecutorUtil.class);

    private CollectionUtil() {
        throw new IllegalStateException();
    }

    public static void printCurrentTime(String prefixStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        logger.info(prefixStr + sdf.format(new Date()));
    }

    public static <E> Set<E> convertHashSet(List<E> items) {
        if (items == null || items.size() == 0) {
            return new HashSet();
        }
        HashSet<E> set = new HashSet<E>();
        for (E item : items) {
            set.add(item);
        }
        return set;
    }

    public static <E> List<E> convertArrayList(Set<E> items) {
        if (items == null || items.size() == 0) {
            return new ArrayList();
        }
        ArrayList<E> result = new ArrayList<E>(items.size());
        for (E item : items) {
            result.add(item);
        }
        return result;
    }

    public static <E> List<E> newArrayList(E ... items) {
        if (items == null || items.length == 0) {
            return new ArrayList();
        }
        ArrayList<E> result = new ArrayList<E>(items.length);
        for (E item : items) {
            result.add(item);
        }
        return result;
    }

    public static <K, V> List<V> convertMapToList(Map<K, V> map) {
        if (map == null || map.size() == 0) {
            return new ArrayList();
        }
        ArrayList<V> list = new ArrayList<V>(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public static <T> List<T> filterList(List<T> list, Set<T> filterSet, boolean ignoreFlag) {
        if (list == null || list.size() == 0) {
            return null;
        }
        if (filterSet == null || filterSet.size() == 0) {
            return ignoreFlag ? list : null;
        }
        ArrayList<T> resultList = new ArrayList<T>(list.size() * 2);
        for (T item : list) {
            if (ignoreFlag && filterSet.contains(item) || !ignoreFlag && !filterSet.contains(item)) continue;
            resultList.add(item);
        }
        return resultList;
    }

    public static String join(List<String> items, String separator) {
        if (items == null || items.size() == 0) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder result = new StringBuilder();
        int count = items.size();
        for (int i = 0; i < count; ++i) {
            if (i > 0) {
                result.append(separator);
            }
            result.append(items.get(i));
        }
        return result.toString();
    }

    public static <K, V> void printListMap(List<Map<K, V>> list) {
        if (list == null || list.size() == 0) {
            logger.info("list is empty.");
            return;
        }
        for (Map<K, V> map : list) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                logger.info(entry.getKey() + " = " + entry.getValue() + "\t");
            }
            logger.info("\n");
        }
    }

    public static <E> void printList(List<E> list) {
        if (list == null || list.size() == 0) {
            logger.info("list is empty.");
            return;
        }
        for (E obj : list) {
            logger.info(Objects.isNull(obj) ? "null" : obj.toString());
        }
    }

    public static <K, V> void print(Map<K, V> map) {
        if (map == null || map.size() == 0) {
            logger.info("map is empty.");
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            logger.info(entry.getKey() + " = " + entry.getValue());
        }
    }
}

