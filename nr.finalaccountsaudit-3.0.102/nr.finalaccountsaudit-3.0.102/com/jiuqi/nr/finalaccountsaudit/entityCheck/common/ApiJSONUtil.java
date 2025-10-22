/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.json.JSONObject
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONObject;

public class ApiJSONUtil {
    public static <T> Object JSONToObj(String jsonStr, Class<T> obj) throws Exception {
        Object t = null;
        ObjectMapper objectMapper = new ObjectMapper();
        t = objectMapper.readValue(jsonStr, obj);
        return t;
    }

    public static <T> JSONObject objectToJson(T obj) throws Exception {
        if (null == obj) {
            return new JSONObject();
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(obj);
        return new JSONObject(jsonStr);
    }

    public static <T> String objectToJsonStr(T obj) throws Exception {
        if (null == obj) {
            return new JSONObject().toString();
        }
        StringWriter str = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue((Writer)str, obj);
        return str.toString();
    }
}

