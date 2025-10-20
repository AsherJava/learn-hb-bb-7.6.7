/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat$Value
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.deser.ContextualDeserializer
 *  com.fasterxml.jackson.databind.introspect.Annotated
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.ser.ContextualSerializer
 *  com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 *  com.jiuqi.budget.common.thread.ThreadLruCache
 */
package com.jiuqi.budget.autoconfigure;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jiuqi.budget.common.thread.ThreadLruCache;
import com.jiuqi.budget.components.LinkId;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.DataPeriodTypeRegistrar;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

@Configuration(value="budCommonSerializerConfig")
public class SerializerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SerializerConfig.class);
    @Value(value="${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    @Primary
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, (JsonSerializer)new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, (JsonDeserializer)new LocalDateTimeDeserializer());
        javaTimeModule.addSerializer(Date.class, (JsonSerializer)new DateSerializer());
        javaTimeModule.addDeserializer(Date.class, (JsonDeserializer)new DateDeserializer());
        return javaTimeModule;
    }

    @Bean
    public Module periodSimpleModule() {
        SimpleModule periodSimpleModule = new SimpleModule();
        periodSimpleModule.addDeserializer(DataPeriod.class, (JsonDeserializer)new DataPeriodDeserialize());
        periodSimpleModule.addSerializer(DataPeriod.class, (JsonSerializer)new DataPeriodSerializer());
        return periodSimpleModule;
    }

    @Bean
    public Module periodTypeSimpleModule() {
        SimpleModule periodTypeSimpleModule = new SimpleModule();
        periodTypeSimpleModule.addDeserializer(IDataPeriodType.class, (JsonDeserializer)new DataPeriodTypeDeserialize());
        periodTypeSimpleModule.addKeyDeserializer(IDataPeriodType.class, new KeyDeserializer(){

            public Object deserializeKey(String key, DeserializationContext ctxt) {
                return DataPeriodTypeRegistrar.typeOf(key);
            }
        });
        periodTypeSimpleModule.addKeySerializer(IDataPeriodType.class, (JsonSerializer)new JsonSerializer<IDataPeriodType>(){

            public void serialize(IDataPeriodType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeFieldName(value.getName());
            }
        });
        periodTypeSimpleModule.addSerializer(IDataPeriodType.class, (JsonSerializer)new DataPeriodTypeSerializer());
        return periodTypeSimpleModule;
    }

    @Bean
    public Module linkIdSimpleModule() {
        SimpleModule linkIdSimpleModule = new SimpleModule();
        linkIdSimpleModule.addDeserializer(LinkId.class, (JsonDeserializer)new LinkIdDeserialize());
        linkIdSimpleModule.addSerializer(LinkId.class, (JsonSerializer)new LinkIdSerializer());
        return linkIdSimpleModule;
    }

    private String getCustomizePattern(AnnotationIntrospector annotationIntrospector, BeanProperty property) {
        if (property == null) {
            return this.pattern;
        }
        JsonFormat.Value format = annotationIntrospector.findFormat((Annotated)property.getMember());
        if (format == null) {
            return this.pattern;
        }
        String pattern = format.getPattern();
        if (StringUtils.hasText(pattern)) {
            return pattern;
        }
        return this.pattern;
    }

    public class DateDeserializer
    extends JsonDeserializer<Date>
    implements ContextualDeserializer {
        private final String customizePattern;

        public DateDeserializer(String customizePattern) {
            this.customizePattern = customizePattern;
        }

        public DateDeserializer() {
            this(null);
        }

        public Date deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            String value = p.getValueAsString();
            if (!StringUtils.hasText(value)) {
                return null;
            }
            try {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat)ThreadLruCache.computeIfAbsent((String)("SimpleDateFormat_" + this.customizePattern), k -> new SimpleDateFormat(this.customizePattern));
                return simpleDateFormat.parse(value);
            }
            catch (ParseException e) {
                try {
                    long date = Long.parseLong(value);
                    return new Date(date);
                }
                catch (NumberFormatException numberFormatException) {
                    logger.error(numberFormatException.getMessage(), numberFormatException);
                    return null;
                }
            }
        }

        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
            String customizePattern = SerializerConfig.this.getCustomizePattern(ctxt.getAnnotationIntrospector(), property);
            return new DateDeserializer(customizePattern);
        }
    }

    public class DateSerializer
    extends JsonSerializer<Date>
    implements ContextualSerializer {
        private final String customizePattern;

        public DateSerializer(String customizePattern) {
            this.customizePattern = customizePattern;
        }

        public DateSerializer() {
            this(null);
        }

        public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            SimpleDateFormat simpleDateFormat = (SimpleDateFormat)ThreadLruCache.computeIfAbsent((String)("SimpleDateFormat_" + this.customizePattern), k -> new SimpleDateFormat(this.customizePattern));
            gen.writeString(simpleDateFormat.format(value));
        }

        public JsonSerializer<Date> createContextual(SerializerProvider prov, BeanProperty property) {
            String customizePattern = SerializerConfig.this.getCustomizePattern(prov.getAnnotationIntrospector(), property);
            return new DateSerializer(customizePattern);
        }
    }

    public class LocalDateTimeDeserializer
    extends JsonDeserializer<LocalDateTime>
    implements ContextualDeserializer {
        private final String customizePattern;

        public LocalDateTimeDeserializer(String customizePattern) {
            this.customizePattern = customizePattern;
        }

        public LocalDateTimeDeserializer() {
            this(null);
        }

        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(this.customizePattern));
        }

        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
            String customizePattern = SerializerConfig.this.getCustomizePattern(ctxt.getAnnotationIntrospector(), property);
            return new LocalDateTimeDeserializer(customizePattern);
        }
    }

    public class LocalDateTimeSerializer
    extends JsonSerializer<LocalDateTime>
    implements ContextualSerializer {
        private final String customizePattern;

        public LocalDateTimeSerializer(String customizePattern) {
            this.customizePattern = customizePattern;
        }

        public LocalDateTimeSerializer() {
            this(null);
        }

        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(this.customizePattern)));
        }

        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
            String customizePattern = SerializerConfig.this.getCustomizePattern(prov.getAnnotationIntrospector(), property);
            return new LocalDateTimeSerializer(customizePattern);
        }
    }

    public static class LinkIdDeserialize
    extends JsonDeserializer<LinkId> {
        public LinkId deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            HashMap<String, String> map = new HashMap<String, String>();
            ObjectMapper mapper = (ObjectMapper)p.getCodec();
            JsonNode node = (JsonNode)mapper.readTree(p);
            node.fieldNames().forEachRemaining(fieldName -> map.put((String)fieldName, node.get(fieldName).asText()));
            return new LinkId(map);
        }
    }

    public static class LinkIdSerializer
    extends JsonSerializer<LinkId> {
        public void serialize(LinkId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            for (Map.Entry<String, String> entry : value) {
                gen.writeStringField(entry.getKey(), entry.getValue());
            }
            gen.writeEndObject();
        }
    }

    public static class DataPeriodDeserialize
    extends JsonDeserializer<DataPeriod> {
        public DataPeriod deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            return DataPeriodFactory.valueOf(p.getValueAsString());
        }
    }

    public static class DataPeriodSerializer
    extends JsonSerializer<DataPeriod> {
        public void serialize(DataPeriod value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class DataPeriodTypeDeserialize
    extends JsonDeserializer<IDataPeriodType> {
        public IDataPeriodType deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            return DataPeriodTypeRegistrar.typeOf(p.getValueAsString());
        }
    }

    public static class DataPeriodTypeSerializer
    extends JsonSerializer<IDataPeriodType> {
        public void serialize(IDataPeriodType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getName());
        }
    }
}

