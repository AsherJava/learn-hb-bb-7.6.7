/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.util.MapBeanUtilsBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MapUtils {
    public static <T> void add(Map<T, Double> cacheMap, T key, Double value) {
        if (null == value) {
            return;
        }
        Double number = cacheMap.get(key);
        if (number == null) {
            cacheMap.put(key, value);
        } else {
            cacheMap.put(key, NumberUtils.sum((Double)number, (Double)value));
        }
    }

    public static <T> void sub(Map<T, Double> cacheMap, T key, Double value) {
        if (null == value) {
            return;
        }
        Double number = cacheMap.get(key);
        if (number == null) {
            cacheMap.put(key, -value.doubleValue());
        } else {
            cacheMap.put(key, NumberUtils.sub((Double)number, (Double)value));
        }
    }

    public static <T> void add(Map<T, BigDecimal> cacheMap, T key, BigDecimal value) {
        if (null == value || BigDecimal.ZERO.compareTo(value) == 0) {
            return;
        }
        BigDecimal number = cacheMap.get(key);
        if (number == null) {
            cacheMap.put(key, value);
        } else {
            cacheMap.put(key, number.add(value));
        }
    }

    public static <T> void sub(Map<T, BigDecimal> cacheMap, T key, BigDecimal value) {
        if (null == value || BigDecimal.ZERO.compareTo(value) == 0) {
            return;
        }
        BigDecimal number = cacheMap.get(key);
        if (number == null) {
            cacheMap.put(key, BigDecimal.ZERO.subtract(value));
        } else {
            cacheMap.put(key, number.subtract(value));
        }
    }

    public static int compareStr(Map map1, Map map2, Object key) {
        String value1 = (String)map1.get(key);
        if (value1 == null) {
            return -1;
        }
        String value2 = (String)map2.get(key);
        if (value2 == null) {
            return 1;
        }
        return value1.compareTo(value2);
    }

    public static int compareUUID(Map map1, Map map2, Object key) {
        UUID value1 = (UUID)map1.get(key);
        if (value1 == null) {
            return -1;
        }
        UUID value2 = (UUID)map2.get(key);
        if (value2 == null) {
            return 1;
        }
        return value1.toString().compareTo(value2.toString());
    }

    public static int compareInt(Map map1, Map map2, Object key) {
        Integer value1 = (Integer)map1.get(key);
        if (value1 == null) {
            return -1;
        }
        Integer value2 = (Integer)map2.get(key);
        if (value2 == null) {
            return 1;
        }
        return value1 - value2;
    }

    public static int compareDouble(Map map1, Map map2, Object key) {
        Double value1 = (Double)map1.get(key);
        if (value1 == null) {
            return -1;
        }
        Double value2 = (Double)map2.get(key);
        if (value2 == null) {
            return 1;
        }
        return value1.compareTo(value2);
    }

    public static String getStr(Map map, Object key) {
        String value = ConverterUtils.getAsString(map.get(key));
        if (null == value) {
            return "";
        }
        return value;
    }

    public static <T> T getVal(Map<? extends Object, T> map, Object key, T defaultVal) {
        T value = map.get(key);
        if (null == value) {
            return defaultVal;
        }
        return value;
    }

    public static UUID getUUID(Map map, Object key) {
        Object value = map.get(key);
        if (null == value) {
            return null;
        }
        if (value instanceof UUID) {
            return (UUID)value;
        }
        return UUID.fromString(String.valueOf(value));
    }

    public static Integer getInteger(Map map, Object key) {
        return (Integer)map.get(key);
    }

    public static int getInt(Map map, Object key) {
        Integer value = (Integer)map.get(key);
        if (null == value) {
            return 0;
        }
        return value;
    }

    public static double doubleValue(Map map, Object key) {
        Double value = (Double)map.get(key);
        if (null != value) {
            return value;
        }
        return 0.0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static void populate(Object bean, Map<String, ?> properties) {
        MapBeanUtilsBean.getInstance().populate(bean, properties);
    }

    public static List<String> listAllChildrens(String parent, Map<String, List<String>> parent2DirectChildrenCacheMap) {
        if (MapUtils.isEmpty(parent2DirectChildrenCacheMap)) {
            return Collections.emptyList();
        }
        ArrayList<String> result = new ArrayList<String>();
        MapUtils.listAllChildrens(parent, result, parent2DirectChildrenCacheMap);
        return result;
    }

    private static void listAllChildrens(String parent, List<String> result, Map<String, List<String>> parent2DirectChildrenCacheMap) {
        List<String> directChildrens = parent2DirectChildrenCacheMap.get(parent);
        if (CollectionUtils.isEmpty(directChildrens)) {
            return;
        }
        result.addAll(directChildrens);
        for (String children : directChildrens) {
            MapUtils.listAllChildrens(children, result, parent2DirectChildrenCacheMap);
        }
    }
}

