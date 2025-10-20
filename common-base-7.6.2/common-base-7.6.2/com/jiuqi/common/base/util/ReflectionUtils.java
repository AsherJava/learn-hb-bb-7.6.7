/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.BusinessRuntimeException;
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
            throw new BusinessRuntimeException(String.format("\u7c7b [%s] \u4e0d\u5b58\u5728\u5c5e\u6027 [%s]", clazz.getCanonicalName(), fieldName));
        }
        Field filed = fieldMap.get(fieldName);
        try {
            return filed.get(target);
        }
        catch (Exception e) {
            String message = String.format("\u7c7b [%s] \u53cd\u5c04\u8bbf\u95ee\u5c5e\u6027 [%s] \u5f02\u5e38!", clazz.getCanonicalName(), fieldName);
            throw new BusinessRuntimeException(message, e);
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
            throw new BusinessRuntimeException(String.format("\u7c7b [%s] \u4e0d\u5b58\u5728\u5c5e\u6027 [%s]", clazz.getCanonicalName(), fieldName));
        }
        Field filed = filedMap.get(fieldName);
        try {
            filed.set(target, fieldValue);
        }
        catch (Exception e) {
            String message = String.format("\u7c7b [%s] \u53cd\u5c04\u8bbf\u95ee\u5c5e\u6027 [%s] \u5f02\u5e38!", clazz.getCanonicalName(), fieldName);
            throw new BusinessRuntimeException(message, e);
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
            throw new BusinessRuntimeException(String.format("\u7c7b [%s] \u4e0d\u5b58\u5728\u65b9\u6cd5 [%s]", clazz.getCanonicalName(), methodName));
        }
        Method method = methodMap.get(methodName);
        try {
            return method.invoke(target, objects);
        }
        catch (Exception e) {
            String message = String.format("\u7c7b [%s] \u53cd\u5c04\u8bbf\u95ee\u65b9\u6cd5 [%s] \u5f02\u5e38!", clazz.getCanonicalName(), methodName);
            throw new BusinessRuntimeException(message, e);
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
            throw new IllegalStateException("\u88ab\u6ce8\u91ca" + type.getSimpleName() + "\u58f0\u660e\u7684\u57df\u4e0d\u552f\u4e00");
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
                throw new IllegalStateException("\u975e\u6cd5\u8bbf\u95ee\u5c5e\u6027 '" + field.getName() + "': " + ex);
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

