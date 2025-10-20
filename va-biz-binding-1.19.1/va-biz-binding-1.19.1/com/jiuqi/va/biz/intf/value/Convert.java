/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import com.jiuqi.va.biz.intf.value.ConvertException;
import com.jiuqi.va.biz.intf.value.ConvertManager;
import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.intf.value.EnumValue;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class Convert {
    public static <T> T cast(Object source, Class<T> targetClass) {
        Converter converter;
        if (source == null) {
            if (targetClass.isPrimitive()) {
                return (T)ConvertManager.INSTANCE.primitiveNullMap.get(targetClass);
            }
            return null;
        }
        if (targetClass.isInstance(source)) {
            return (T)source;
        }
        Class<?> sourceType = source.getClass();
        Class<Object> targetType = targetClass;
        if (targetType.isPrimitive()) {
            targetType = ConvertManager.INSTANCE.primitiveTypeMap.get(targetType);
        }
        if (sourceType == targetType) {
            return (T)source;
        }
        Map<Class<?>, Converter> map = ConvertManager.INSTANCE.map.get(sourceType);
        if (map == null) {
            if (sourceType.isEnum() || sourceType.getSuperclass() != null && sourceType.getSuperclass().isEnum()) {
                map = ConvertManager.INSTANCE.map.get(Enum.class);
            }
            if (map == null) {
                throw new ConvertException(BizBindingI18nUtil.getMessage("va.bizbinding.convert.unregisteredsourcetype") + sourceType);
            }
        }
        if ((converter = map.get(targetType)) == null) {
            if ((targetType.isEnum() || targetType.getSuperclass() != null && targetType.getSuperclass().isEnum()) && (converter = map.get(Enum.class)) != null) {
                Object target = converter.cast(source);
                if (target instanceof String) {
                    return Enum.valueOf(targetClass, (String)target);
                }
                if (target instanceof Integer) {
                    if (EnumValue.class.isAssignableFrom(targetClass)) {
                        Optional<Object> findFirst = Stream.of(targetClass.getEnumConstants()).filter(o -> ((EnumValue)o).getValue() == ((Integer)target).intValue()).findFirst();
                        return (T)(findFirst.isPresent() ? findFirst.get() : targetClass.getEnumConstants()[(Integer)target]);
                    }
                    return targetClass.getEnumConstants()[(Integer)target];
                }
                throw new ConvertException(BizBindingI18nUtil.getMessage("va.bizbinding.convert.enumconvertmustintorstring") + converter.getClass());
            }
            if (converter == null) {
                throw new ConvertException(BizBindingI18nUtil.getMessage("va.bizbinding.convert.unregisteredconverter", new Object[]{sourceType, targetType}));
            }
        }
        return (T)converter.cast(source);
    }
}

