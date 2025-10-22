/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.definition.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SerializeUtils {
    public static byte[] jsonSerializeToByte(Object obj) throws JsonProcessingException {
        if (null == obj) {
            return new byte[0];
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(obj);
    }

    public static String jsonSerializeToString(Object obj) throws JsonProcessingException {
        if (null == obj) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T jsonDeserialize(String data, Class<T> t) throws IOException {
        if (null == data) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T)objectMapper.readValue(data, t);
    }

    public static <T> T jsonDeserialize(byte[] byteArray, Class<T> t) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T)objectMapper.readValue(byteArray, t);
    }

    public static <T> T jsonDeserialize(Module module, byte[] byteArray, Class<T> t) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(module);
        return (T)objectMapper.readValue(byteArray, t);
    }

    public static <T> List<T> jsonDeserializeToArray(byte[] byteArray, Class<T> t) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{t});
        return (List)objectMapper.readValue(byteArray, valueType);
    }

    public static <K, V> Map<K, V> jsonDeserializeToMap(byte[] byteArray, Class<K> k, Class<V> v) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{k, v});
        return (Map)objectMapper.readValue(byteArray, valueType);
    }
}

