/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonCreator$Mode
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.formtype.common.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataConverter<T> {
    private String version;
    private T data;

    @JsonCreator(mode=JsonCreator.Mode.PROPERTIES)
    public JsonDataConverter(@JsonProperty(value="data") T data, @JsonProperty(value="version") String version) {
        this.data = data;
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public T getData() {
        return this.data;
    }

    public byte[] serializer() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes((Object)this);
    }

    public byte[] serializer(IJsonModuleRegister moduleRegister) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (null != moduleRegister) {
            moduleRegister.register(objectMapper);
        }
        return objectMapper.writeValueAsBytes((Object)this);
    }

    public static <V> JsonDataConverter<V> deserializer(byte[] byteArray, Class<V> v, IJsonModuleRegister moduleRegister) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (null != moduleRegister) {
            moduleRegister.register(objectMapper);
        }
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(JsonDataConverter.class, new Class[]{v});
        return (JsonDataConverter)objectMapper.readValue(byteArray, valueType);
    }

    public static <V> JsonDataConverter<List<V>> deserializerForList(byte[] byteArray, Class<V> v, IJsonModuleRegister moduleRegister) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (null != moduleRegister) {
            moduleRegister.register(objectMapper);
        }
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{v});
        JavaType constructParametricType = objectMapper.getTypeFactory().constructParametricType(JsonDataConverter.class, new JavaType[]{valueType});
        return (JsonDataConverter)objectMapper.readValue(byteArray, constructParametricType);
    }

    public static <K, V> JsonDataConverter<Map<K, V>> deserializerForMap(byte[] byteArray, Class<K> k, Class<V> v, IJsonModuleRegister moduleRegister) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (null != moduleRegister) {
            moduleRegister.register(objectMapper);
        }
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{k, v});
        JavaType constructParametricType = objectMapper.getTypeFactory().constructParametricType(JsonDataConverter.class, new JavaType[]{valueType});
        return (JsonDataConverter)objectMapper.readValue(byteArray, constructParametricType);
    }

    public String toString() {
        return "JsonDataConverter [version=" + this.version + ", data=" + this.data + "]";
    }

    @FunctionalInterface
    public static interface IJsonModuleRegister {
        public void register(ObjectMapper var1);
    }
}

