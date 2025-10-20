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
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 */
package com.jiuqi.common.base.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jiuqi.common.base.BusinessRuntimeException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonUtils {
    private static final ObjectMapper mapper = JsonUtils.newObjectMapper();
    private static final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    public static ObjectMapper newObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setDateFormat((DateFormat)new SimpleDateFormat(defaultDateFormat));
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigDecimal.class, (JsonSerializer)new JsonSerializer<BigDecimal>(){

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
        module.addSerializer(Double.class, (JsonSerializer)new JsonSerializer<Double>(){

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
        mapper.registerModule((Module)module);
        mapper.registerModule((Module)new JavaTimeModule());
        return mapper;
    }

    public static ObjectMapper newObjectMapperEnumNull() {
        ObjectMapper objectMapper = JsonUtils.newObjectMapper();
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        return objectMapper;
    }

    public static String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e);
        }
    }

    public static <T> T readValue(String json, Class<T> beanType) {
        try {
            return (T)mapper.readValue(json, beanType);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e);
        }
    }

    public static <T> T readValue(String json, TypeReference<T> valueTypeRef) {
        try {
            return (T)mapper.readValue(json, valueTypeRef);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] json, Class<T> beanType) {
        try {
            return (T)mapper.readValue(json, beanType);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] json, TypeReference<T> valueTypeRef) {
        try {
            return (T)mapper.readValue(json, valueTypeRef);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e);
        }
    }

    public static JsonNode readTree(String json) {
        try {
            return mapper.readTree(json);
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(e);
        }
    }
}

