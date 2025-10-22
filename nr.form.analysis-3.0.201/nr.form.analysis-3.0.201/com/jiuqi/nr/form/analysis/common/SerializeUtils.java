/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.form.analysis.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.form.analysis.common.FormAnalysisErrorEnum;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.List;
import java.util.Map;

public class SerializeUtils {
    private static final Logger log = LogFactory.getLogger(SerializeUtils.class);

    public static byte[] serialize1(Object obj) {
        byte[] result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos);){
            oos.writeObject(obj);
            result = baos.toByteArray();
        }
        catch (IOException e) {
            log.error(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    public static final <T> T deserialize1(byte[] byteArray, final Class<T> t) {
        if (byteArray == null) {
            return null;
        }
        Object result = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        try (ObjectInputStream ois = new ObjectInputStream(bais){

            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                return t != null && desc.getName().equals(t.getName()) ? t : super.resolveClass(desc);
            }
        };){
            result = ois.readObject();
        }
        catch (IOException e) {
            log.error(e.getMessage(), (Throwable)e);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage(), (Throwable)e);
        }
        return (T)result;
    }

    public static String jsonSerialize(Object obj) throws JQException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_301, (Throwable)e);
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
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_301, (Throwable)e);
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
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_302, (Throwable)e);
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
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_302, (Throwable)e);
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
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_302, (Throwable)e);
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
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_302, (Throwable)e);
        }
    }

    public static <T> List<T> jsonDeserializeToArray(Module module, String jsonStr, Class<T> t) throws JQException {
        if (null == jsonStr || jsonStr.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(module);
        JavaType valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{t});
        try {
            return (List)objectMapper.readValue(jsonStr, valueType);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_302, (Throwable)e);
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
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_302, (Throwable)e);
        }
    }
}

