/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.va.feign.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class VaJacksonSerialize {

    public static class VaDoublelSerialize
    extends JsonSerializer<Double> {
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
    }

    public static class VaBigDecimalSerialize
    extends JsonSerializer<BigDecimal> {
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
    }
}

