/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.json.JsonMapper
 *  com.fasterxml.jackson.databind.json.JsonMapper$Builder
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 */
package com.jiuqi.gcreport.oauth2.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.boot.json.JsonParseException;

public class JsonUtils {
    private static final ObjectMapper mapper = JsonUtils.newJsonMapper();
    private static final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    public static JsonMapper newJsonMapper() {
        SimpleModule customNumberModule = new SimpleModule();
        customNumberModule.addSerializer(BigDecimal.class, (JsonSerializer)new JsonSerializer<BigDecimal>(){

            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    String valStr = value.toPlainString();
                    if (valStr.length() > 16) {
                        gen.writeString(valStr);
                    } else {
                        gen.writeNumber(value);
                    }
                }
            }
        });
        customNumberModule.addSerializer(Double.class, (JsonSerializer)new JsonSerializer<Double>(){

            public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    String valStr = value.toString();
                    if (valStr.contains("E")) {
                        gen.writeString(new BigDecimal(valStr).toPlainString());
                    } else {
                        gen.writeNumber(value.doubleValue());
                    }
                }
            }
        });
        JsonMapper jsonMapper = (JsonMapper)((JsonMapper.Builder)((JsonMapper.Builder)((JsonMapper.Builder)((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().enable(new MapperFeature[]{MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES})).disable(new DeserializationFeature[]{DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES})).defaultDateFormat((DateFormat)new SimpleDateFormat(defaultDateFormat))).addModule((Module)customNumberModule)).addModule((Module)new JavaTimeModule())).build();
        return jsonMapper;
    }

    public static String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public static <T> T readValue(String json, Class<T> beanType) {
        try {
            return (T)mapper.readValue(json, beanType);
        }
        catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public static <T> T readValue(String json, TypeReference<T> valueTypeRef) {
        try {
            return (T)mapper.readValue(json, valueTypeRef);
        }
        catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public static <T> T readValue(byte[] bytes, Class<T> beanType) {
        try {
            return (T)mapper.readValue(bytes, beanType);
        }
        catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public static <T> T readValue(byte[] bytes, TypeReference<T> valueTypeRef) {
        try {
            return (T)mapper.readValue(bytes, valueTypeRef);
        }
        catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public static JsonNode readTree(String json) {
        try {
            return mapper.readTree(json);
        }
        catch (IOException e) {
            throw new JsonParseException(e);
        }
    }
}

