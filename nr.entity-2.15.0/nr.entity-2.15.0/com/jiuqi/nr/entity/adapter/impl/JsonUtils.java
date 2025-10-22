/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser$Feature
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 */
package com.jiuqi.nr.entity.adapter.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String json, Class<T> beanType) {
        try {
            return (T)mapper.readValue(json, beanType);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String json, TypeReference<T> valueTypeRef) {
        try {
            return (T)mapper.readValue(json, valueTypeRef);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] json, Class<T> beanType) {
        try {
            return (T)mapper.readValue(json, beanType);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] json, TypeReference<T> valueTypeRef) {
        try {
            return (T)mapper.readValue(json, valueTypeRef);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readTree(String json) {
        try {
            return mapper.readTree(json);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }
}

