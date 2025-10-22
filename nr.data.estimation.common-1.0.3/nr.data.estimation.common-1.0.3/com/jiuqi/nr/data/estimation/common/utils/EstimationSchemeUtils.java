/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.type.CollectionType
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.estimation.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.estimation.common.utils.JSONValidator;
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.rowset.serial.SerialClob;

public class EstimationSchemeUtils {
    private EstimationSchemeUtils() {
    }

    public static DimensionValueSet convert2DimValueSet(Map<String, DimensionValue> dimValueSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry<String, DimensionValue> entry : dimValueSet.entrySet()) {
            dimensionValueSet.setValue(entry.getKey(), (Object)entry.getValue().getValue());
        }
        return dimensionValueSet;
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
                throw new RuntimeException(e);
            }
        }
        return re;
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
        if (EstimationSchemeUtils.isJSON(asText)) {
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
        if (EstimationSchemeUtils.isJSON(asText)) {
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
        if (EstimationSchemeUtils.isJSON(asText)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                TypeFactory typeFactory = mapper.getTypeFactory();
                CollectionType listType = typeFactory.constructCollectionType(List.class, clazz);
                return (List)mapper.readValue(asText, (JavaType)listType);
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
            return JSONValidator.Type.Object.compareTo(validator.getType()) == 0 || JSONValidator.Type.Array.compareTo(validator.getType()) == 0;
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

