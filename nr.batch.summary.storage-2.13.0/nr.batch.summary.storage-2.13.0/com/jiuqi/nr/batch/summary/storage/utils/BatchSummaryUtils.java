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
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.batch.summary.storage.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.batch.summary.storage.utils.JSONValidator;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.rowset.serial.SerialClob;

public class BatchSummaryUtils {
    private BatchSummaryUtils() {
    }

    public static Clob stringToClob(String text) {
        try {
            return new SerialClob(text.toCharArray());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJSONStr(Object obj) {
        if (obj != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(obj);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T toJavaBean(String asText, Class<T> clazz) {
        if (BatchSummaryUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return (T)mapper.readValue(asText, clazz);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T toJavaBean(String asText, Class<T> clazz, JsonDeserializer<? extends T> deser) {
        if (BatchSummaryUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addDeserializer(clazz, deser);
                mapper.registerModule((Module)simpleModule);
                return (T)mapper.readValue(asText, clazz);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> List<T> toJavaArray(String asText, Class<T> clazz) {
        if (BatchSummaryUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                TypeReference typeReference = new TypeReference<List<T>>(){};
                return (List)mapper.readValue(asText, typeReference);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
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

    public static String getCurrentUserID() {
        return NpContextHolder.getContext().getUserId();
    }

    public static String getCopySchemeCode(String oriCode) {
        String regex = "^[a-zA-Z0-9_]+_FB_([0-9]|[0-9]\\d|100)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oriCode.trim());
        if (matcher.matches()) {
            String number = matcher.group(1);
            String newNumber = String.format("%02d", Integer.parseInt(number) + 1);
            return oriCode.substring(0, oriCode.length() - 2) + newNumber;
        }
        return oriCode + "_FB_01";
    }

    public static String getCopySchemeTitle(String oriTitle) {
        String regex = ".*_\u526f\u672c_(\\d{2})$";
        Pattern pattern = Pattern.compile(regex, 256);
        Matcher matcher = pattern.matcher(oriTitle.trim());
        if (matcher.matches()) {
            String number = matcher.group(1);
            String newNumber = String.format("%02d", Integer.parseInt(number) + 1);
            return oriTitle.substring(0, oriTitle.length() - 2) + newNumber;
        }
        return oriTitle + "_\u526f\u672c_01";
    }
}

