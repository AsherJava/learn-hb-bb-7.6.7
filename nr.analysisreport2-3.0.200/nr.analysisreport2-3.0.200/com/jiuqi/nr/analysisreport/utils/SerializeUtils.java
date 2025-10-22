/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.analysisreport.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.analysisreport.common.NrAnalysisErrorEnum;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {
    private static final Logger log = LoggerFactory.getLogger(SerializeUtils.class);

    public static byte[] serialize1(Object obj) {
        byte[] result = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);){
            oos.writeObject(obj);
            result = baos.toByteArray();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T> String serialize(T obj) throws JQException {
        if (obj == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static final <T> T deserialize1(byte[] byteArray, final Class<T> t) {
        if (byteArray == null) {
            return null;
        }
        Object result = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bais){

            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                return t != null && desc.getName().equals(t.getName()) ? t : super.resolveClass(desc);
            }
        };){
            result = ois.readObject();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return (T)result;
    }

    public static String jsonSerialize(Object obj) throws JQException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_301, (Throwable)e);
        }
    }

    public static byte[] jsonSerializeToByte(Object obj) throws JQException {
        if (null == obj) {
            return new byte[0];
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(obj);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_301, (Throwable)e);
        }
    }

    public static <T> T jsonDeserialize(String jsonStr, Class<T> t) throws JQException {
        if (StringUtils.isEmpty((String)jsonStr)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (T)objectMapper.readValue(jsonStr, t);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static <T> T jsonDeserialize(byte[] byteArray, Class<T> t) throws JQException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (T)objectMapper.readValue(byteArray, t);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static <T> T jsonDeserialize(File file, Class<T> t) throws JQException {
        if (null == file) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (T)objectMapper.readValue(file, t);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static <T> List<T> jsonDeserializeToArray(byte[] byteArray, Class<T> t) throws JQException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{t});
        try {
            return (List)objectMapper.readValue(byteArray, valueType);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static <T> List<T> jsonDeserializeToArray(String jsonStr, Class<T> t) throws JQException {
        if (null == jsonStr || jsonStr.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{t});
        try {
            return (List)objectMapper.readValue(jsonStr, valueType);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static <K, V> Map<K, V> jsonDeserializeToMap(byte[] byteArray, Class<K> k, Class<V> v) throws JQException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{k, v});
        try {
            return (Map)objectMapper.readValue(byteArray, valueType);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_302, (Throwable)e);
        }
    }

    public static byte[] serializeByObjectStream(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(stream).writeObject(object);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not serialize object of type: " + object.getClass(), e);
        }
        return stream.toByteArray();
    }

    public static Object deserializeByObjectStream(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize object", e);
        }
    }
}

