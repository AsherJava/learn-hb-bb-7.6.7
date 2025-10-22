/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 */
package com.jiuqi.nr.period.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JacksonUtils {
    public static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private JacksonUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String objectToJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(value);
        }
        catch (JsonProcessingException e) {
            logger.info("parse class [\"{}\"] to json error:\n{{}}", (Object)value.getClass().getSimpleName(), (Object)e.getMessage());
            return null;
        }
    }

    public static <T> T jsonToObject(String value, Class<T> clazz) {
        if (value == null) {
            return null;
        }
        try {
            return (T)mapper.readValue(value, clazz);
        }
        catch (JsonProcessingException e) {
            logger.info("parse json [\"{}\"] to class [{}] error:\n[{}]", value, clazz.getSimpleName(), e.getMessage());
            return null;
        }
    }

    public static <T> T jsonToObject(String content, JavaType javaType) {
        if (content == null) {
            return null;
        }
        try {
            return (T)mapper.readValue(content, javaType);
        }
        catch (JsonProcessingException e) {
            logger.info("parse json [\"{}\"] to [{}] error:\n[{}]", content, javaType.getGenericSignature(), e.getMessage());
            return null;
        }
    }

    public static <T> T convertValue(String content, TypeReference<T> typeReference) {
        if (content == null) {
            return null;
        }
        try {
            JsonNode jsonNode = mapper.readTree(content);
            return (T)mapper.convertValue((Object)jsonNode, typeReference);
        }
        catch (JsonProcessingException e) {
            logger.info("parse json [\"{}\"] to json node error:\n[{}]", (Object)content, (Object)e.getMessage());
            return null;
        }
    }

    public static <T> T jsonToObject(String content, TypeReference<T> reference) {
        if (content == null) {
            return null;
        }
        try {
            return (T)mapper.readValue(content, reference);
        }
        catch (JsonProcessingException e) {
            logger.info("parse json [\"{}\"] to [{}] error:\n[{}]", content, reference.getType().getTypeName(), e.getMessage());
            return null;
        }
    }

    public static JavaType constructType(Class<?> type) {
        return mapper.constructType(type);
    }

    public static JavaType constructJavaType(Class<?> parametrized, Class<?> ... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, (Class[])parameterClasses);
    }

    public static JavaType constructJavaType(Class<?> rawType, JavaType ... parameterTypes) {
        return mapper.getTypeFactory().constructParametricType(rawType, parameterTypes);
    }

    public static JavaType constructMapType(JavaType keyType, JavaType valueType) {
        return mapper.getTypeFactory().constructMapType(HashMap.class, keyType, valueType);
    }

    public static JavaType constructMapType(Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
    }

    public static <T> List<T> toList(String value, Class<T> clazz) {
        return (List)JacksonUtils.jsonToObject(value, JacksonUtils.constructJavaType(List.class, clazz));
    }

    public static <V> Map<String, V> toMap(String content, Class<V> vClass) {
        return JacksonUtils.toMap(content, String.class, vClass);
    }

    public static <K, V> Map<K, V> toMap(String content, Class<K> kClass, Class<V> vClass) {
        return (Map)JacksonUtils.jsonToObject(content, JacksonUtils.constructJavaType(Map.class, kClass, vClass));
    }

    @Deprecated
    public static <E extends Enum<E>> String enumToJson(E e) {
        Field[] declaredFields;
        if (e == null) {
            return null;
        }
        if (e.getClass().getSuperclass() != Enum.class) {
            return null;
        }
        ObjectNode objectNode = mapper.createObjectNode();
        for (Field declaredField : declaredFields = e.getDeclaringClass().getDeclaredFields()) {
            Class<?> type = declaredField.getType();
            if (type == e.getClass() || type.isArray()) continue;
            try {
                String methodName = "get" + JacksonUtils.capitalize(declaredField.getName());
                Method declaredMethod = e.getDeclaringClass().getDeclaredMethod(methodName, new Class[0]);
                objectNode.putPOJO(methodName, declaredMethod.invoke(e, new Object[0]));
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                logger.error("parse enum [{}] to json error\uff1a{}", (Object)e.getClass().getSimpleName(), (Object)ex.getMessage());
                return null;
            }
        }
        objectNode.put("ordinal", e.ordinal());
        return objectNode.toString();
    }

    private static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
    }

    @Deprecated
    public static <E> E jsonToEnum(String content, Class<E> eClass) {
        if (content == null) {
            return null;
        }
        try {
            E[] values = eClass.getEnumConstants();
            JsonNode jsonNode = mapper.readTree(content);
            int ordinal = jsonNode.get("ordinal").asInt();
            return values[ordinal];
        }
        catch (JsonProcessingException e) {
            logger.info("parse json [{}] to enum [{}] error\uff1a{}", content, eClass.getSimpleName(), e.getMessage());
            return null;
        }
    }

    static {
        mapper.setDateFormat((DateFormat)new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}

