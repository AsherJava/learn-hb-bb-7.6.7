/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskParam
 *  com.jiuqi.np.asynctask.ParamConverter
 *  com.jiuqi.np.asynctask.exception.ParameterConversionException
 */
package com.jiuqi.np.asynctask.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskParam;
import com.jiuqi.np.asynctask.ParamConverter;
import com.jiuqi.np.asynctask.exception.ParameterConversionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PatternMatchUtils;

public class SimpleParamConverter
implements ParamConverter {
    public static final String DEFAULT_CHARSET = "UTF-8";
    private volatile String defaultCharset = "UTF-8";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public Object fromParam(AsyncTaskParam param) throws ParameterConversionException {
        Object content = null;
        String contentType = param.getContentType();
        if (contentType != null) {
            if (contentType != null && contentType.startsWith("text")) {
                String encoding = param.getContentEncoding();
                if (encoding == null) {
                    encoding = this.defaultCharset;
                }
                try {
                    content = new String(param.getBody(), encoding);
                }
                catch (UnsupportedEncodingException e) {
                    throw new ParameterConversionException("failed to convert text-based Message content", (Throwable)e);
                }
            }
            if (contentType != null && contentType.equals("application/x-java-serialized-object")) {
                try {
                    content = SerializationUtils.deserialize(param.getBody());
                }
                catch (IllegalArgumentException | IllegalStateException e) {
                    throw new ParameterConversionException("failed to convert serialized Message content", (Throwable)e);
                }
            }
        }
        if (content == null) {
            content = param.getBody();
        }
        return content;
    }

    public Object fromParam(byte[] bytes) throws ParameterConversionException {
        AsyncTaskParam param = (AsyncTaskParam)SerializationUtils.deserialize(bytes);
        if (null == param) {
            return null;
        }
        Object content = null;
        String contentType = param.getContentType();
        if (contentType != null) {
            if (contentType != null && contentType.startsWith("text")) {
                String encoding = param.getContentEncoding();
                if (encoding == null) {
                    encoding = this.defaultCharset;
                }
                try {
                    content = new String(param.getBody(), encoding);
                }
                catch (UnsupportedEncodingException e) {
                    throw new ParameterConversionException("failed to convert text-based Message content", (Throwable)e);
                }
            }
            if (contentType != null && contentType.equals("application/x-java-serialized-object")) {
                try {
                    content = SerializationUtils.deserialize(param.getBody());
                }
                catch (IllegalArgumentException | IllegalStateException e) {
                    throw new ParameterConversionException("failed to convert serialized Message content", (Throwable)e);
                }
            }
        }
        if (content == null) {
            content = param.getBody();
        }
        return content;
    }

    public AsyncTaskParam createParam(Object object) throws ParameterConversionException {
        if (null == object) {
            return null;
        }
        AsyncTaskParam asyncTaskParam = null;
        byte[] bytes = null;
        if (object instanceof byte[]) {
            bytes = (byte[])object;
            asyncTaskParam = new AsyncTaskParam(bytes);
            asyncTaskParam.setContentType("application/octet-stream");
            return asyncTaskParam;
        }
        if (object instanceof String) {
            try {
                bytes = ((String)object).getBytes(this.defaultCharset);
            }
            catch (UnsupportedEncodingException e) {
                throw new ParameterConversionException("failed to convert to Message content", (Throwable)e);
            }
            asyncTaskParam = new AsyncTaskParam(bytes);
            asyncTaskParam.setContentType("text/plain");
            asyncTaskParam.setContentEncoding(this.defaultCharset);
            return asyncTaskParam;
        }
        if (object instanceof Serializable) {
            try {
                bytes = SerializationUtils.serialize(object);
            }
            catch (IllegalArgumentException e) {
                throw new ParameterConversionException("failed to convert to serialized Message content", (Throwable)e);
            }
            asyncTaskParam = new AsyncTaskParam(bytes);
            asyncTaskParam.setContentType("application/x-java-serialized-object");
            return asyncTaskParam;
        }
        throw new IllegalArgumentException(this.getClass().getSimpleName() + " only supports String, byte[] and Serializable payloads, received: " + object.getClass().getName());
    }

    public static final class SerializationUtils {
        private SerializationUtils() {
        }

        public static byte[] serialize(Object object) {
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

        public static String serializeToString(Object object) {
            if (object == null) {
                return null;
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                new ObjectOutputStream(stream).writeObject(object);
                byte[] bytes = stream.toByteArray();
                if (bytes.length == 0) {
                    return "";
                }
                return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new IllegalArgumentException("Could not serialize object of type: " + object.getClass(), e);
            }
        }

        public static Object deserialize(byte[] bytes) {
            if (bytes == null) {
                return null;
            }
            try {
                return SerializationUtils.deserialize(new ObjectInputStream(new ByteArrayInputStream(bytes)));
            }
            catch (IOException e) {
                throw new IllegalArgumentException("Could not deserialize object", e);
            }
        }

        public static Object deserialize(String objStr) {
            if (objStr == null) {
                return null;
            }
            try {
                byte[] bytes = objStr.isEmpty() ? new byte[]{} : Base64.getDecoder().decode(objStr);
                return SerializationUtils.deserialize(new ObjectInputStream(new ByteArrayInputStream(bytes)));
            }
            catch (IOException e) {
                throw new IllegalArgumentException("Could not deserialize object", e);
            }
        }

        public static Object deserialize(ObjectInputStream stream) {
            if (stream == null) {
                return null;
            }
            try {
                return stream.readObject();
            }
            catch (IOException e) {
                throw new IllegalArgumentException("Could not deserialize object", e);
            }
            catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not deserialize object type", e);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static Object deserialize(InputStream inputStream, final Set<String> whiteListPatterns, ClassLoader classLoader) throws IOException {
            try (ConfigurableObjectInputStream objectInputStream = new ConfigurableObjectInputStream(inputStream, classLoader){

                @Override
                protected Class<?> resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
                    Class<?> clazz = super.resolveClass(classDesc);
                    SerializationUtils.checkWhiteList(clazz, whiteListPatterns);
                    return clazz;
                }
            };){
                Object object = objectInputStream.readObject();
                return object;
            }
            catch (ClassNotFoundException ex) {
                throw new IOException("Failed to deserialize object type", ex);
            }
        }

        public static void checkWhiteList(Class<?> clazz, Set<String> whiteListPatterns) {
            if (ObjectUtils.isEmpty(whiteListPatterns)) {
                return;
            }
            if (clazz.isArray() || clazz.isPrimitive() || clazz.equals(String.class) || Number.class.isAssignableFrom(clazz)) {
                return;
            }
            String className = clazz.getName();
            for (String pattern : whiteListPatterns) {
                if (!PatternMatchUtils.simpleMatch(pattern, className)) continue;
                return;
            }
            throw new SecurityException("Attempt to deserialize unauthorized " + clazz);
        }
    }
}

