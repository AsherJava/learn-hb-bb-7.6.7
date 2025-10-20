/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser$Feature
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.fasterxml.jackson.databind.type.CollectionType
 *  com.fasterxml.jackson.databind.type.MapType
 *  com.fasterxml.jackson.databind.type.TypeFactory
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.msgpack.jackson.dataformat.MessagePackMapper
 */
package com.jiuqi.va.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jiuqi.va.domain.common.AesCipherUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import org.msgpack.jackson.dataformat.MessagePackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

public class JSONUtil {
    private static Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    private static final String MSGPACK = "#msgpack";
    private static final String DEFAULT_DATE_FORMAT = "long";
    private static ConcurrentHashMap<String, DateTimeFormatter> dateFormatMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ObjectMapper> mapperMap = new ConcurrentHashMap();
    private static Environment environment = null;

    public static String toJSONString(Object value) {
        return JSONUtil.toJSONString(value, DEFAULT_DATE_FORMAT, false);
    }

    public static String toJSONString(Object value, boolean prettyFormat) {
        return JSONUtil.toJSONString(value, DEFAULT_DATE_FORMAT, prettyFormat);
    }

    public static String toJSONString(Object value, String dataFormatString) {
        return JSONUtil.toJSONString(value, dataFormatString, false);
    }

    public static String toJSONString(Object value, String dataFormatString, boolean prettyFormat) {
        return JSONUtil.toJSONString(value, dataFormatString, prettyFormat, JsonInclude.Include.NON_NULL);
    }

    public static String toJSONString(Object value, JsonInclude.Include include) {
        return JSONUtil.toJSONString(value, DEFAULT_DATE_FORMAT, false, include);
    }

    public static String toJSONString(Object value, String dataFormatString, JsonInclude.Include include) {
        return JSONUtil.toJSONString(value, dataFormatString, false, include);
    }

    public static String toJSONString(Object value, String dataFormatString, boolean prettyFormat, JsonInclude.Include include) {
        if (value == null) {
            return null;
        }
        if (!StringUtils.hasText(dataFormatString)) {
            dataFormatString = DEFAULT_DATE_FORMAT;
        }
        ObjectMapper objectMapper = JSONUtil.getObjectMapper(dataFormatString, prettyFormat, include);
        try {
            return objectMapper.writeValueAsString(value);
        }
        catch (JsonProcessingException e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public static byte[] toBytes(Object value) {
        return JSONUtil.toBytes(value, JsonInclude.Include.NON_NULL);
    }

    public static byte[] toBytes(Object value, JsonInclude.Include include) {
        try {
            ObjectMapper objectMapper = JSONUtil.getMessagePackMapper(include);
            return AesCipherUtil.exchange(objectMapper.writeValueAsBytes(value));
        }
        catch (JsonProcessingException e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public static ObjectNode parseObject(String content) {
        return JSONUtil.parseObject(content, ObjectNode.class);
    }

    public static ObjectNode parseObject(byte[] content) {
        return JSONUtil.parseObject(content, ObjectNode.class);
    }

    public static <T> T parseObject(String content, Class<T> valueType) {
        return JSONUtil.parseObject(JSONUtil.getObjectMapper(), (Object)content, valueType);
    }

    public static <T> T parseObject(byte[] content, Class<T> valueType) {
        return JSONUtil.parseObject(JSONUtil.getMessagePackMapper(), (Object)content, valueType);
    }

    private static <T> T parseObject(ObjectMapper objectMapper, Object content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            if (content instanceof String) {
                return (T)objectMapper.readValue((String)content, valueType);
            }
            return (T)objectMapper.readValue(AesCipherUtil.exchange((byte[])content), valueType);
        }
        catch (Exception e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public static <T> T parseObject(String content, TypeReference<T> valueTypeRef) {
        return JSONUtil.parseObject(JSONUtil.getObjectMapper(), (Object)content, valueTypeRef);
    }

    public static <T> T parseObject(byte[] content, TypeReference<T> valueTypeRef) {
        return JSONUtil.parseObject(JSONUtil.getMessagePackMapper(), (Object)content, valueTypeRef);
    }

    private static <T> T parseObject(ObjectMapper objectMapper, Object content, TypeReference<T> valueTypeRef) {
        if (content == null) {
            return null;
        }
        try {
            if (content instanceof String) {
                return (T)objectMapper.readValue((String)content, valueTypeRef);
            }
            return (T)objectMapper.readValue(AesCipherUtil.exchange((byte[])content), valueTypeRef);
        }
        catch (Exception e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public static ArrayNode parseArray(String content) {
        return JSONUtil.parseObject(content, ArrayNode.class);
    }

    public static ArrayNode parseArray(byte[] content) {
        return JSONUtil.parseObject(content, ArrayNode.class);
    }

    public static <T> List<T> parseArray(String content, Class<T> valueType) {
        return JSONUtil.parseArray(JSONUtil.getObjectMapper(), content, valueType);
    }

    public static <T> List<T> parseArray(byte[] content, Class<T> valueType) {
        return JSONUtil.parseArray(JSONUtil.getMessagePackMapper(), content, valueType);
    }

    private static <T> List<T> parseArray(ObjectMapper objectMapper, Object content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, valueType);
            if (content instanceof String) {
                return (List)objectMapper.readValue((String)content, (JavaType)javaType);
            }
            return (List)objectMapper.readValue(AesCipherUtil.exchange((byte[])content), (JavaType)javaType);
        }
        catch (Exception e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public static Map<String, Object> parseMap(String content) {
        return JSONUtil.parseMap(content, String.class, Object.class);
    }

    public static Map<String, Object> parseMap(byte[] content) {
        return JSONUtil.parseMap(content, String.class, Object.class);
    }

    public static <K, V> Map<K, V> parseMap(String content, Class<K> keyType, Class<V> valueType) {
        return JSONUtil.parseMap(JSONUtil.getObjectMapper(), content, keyType, valueType);
    }

    public static <K, V> Map<K, V> parseMap(byte[] content, Class<K> keyType, Class<V> valueType) {
        return JSONUtil.parseMap(JSONUtil.getMessagePackMapper(), content, keyType, valueType);
    }

    private static <K, V> Map<K, V> parseMap(ObjectMapper objectMapper, Object content, Class<K> keyType, Class<V> valueType) {
        if (content == null) {
            return null;
        }
        try {
            MapType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
            if (content instanceof String) {
                return (Map)objectMapper.readValue((String)content, (JavaType)javaType);
            }
            return (Map)objectMapper.readValue(AesCipherUtil.exchange((byte[])content), (JavaType)javaType);
        }
        catch (Exception e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    public static List<Map<String, Object>> parseMapArray(String content) {
        return JSONUtil.parseMapArray(content, String.class, Object.class);
    }

    public static List<Map<String, Object>> parseMapArray(byte[] content) {
        return JSONUtil.parseMapArray(content, String.class, Object.class);
    }

    public static <K, V> List<Map<K, V>> parseMapArray(String content, Class<K> keyType, Class<V> valueType) {
        return JSONUtil.parseMapArray(JSONUtil.getObjectMapper(), content, keyType, valueType);
    }

    public static <K, V> List<Map<K, V>> parseMapArray(byte[] content, Class<K> keyType, Class<V> valueType) {
        return JSONUtil.parseMapArray(JSONUtil.getMessagePackMapper(), content, keyType, valueType);
    }

    private static <K, V> List<Map<K, V>> parseMapArray(ObjectMapper objectMapper, Object content, Class<K> keyType, Class<V> valueType) {
        if (content == null) {
            return null;
        }
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            MapType mapType = typeFactory.constructMapType(Map.class, keyType, valueType);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, (JavaType)mapType);
            if (content instanceof String) {
                return (List)objectMapper.readValue((String)content, (JavaType)collectionType);
            }
            return (List)objectMapper.readValue(AesCipherUtil.exchange((byte[])content), (JavaType)collectionType);
        }
        catch (Exception e) {
            logger.error("JsonProcessingException: ", e);
            return null;
        }
    }

    private static ObjectMapper getObjectMapper() {
        return JSONUtil.getObjectMapper(DEFAULT_DATE_FORMAT, false, JsonInclude.Include.NON_NULL);
    }

    private static ObjectMapper getObjectMapper(String dataFormatString, boolean prettyFormat, JsonInclude.Include include) {
        String key = (prettyFormat ? "P#" + dataFormatString : dataFormatString) + include.name();
        if (!mapperMap.containsKey(key)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONUtil.initMapper(objectMapper, dataFormatString, prettyFormat, include);
            mapperMap.putIfAbsent(key, objectMapper);
        }
        return mapperMap.get(key);
    }

    private static ObjectMapper getMessagePackMapper() {
        return JSONUtil.getMessagePackMapper(JsonInclude.Include.NON_NULL);
    }

    private static ObjectMapper getMessagePackMapper(JsonInclude.Include include) {
        String key = MSGPACK + include.name();
        if (!mapperMap.containsKey(key)) {
            MessagePackMapper objectMapper = new MessagePackMapper();
            JSONUtil.initMapper((ObjectMapper)objectMapper, "##long", false, include);
            mapperMap.putIfAbsent(key, (ObjectMapper)objectMapper);
        }
        return mapperMap.get(key);
    }

    private static void initMapper(ObjectMapper objectMapper, final String dataFormatString, boolean prettyFormat, JsonInclude.Include include) {
        if (environment == null) {
            environment = (Environment)ApplicationContextRegister.getBean(Environment.class);
        }
        if (environment != null) {
            String gmt = environment.getProperty("spring.jackson.time-zone", "GMT+8");
            objectMapper.setTimeZone(TimeZone.getTimeZone(gmt));
        } else {
            objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
        objectMapper.setSerializationInclusion(include);
        objectMapper.enable(new JsonParser.Feature[]{JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, JsonParser.Feature.ALLOW_SINGLE_QUOTES});
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        if (prettyFormat) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        SimpleModule module = new SimpleModule();
        if (dataFormatString.startsWith("##")) {
            if (!dataFormatString.equals("##long")) {
                dateFormatMap.putIfAbsent(dataFormatString, DateTimeFormatter.ofPattern(dataFormatString.substring(2)).withZone(ZoneId.systemDefault()));
            }
            module.addSerializer(Date.class, (JsonSerializer)new JsonSerializer<Date>(){

                public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    if (value == null) {
                        gen.writeNull();
                    } else if (dataFormatString.equals("##long")) {
                        gen.writeNumber(value.getTime());
                    } else {
                        DateTimeFormatter f = (DateTimeFormatter)dateFormatMap.get(dataFormatString);
                        gen.writeString(f.format(value.toInstant()));
                    }
                }
            });
        } else if (DEFAULT_DATE_FORMAT.equals(dataFormatString)) {
            objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        } else {
            objectMapper.setDateFormat((DateFormat)new SimpleDateFormat(dataFormatString));
        }
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
        objectMapper.registerModule((Module)module);
    }

    @Deprecated
    public static String beanToJson(Object value) {
        return JSONUtil.toJSONString(value);
    }

    @Deprecated
    public static String beanToJson(Object value, String dataFormatString) {
        return JSONUtil.toJSONString(value, dataFormatString);
    }

    @Deprecated
    public static Object jsonToBean(String content, Object clazz) {
        return JSONUtil.parseObject(content, clazz.getClass());
    }

    @Deprecated
    public static Map<String, Object> jsonToMap(String content) {
        return JSONUtil.parseMap(content);
    }
}

