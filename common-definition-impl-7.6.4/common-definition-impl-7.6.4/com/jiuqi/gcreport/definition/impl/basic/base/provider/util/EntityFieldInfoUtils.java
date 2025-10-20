/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  javax.persistence.EntityNotFoundException
 */
package com.jiuqi.gcreport.definition.impl.basic.base.provider.util;

import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.util.ReflectionUtils;

public class EntityFieldInfoUtils {
    public static List<PropertyDescriptor> getFields(Class<? extends BaseEntity> entityType) {
        PropertyDescriptor[] fields;
        try {
            DBTable entityTableAnn = entityType.getAnnotation(DBTable.class);
            Class stopClass = entityTableAnn == null ? Object.class : entityTableAnn.stopSuper();
            fields = Introspector.getBeanInfo(entityType, stopClass).getPropertyDescriptors();
        }
        catch (IntrospectionException e) {
            Object[] i18Args = new String[]{entityType.getName()};
            throw new EntityNotFoundException(GcI18nUtil.getMessage((String)"ent.definition.entity.property.load.error", (Object[])i18Args));
        }
        return Arrays.stream(fields).filter(propertyDescriptor -> EntityFieldInfoUtils.isNotStop(entityType, propertyDescriptor)).collect(Collectors.toList());
    }

    private static boolean isNotStop(Class<? extends BaseEntity> entityType, PropertyDescriptor propertyDescriptor) {
        DBTable entityTableAnn = entityType.getAnnotation(DBTable.class);
        Class stopClass = entityTableAnn == null ? Object.class : entityTableAnn.stopSuper();
        Field field = EntityFieldInfoUtils.getField(entityType, propertyDescriptor, stopClass);
        return field != null;
    }

    private static Field getField(Class<?> entityType, PropertyDescriptor pd, Class<?> stopClass) {
        Field f = ReflectionUtils.findField(entityType, pd.getName());
        if (f == null || f.getDeclaringClass().isAssignableFrom(stopClass)) {
            return null;
        }
        return f;
    }
}

