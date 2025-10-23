/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils;

import com.jiuqi.nr.formula.utils.ConvertMap;
import com.jiuqi.nr.formula.utils.HashConvertMap;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class BeanCopyUtils {
    public static void copyProperties(Object target, Object source) {
        BeanCopyUtils.copyProperties(target, source, null, (String[])null);
    }

    public static void copyProperties(Object target, Object source, @Nullable ConvertMap<String, String> map) {
        BeanCopyUtils.copyProperties(target, source, map, (String[])null);
    }

    public static void copyProperties(Object target, Object source, String ... targetIgnoreProperties) {
        BeanCopyUtils.copyProperties(target, source, null, targetIgnoreProperties);
    }

    public static void copyProperties(Object target, Object source, @Nullable ConvertMap<String, String> map, String ... targetIgnoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        if (map == null) {
            map = new HashConvertMap();
        }
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<Object> ignoreList = targetIgnoreProperties != null ? Arrays.asList(targetIgnoreProperties) : Collections.emptyList();
        for (PropertyDescriptor targetPd : targetPds) {
            Method readMethod;
            String sourceMethodName;
            PropertyDescriptor sourcePd;
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod == null || ignoreList.contains(targetPd.getName()) || (sourcePd = BeanCopyUtils.getSourcePd(targetPd, source, sourceMethodName = (String)map.get(targetPd.getName()))) == null || (readMethod = sourcePd.getReadMethod()) == null) continue;
            try {
                boolean isAssignable;
                Function function;
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(source, new Object[0]);
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                if (Objects.nonNull(function = map.getFunction(targetPd.getName()))) {
                    Object apply = function.apply(value);
                    writeMethod.invoke(target, apply);
                    continue;
                }
                ResolvableType sourceResolvableType = ResolvableType.forMethodReturnType(readMethod);
                ResolvableType targetResolvableType = ResolvableType.forMethodParameter(writeMethod, 0);
                boolean bl = isAssignable = sourceResolvableType.hasUnresolvableGenerics() || targetResolvableType.hasUnresolvableGenerics() ? ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType()) : targetResolvableType.isAssignableFrom(sourceResolvableType);
                if (isAssignable) {
                    writeMethod.invoke(target, value);
                    continue;
                }
                Object typeValue = BeanCopyUtils.getTypeValue(targetResolvableType.getType(), value);
                writeMethod.invoke(target, typeValue);
            }
            catch (Throwable ex) {
                throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
            }
        }
    }

    private static PropertyDescriptor getSourcePd(PropertyDescriptor targetPd, Object source, String sourceMethodName) {
        PropertyDescriptor sourcePd = null;
        if (sourceMethodName != null && (sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), sourceMethodName)) == null) {
            throw new FatalBeanException(source.getClass().getSimpleName() + "\u4e2d\u4e0d\u5b58\u5728" + sourceMethodName + "\u5c5e\u6027");
        }
        if (sourcePd == null) {
            sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
        }
        return sourcePd;
    }

    private static Object getTypeValue(Type type, Object value) {
        if (Integer.TYPE.equals(type)) {
            return Integer.valueOf(String.valueOf(value));
        }
        if (Long.TYPE.equals(type)) {
            return Long.valueOf(String.valueOf(value));
        }
        if (Double.TYPE.equals(type)) {
            return Double.valueOf(String.valueOf(value));
        }
        if (Boolean.TYPE.equals(type)) {
            return Boolean.valueOf(String.valueOf(value));
        }
        if (Float.TYPE.equals(type)) {
            return Float.valueOf(String.valueOf(value));
        }
        if (Short.TYPE.equals(type)) {
            return Short.valueOf(String.valueOf(value));
        }
        if (Byte.TYPE.equals(type)) {
            return Byte.valueOf(String.valueOf(value));
        }
        if (Character.TYPE.equals(type)) {
            return Character.valueOf(String.valueOf(value).charAt(0));
        }
        if (String.class.equals((Object)type)) {
            return String.valueOf(value);
        }
        return null;
    }
}

