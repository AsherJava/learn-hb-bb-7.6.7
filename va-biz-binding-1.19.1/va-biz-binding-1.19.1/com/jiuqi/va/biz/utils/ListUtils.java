/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.utils;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListUtils.class);

    public static <E> void sort(List<E> list, final boolean isAsc, final String ... sortnameArr) {
        Collections.sort(list, new Comparator<E>(){

            @Override
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length && 0 == (ret = ListUtils.compareObject(sortnameArr[i], isAsc, false, a, b)); ++i) {
                    }
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return ret;
            }
        });
    }

    public static <E> void sort(List<E> list, final String[] sortnameArr, final Boolean[] typeArr) {
        if (sortnameArr.length != typeArr.length) {
            throw new RuntimeException("\u5c5e\u6027\u6570\u7ec4\u5143\u7d20\u4e2a\u6570\u548c\u5347\u964d\u5e8f\u6570\u7ec4\u5143\u7d20\u4e2a\u6570\u4e0d\u76f8\u7b49");
        }
        Collections.sort(list, new Comparator<E>(){

            @Override
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length && 0 == (ret = ListUtils.compareObject(sortnameArr[i], typeArr[i], false, a, b)); ++i) {
                    }
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return ret;
            }
        });
    }

    public static <E> void sort(List<E> list, final String[] sortnameArr, final Boolean[] typeArr, final Boolean[] nullMaxArr) {
        if (sortnameArr.length != typeArr.length) {
            throw new RuntimeException("\u5c5e\u6027\u6570\u7ec4\u5143\u7d20\u4e2a\u6570\u548c\u5347\u964d\u5e8f\u6570\u7ec4\u5143\u7d20\u4e2a\u6570\u4e0d\u76f8\u7b49");
        }
        Collections.sort(list, new Comparator<E>(){

            @Override
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length && 0 == (ret = ListUtils.compareObject(sortnameArr[i], typeArr[i], nullMaxArr[i], a, b)); ++i) {
                    }
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return ret;
            }
        });
    }

    public static String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumIntegerDigits(length);
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }

    public static <E> int compareObject(String sortname, boolean isAsc, boolean nullMax, E a, E b) throws Exception {
        Object value1 = ListUtils.forceGetFieldValue(a, sortname);
        Object value2 = ListUtils.forceGetFieldValue(b, sortname);
        if (nullMax) {
            if (value1 == null && value2 == null) {
                return 0;
            }
            if (value1 == null) {
                return isAsc ? 1 : -1;
            }
            if (value2 == null) {
                return isAsc ? -1 : 1;
            }
        } else {
            if (value1 == null && value2 == null) {
                return 0;
            }
            if (value1 == null) {
                return isAsc ? -1 : 1;
            }
            if (value2 == null) {
                return isAsc ? 1 : -1;
            }
        }
        String str1 = value1.toString();
        String str2 = value2.toString();
        if (value1 instanceof Number && value2 instanceof Number) {
            int maxlen = Math.max(str1.length(), str2.length());
            str1 = ListUtils.addZero2Str((Number)value1, maxlen);
            str2 = ListUtils.addZero2Str((Number)value2, maxlen);
        } else if (value1 instanceof Date && value2 instanceof Date) {
            long time1 = ((Date)value1).getTime();
            long time2 = ((Date)value2).getTime();
            int maxlen = Long.toString(Math.max(time1, time2)).length();
            str1 = ListUtils.addZero2Str(time1, maxlen);
            str2 = ListUtils.addZero2Str(time2, maxlen);
        }
        int ret = isAsc ? str1.compareTo(str2) : str2.compareTo(str1);
        return ret;
    }

    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        if (obj instanceof Map) {
            return ((Map)obj).get(fieldName);
        }
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object = null;
        boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
            object = field.get(obj);
            field.setAccessible(accessible);
            return object;
        }
        object = field.get(obj);
        return object;
    }
}

