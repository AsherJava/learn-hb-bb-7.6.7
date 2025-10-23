/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator$Feature
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger;

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        }
        catch (Exception e) {
            logger.error("json error", e);
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return (T)mapper.readValue(json, clazz);
        }
        catch (Exception e) {
            logger.error("json error", e);
            return null;
        }
    }

    public static <T> T fromJson(String content, TypeReference<T> reference) {
        try {
            return (T)mapper.readValue(content, reference);
        }
        catch (Exception e) {
            logger.error("json error", e);
            return null;
        }
    }

    static {
        mapper.registerModule((Module)new JavaTimeModule());
        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, false);
        logger = LoggerFactory.getLogger(JsonUtils.class);
    }
}

