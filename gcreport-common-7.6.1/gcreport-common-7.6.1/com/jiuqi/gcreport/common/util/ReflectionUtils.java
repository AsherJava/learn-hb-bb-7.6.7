/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.ReflectionUtils;

public class ReflectionUtils
extends org.springframework.util.ReflectionUtils {
    private static final Map<Class<?>, Map<String, Field>> fieldCache = new ConcurrentHashMap();
    private static final Map<Class<?>, Map<String, Method>> methodCache = new ConcurrentHashMap();

    public static Field[] getAllDeclareFields(Class clazz) {
        Field[] fields;
        if (clazz.getSuperclass() == Object.class) {
            return clazz.getDeclaredFields();
        }
        HashSet names = new HashSet();
        ArrayList<Field> list = new ArrayList<Field>();
        for (Class searchType = clazz; !Object.class.equals((Object)searchType) && searchType != null; searchType = searchType.getSuperclass()) {
            for (Field field : fields = searchType.getDeclaredFields()) {
                if (names.contains(field.getName())) continue;
                list.add(field);
            }
        }
        fields = new Field[list.size()];
        return list.toArray(fields);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (Character.isWhitespace(cs.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static Object getFieldValue(Object target, String fieldName) {
        Field field;
        if (target == null) {
            return null;
        }
        if (ReflectionUtils.isBlank(fieldName)) {
            return null;
        }
        if (target instanceof Map) {
            return ((Map)target).get(fieldName);
        }
        Class<?> clazz = null;
        clazz = target instanceof Class ? (Class<?>)target : target.getClass();
        Map<String, Field> fieldMap = fieldCache.get(clazz);
        if (!(fieldMap != null && fieldMap.containsKey(fieldName) || (fieldMap = fieldCache.get(clazz)) != null && fieldMap.containsKey(fieldName) || (field = ReflectionUtils.findField(clazz, fieldName)) == null)) {
            field.setAccessible(true);
            if (fieldMap == null) {
                fieldMap = new ConcurrentHashMap<String, Field>();
                fieldCache.put(clazz, fieldMap);
            }
            fieldMap.put(fieldName, field);
        }
        if (fieldMap == null || !fieldMap.containsKey(fieldName)) {
            Object[] i18Args = new String[]{clazz.getCanonicalName(), fieldName};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.field.notexists", (Object[])i18Args);
            throw new BusinessRuntimeException(message);
        }
        Field filed = fieldMap.get(fieldName);
        try {
            return filed.get(target);
        }
        catch (Exception e) {
            Object[] i18Args = new String[]{clazz.getCanonicalName(), fieldName};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.field.notexists", (Object[])i18Args);
            throw new BusinessRuntimeException(message, (Throwable)e);
        }
    }

    public static void setFieldValue(Object target, String fieldName, Object fieldValue) {
        Field field;
        if (target == null || ReflectionUtils.isBlank(fieldName)) {
            return;
        }
        if (target instanceof Map) {
            ((Map)target).put(fieldName, fieldValue);
            return;
        }
        Class<?> clazz = null;
        clazz = target instanceof Class ? (Class<?>)target : target.getClass();
        Map<String, Field> filedMap = fieldCache.get(clazz);
        if (!(filedMap != null && filedMap.containsKey(fieldName) || (filedMap = fieldCache.get(clazz)) != null && filedMap.containsKey(fieldName) || (field = ReflectionUtils.findField(clazz, fieldName)) == null)) {
            field.setAccessible(true);
            if (filedMap == null) {
                filedMap = new ConcurrentHashMap<String, Field>();
                fieldCache.put(clazz, filedMap);
            }
            filedMap.put(fieldName, field);
        }
        if (filedMap == null || !filedMap.containsKey(fieldName)) {
            Object[] i18Args = new String[]{clazz.getCanonicalName(), fieldName};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.field.notexists", (Object[])i18Args);
            throw new BusinessRuntimeException(message);
        }
        Field filed = filedMap.get(fieldName);
        try {
            filed.set(target, fieldValue);
            if (target instanceof AbstractFieldDynamicDeclarator) {
                ((AbstractFieldDynamicDeclarator)target).addFieldValue(fieldName.toUpperCase(), fieldValue);
            }
        }
        catch (Exception e) {
            Object[] i18Args = new String[]{clazz.getCanonicalName(), fieldName};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.field.notaccess", (Object[])i18Args);
            throw new BusinessRuntimeException(message, (Throwable)e);
        }
    }

    public static Object invokeStaticMethod(Class<?> clazz, String methodName, Object ... objects) {
        return ReflectionUtils.invokeMethod(clazz, methodName, objects);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object invokeMethod(Object target, String methodName, Object[] objects) {
        if (target == null || ReflectionUtils.isBlank(methodName)) {
            return null;
        }
        Class<?> clazz = null;
        clazz = target instanceof Class ? (Class<?>)target : target.getClass();
        Map<String, Method> methodMap = methodCache.get(clazz);
        if (methodMap == null || !methodMap.containsKey(methodName)) {
            Class<?> clazz2 = clazz;
            synchronized (clazz2) {
                Method method;
                methodMap = methodCache.get(clazz);
                if (!(methodMap != null && methodMap.containsKey(methodName) || (method = ReflectionUtils.findMethod(clazz, methodName, new Class[0])) == null)) {
                    method.setAccessible(true);
                    if (methodMap == null) {
                        methodMap = new ConcurrentHashMap<String, Method>();
                        methodCache.put(clazz, methodMap);
                    }
                    methodMap.put(methodName, method);
                }
            }
        }
        if (methodMap == null || !methodMap.containsKey(methodName)) {
            Object[] i18Args = new String[]{clazz.getCanonicalName(), methodName};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.method.notexists", (Object[])i18Args);
            throw new BusinessRuntimeException(message);
        }
        Method method = methodMap.get(methodName);
        try {
            return method.invoke(target, objects);
        }
        catch (Exception e) {
            Object[] i18Args = new String[]{clazz.getCanonicalName(), methodName};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.method.notaccess", (Object[])i18Args);
            throw new BusinessRuntimeException(message);
        }
    }

    public static <A extends Annotation> Field findUniqueFieldWithAnnotation(Class<?> clz, final Class<A> type) {
        final ArrayList fields = new ArrayList();
        ReflectionUtils.doWithFields(clz, new ReflectionUtils.FieldCallback(){

            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                fields.add(field);
            }
        }, new ReflectionUtils.FieldFilter(){

            @Override
            public boolean matches(Field field) {
                return field.isAnnotationPresent(type);
            }
        });
        if (fields.size() > 1) {
            Object[] i18Args = new String[]{clz.getCanonicalName(), type.getSimpleName()};
            String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.anno.notunique", (Object[])i18Args);
            throw new IllegalStateException(message);
        }
        if (fields.size() == 1) {
            return (Field)fields.get(0);
        }
        return null;
    }

    public static void doWithDeclaredFields(Class<?> clazz, ReflectionUtils.FieldCallback fc, ReflectionUtils.FieldFilter ff) throws IllegalArgumentException {
        Field[] fields;
        if (clazz == null || clazz == Object.class) {
            return;
        }
        for (Field field : fields = clazz.getDeclaredFields()) {
            if (ff != null && !ff.matches(field)) continue;
            try {
                fc.doWith(field);
            }
            catch (IllegalAccessException ex) {
                Object[] i18Args = new String[]{clazz.getCanonicalName(), field.getName()};
                String message = GcI18nUtil.getMessage((String)"gc.common.util.reflect.field.illegalaccess", (Object[])i18Args);
                throw new IllegalStateException(message, ex);
            }
        }
    }

    public static Field getFirstDeclaredFieldWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(annotationClass)) continue;
            return field;
        }
        return null;
    }

    public static Field[] getDeclaredFieldsWith(Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        final ArrayList fields = new ArrayList();
        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback(){

            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (field.isAnnotationPresent(annotationClass)) {
                    fields.add(field);
                }
            }
        });
        return fields.toArray(new Field[0]);
    }

    public static Method getFirstDeclaredMethodWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            return method;
        }
        return null;
    }

    public static Method[] getDeclaredMethodsWith(Class<?> clazz, Class<? extends Annotation> annotionClass) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(annotionClass)) continue;
            methods.add(method);
        }
        return methods.toArray(new Method[0]);
    }

    public static Method[] getDecllaredGetMethodsWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annotationClass) == null || method.getReturnType().equals(Void.TYPE) || method.getParameterTypes().length > 0) continue;
            methods.add(method);
        }
        return methods.toArray(new Method[0]);
    }
}

