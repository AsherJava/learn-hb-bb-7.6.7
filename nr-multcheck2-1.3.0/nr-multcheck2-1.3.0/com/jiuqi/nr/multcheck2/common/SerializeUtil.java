/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.multcheck2.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializeUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serializeToJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T deserializeFromJson(String json, Class<T> clazz) throws Exception {
        return (T)objectMapper.readValue(json, clazz);
    }
}

