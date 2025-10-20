/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.utils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

public final class BeanConvertUtil
extends BeanUtils {
    private BeanConvertUtil() {
    }

    public static <F, T> List<T> convert(List<F> objs, Class<T> toClass, String ... ignoreProperties) {
        if (objs == null || objs.isEmpty()) {
            return Collections.emptyList();
        }
        return objs.stream().map(obj -> BeanConvertUtil.convert(obj, toClass, ignoreProperties)).collect(Collectors.toList());
    }

    public static <F, T> T convert(F obj, Class<T> toClass, String ... ignoreProperties) {
        Assert.notNull(toClass, "'toClass' must not be null");
        if (obj == null) {
            return null;
        }
        try {
            Constructor<T> constructor = BeanConvertUtil.findConstructor(toClass, obj.getClass());
            if (constructor != null) {
                return constructor.newInstance(obj);
            }
            T v = toClass.newInstance();
            BeanUtils.copyProperties(obj, v, ignoreProperties);
            return v;
        }
        catch (Exception e) {
            LoggerFactory.getLogger(BeanConvertUtil.class).error("bean\u8f6c\u6362\u5931\u8d25\uff01", e);
            return null;
        }
    }

    private static <T, P> Constructor<T> findConstructor(Class<T> toClass, Class<P> paramClass) {
        for (Constructor<?> c : toClass.getConstructors()) {
            if (c.getParameterCount() != 1 || !c.getParameterTypes()[0].isAssignableFrom(paramClass)) continue;
            return c;
        }
        return null;
    }
}

