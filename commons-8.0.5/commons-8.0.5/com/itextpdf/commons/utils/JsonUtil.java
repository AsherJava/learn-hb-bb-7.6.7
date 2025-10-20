/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonGenerator$Feature
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.core.util.DefaultIndenter
 *  com.fasterxml.jackson.core.util.DefaultPrettyPrinter
 *  com.fasterxml.jackson.core.util.DefaultPrettyPrinter$Indenter
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.ObjectWriter
 *  com.fasterxml.jackson.databind.SerializationFeature
 */
package com.itextpdf.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    public static boolean areTwoJsonObjectEquals(String expectedString, String toCompare) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedObject = mapper.readTree(expectedString);
        JsonNode actualObject = mapper.readTree(toCompare);
        return actualObject.equals((Object)expectedObject);
    }

    public static void serializeToStream(OutputStream outputStream, Object value) {
        JsonUtil.serializeToStream(outputStream, value, new CustomPrettyPrinter());
    }

    public static String serializeToString(Object value) {
        return JsonUtil.serializeToString(value, new CustomPrettyPrinter());
    }

    public static void serializeToMinimalStream(OutputStream outputStream, Object value) {
        JsonUtil.serializeToStream(outputStream, value, new MinimalPrinter());
    }

    public static String serializeToMinimalString(Object value) {
        return JsonUtil.serializeToString(value, new MinimalPrinter());
    }

    public static <T> T deserializeFromStream(InputStream content, Class<T> objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return JsonUtil.deserializeFromStream(content, objectMapper.constructType(objectType));
    }

    public static <T> T deserializeFromStream(InputStream content, TypeReference<T> objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return JsonUtil.deserializeFromStream(content, objectMapper.constructType(objectType));
    }

    public static <T> T deserializeFromStream(InputStream content, JavaType objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (T)objectMapper.readValue(content, objectType);
        }
        catch (IOException ex) {
            LOGGER.warn(MessageFormatUtil.format("Unable to deserialize json. Exception {0} was thrown with the message: {1}.", ex.getClass(), ex.getMessage()));
            return null;
        }
    }

    public static <T> T deserializeFromString(String content, Class<T> objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return JsonUtil.deserializeFromString(content, objectMapper.constructType(objectType));
    }

    public static <T> T deserializeFromString(String content, TypeReference<T> objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return JsonUtil.deserializeFromString(content, objectMapper.constructType(objectType));
    }

    public static <T> T deserializeFromString(String content, JavaType objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (T)objectMapper.readValue(content, objectType);
        }
        catch (JsonProcessingException ex) {
            LOGGER.warn(MessageFormatUtil.format("Unable to deserialize json. Exception {0} was thrown with the message: {1}.", ((Object)((Object)ex)).getClass(), ex.getMessage()));
            return null;
        }
    }

    private static ObjectWriter createAndConfigureObjectWriter(DefaultPrettyPrinter prettyPrinter) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(new JsonGenerator.Feature[]{JsonGenerator.Feature.AUTO_CLOSE_TARGET});
        return objectMapper.writer((PrettyPrinter)prettyPrinter);
    }

    private static void serializeToStream(OutputStream outputStream, Object value, DefaultPrettyPrinter prettyPrinter) {
        try {
            JsonUtil.createAndConfigureObjectWriter(prettyPrinter).writeValue(outputStream, value);
        }
        catch (IOException ex) {
            LOGGER.warn(MessageFormatUtil.format("Unable to serialize object. Exception {0} was thrown with the message: {1}.", ex.getClass(), ex.getMessage()));
        }
    }

    private static String serializeToString(Object value, DefaultPrettyPrinter prettyPrinter) {
        try {
            return JsonUtil.createAndConfigureObjectWriter(prettyPrinter).writeValueAsString(value);
        }
        catch (JsonProcessingException ex) {
            LOGGER.warn(MessageFormatUtil.format("Unable to serialize object. Exception {0} was thrown with the message: {1}.", ((Object)((Object)ex)).getClass(), ex.getMessage()));
            return null;
        }
    }

    private static class MinimalPrinter
    extends DefaultPrettyPrinter {
        public MinimalPrinter() {
            this._objectFieldValueSeparatorWithSpaces = ":";
            this.indentArraysWith((DefaultPrettyPrinter.Indenter)new DefaultIndenter("", ""));
            this.indentObjectsWith((DefaultPrettyPrinter.Indenter)new DefaultIndenter("", ""));
        }

        public DefaultPrettyPrinter createInstance() {
            return new MinimalPrinter();
        }
    }

    private static class CustomPrettyPrinter
    extends DefaultPrettyPrinter {
        public CustomPrettyPrinter() {
            this._objectFieldValueSeparatorWithSpaces = ": ";
            this.indentArraysWith((DefaultPrettyPrinter.Indenter)DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.withLinefeed("\n"));
            this.indentObjectsWith((DefaultPrettyPrinter.Indenter)DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.withLinefeed("\n"));
        }

        public DefaultPrettyPrinter createInstance() {
            return new CustomPrettyPrinter();
        }
    }
}

