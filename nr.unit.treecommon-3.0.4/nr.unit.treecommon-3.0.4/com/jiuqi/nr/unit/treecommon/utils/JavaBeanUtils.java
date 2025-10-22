/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treecommon.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.unit.treecommon.utils.JSONValidator;
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.sql.rowset.serial.SerialClob;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class JavaBeanUtils {
    private JavaBeanUtils() {
    }

    public static JSONObject toJSONObject(String asText) {
        if (JavaBeanUtils.isJSON(asText)) {
            return new JSONObject(asText);
        }
        return null;
    }

    public static JSONObject toJSONObject(Object obj) {
        String jsonStr = JavaBeanUtils.toJSONStr(obj);
        return JavaBeanUtils.toJSONObject(jsonStr);
    }

    public static <T> T toJavaBean(JSONObject jsonObject, Class<T> clazz) {
        if (jsonObject != null) {
            return JavaBeanUtils.toJavaBean(jsonObject.toString(), clazz);
        }
        return null;
    }

    public static JSONArray toJSONArray(String asText) {
        if (JavaBeanUtils.isJSON(asText)) {
            return new JSONArray(asText);
        }
        return null;
    }

    public static String clob2String(Clob clob) {
        String re = "";
        if (clob != null) {
            try (Reader reader = clob.getCharacterStream();
                 BufferedReader br = new BufferedReader(reader);){
                String str = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (str != null) {
                    sb.append(str);
                    str = br.readLine();
                }
                re = sb.toString();
            }
            catch (Exception e) {
                LoggerFactory.getLogger(JavaBeanUtils.class).error(e.getMessage(), e.getCause());
            }
        }
        return re;
    }

    public static Clob stringToClob(String text) {
        try {
            return new SerialClob(text.toCharArray());
        }
        catch (SQLException e) {
            LoggerFactory.getLogger(JavaBeanUtils.class).error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public static String toJSONStr(Object obj) {
        if (obj != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(obj);
            }
            catch (JsonProcessingException e) {
                LoggerFactory.getLogger(JavaBeanUtils.class).error(e.getMessage(), e.getCause());
            }
        }
        return null;
    }

    public static <T> T toJavaBean(JSONObject jsonObject, String attrKey, Class<T> clazz) {
        if (null != jsonObject && jsonObject.has(attrKey) && !jsonObject.isNull(attrKey)) {
            JSONObject attrJson = jsonObject.getJSONObject(attrKey);
            return JavaBeanUtils.toJavaBean(attrJson, clazz);
        }
        return null;
    }

    public static <T> T toJavaBean(String asText, Class<T> clazz) {
        if (JavaBeanUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return (T)mapper.readValue(asText, clazz);
            }
            catch (JsonProcessingException e) {
                LoggerFactory.getLogger(JavaBeanUtils.class).error(e.getMessage(), e.getCause());
            }
        }
        return null;
    }

    public static <T> T toJavaBean(String asText, Class<T> clazz, JsonDeserializer<? extends T> deser) {
        if (JavaBeanUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addDeserializer(clazz, deser);
                mapper.registerModule((Module)simpleModule);
                return (T)mapper.readValue(asText, clazz);
            }
            catch (JsonProcessingException e) {
                LoggerFactory.getLogger(JavaBeanUtils.class).error(e.getMessage(), e.getCause());
            }
        }
        return null;
    }

    public static <T> List<T> toJavaArray(String asText, Class<T> clazz) {
        if (JavaBeanUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                TypeReference typeReference = new TypeReference<List<T>>(){};
                return (List)mapper.readValue(asText, typeReference);
            }
            catch (JsonProcessingException e) {
                LoggerFactory.getLogger(JavaBeanUtils.class).error(e.getMessage(), e.getCause());
            }
        }
        return null;
    }

    public static boolean isJSON(String json) {
        JSONValidator validator;
        if (StringUtils.isNotEmpty((String)json) && (validator = JSONValidator.from(json)).validate()) {
            return JSONValidator.Type.OBJECT.compareTo(validator.getType()) == 0 || JSONValidator.Type.ARRAY.compareTo(validator.getType()) == 0;
        }
        return false;
    }

    @SafeVarargs
    public static <T> T[] concatArrays(T[] first, T[] ... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    @SafeVarargs
    public static <T> T[] appendElement(T[] arr, T ... els) {
        return JavaBeanUtils.concatArrays(arr, new Object[][]{els});
    }
}

