/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.gcreport.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.gcreport.common.util.NumberUtils;
import java.io.IOException;
import java.util.Map;

public class MapToPropertyJsonSerializer
extends JsonSerializer<Map<String, ? extends Number>> {
    public void serialize(Map<String, ? extends Number> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNull();
        String fieldName = gen.getOutputContext().getCurrentName();
        for (Map.Entry<String, ? extends Number> entry : value.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                gen.writeStringField(fieldName + "_" + entry.getKey(), NumberUtils.formatInteger((Number)entry.getValue()));
                continue;
            }
            gen.writeStringField(fieldName + "_" + entry.getKey(), NumberUtils.format((Number)entry.getValue()));
        }
    }
}

