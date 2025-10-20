/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.util.StringUtils;
import java.util.Collection;
import java.util.Map;

public class Assert {
    public static void isTrue(boolean expression) {
        Assert.isTrue(expression, "\u65ad\u8a00\u5931\u8d25\uff1a\u8868\u8fbe\u5f0f\u7ed3\u679c\u5e94\u4e3atrue\u3002", new Object[0]);
    }

    public static void isTrue(boolean expression, String message, Object ... args) {
        if (!expression) {
            Assert.throwException(message, args);
        }
    }

    public static void isFalse(boolean expression) {
        Assert.isTrue(!expression, "\u65ad\u8a00\u5931\u8d25\uff1a\u8868\u8fbe\u5f0f\u7ed3\u679c\u5e94\u4e3afalse\u3002", new Object[0]);
    }

    public static void isFalse(boolean expression, String message, Object ... args) {
        Assert.isTrue(!expression, message, args);
    }

    public static void isNull(Object object) {
        Assert.isNull(object, "\u65ad\u8a00\u5931\u8d25\uff1a\u5bf9\u8c61\u5e94\u4e3anull\u3002", new Object[0]);
    }

    public static void isNull(Object object, String message, Object ... args) {
        if (object != null) {
            Assert.throwException(message, args);
        }
    }

    public static void isNotNull(Object object) {
        Assert.isNotNull(object, "\u65ad\u8a00\u5931\u8d25\uff1a\u5bf9\u8c61\u4e0d\u5e94\u4e3anull\u3002", new Object[0]);
    }

    public static void isNotNull(Object object, String message, Object ... args) {
        if (object == null) {
            Assert.throwException(message, args);
        }
    }

    public static void isNotEmpty(Collection<?> collection) {
        Assert.isNotEmpty(collection, "\u65ad\u8a00\u5931\u8d25\uff1a\u96c6\u5408\u4e2d\u81f3\u5c11\u5e94\u5305\u542b\u4e00\u4e2a\u6570\u636e\u9879\u3002", new Object[0]);
    }

    public static void isNotEmpty(Collection<?> collection, String message, Object ... args) {
        if (collection == null || collection.isEmpty()) {
            Assert.throwException(message, args);
        }
    }

    public static <T> void isNotEmpty(T[] array) {
        Assert.isNotEmpty(array, "\u65ad\u8a00\u5931\u8d25\uff1a\u6570\u7ec4\u4e2d\u81f3\u5c11\u5e94\u5305\u542b\u4e00\u4e2a\u6570\u636e\u9879\u3002", new Object[0]);
    }

    public static <T> void isNotEmpty(T[] array, String message, Object ... args) {
        if (array == null || array.length == 0) {
            Assert.throwException(message, args);
        }
    }

    public static void isNotEmpty(Map<?, ?> map) {
        Assert.isNotEmpty(map, "\u65ad\u8a00\u5931\u8d25\uff1aMap\u4e2d\u81f3\u5c11\u5e94\u5305\u542b\u4e00\u4e2a\u6570\u636e\u9879\u3002", new Object[0]);
    }

    public static void isNotEmpty(Map<?, ?> map, String message, Object ... args) {
        if (map == null || map.isEmpty()) {
            Assert.throwException(message, args);
        }
    }

    public static void isNotEmpty(String str) {
        Assert.isFalse(StringUtils.isEmpty(str), "\u65ad\u8a00\u5931\u8d25\uff1a\u5b57\u7b26\u4e32\u4e0d\u5e94\u4e3a\u7a7a\u3002", new Object[0]);
    }

    public static void isNotEmpty(String str, String message, Object ... args) {
        Assert.isFalse(StringUtils.isEmpty(str), message, args);
    }

    public static void isZero(Number number) {
        Assert.isNotZero(number, "\u65ad\u8a00\u5931\u8d25\uff1a\u6570\u5b57\u4e0d\u5e94\u4e3a0\u3002", new Object[0]);
    }

    public static void isZero(Number number, String message, Object ... args) {
        Assert.isNotNull(number, message, args);
        Assert.isTrue(number.doubleValue() == 0.0, message, args);
    }

    public static void isNotZero(Number number) {
        Assert.isNotZero(number, "\u65ad\u8a00\u5931\u8d25\uff1a\u6570\u5b57\u4e0d\u5e94\u4e3a0\u3002", new Object[0]);
    }

    public static void isNotZero(Number number, String message, Object ... args) {
        Assert.isNotNull(number, message, args);
        Assert.isFalse(number.doubleValue() == 0.0, message, args);
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        Assert.isNotNull(clazz, "\u65ad\u8a00\u5931\u8d25\uff1a\u6307\u5b9a\u7c7b\u578b\u4e0d\u5e94\u4e3a\u7a7a\u3002", new Object[0]);
        Assert.isNotNull(obj);
        if (!clazz.isInstance(obj)) {
            Assert.throwException("\u65ad\u8a00\u5931\u8d25\uff1a\u5bf9\u8c61\u7c7b\u578b\u5e94\u4e3a%s\u3002", clazz.getName());
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object obj, String message, Object ... args) {
        Assert.isNotNull(clazz, message, args);
        Assert.isNotNull(obj, message, args);
        if (!clazz.isInstance(obj)) {
            Assert.throwException(message, args);
        }
    }

    private static void throwException(String message, Object ... args) {
        throw new IllegalArgumentException(args == null || args.length == 0 ? message : String.format(message, args));
    }
}

