/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.batch.summary.storage.entity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue;
import java.io.IOException;

public class CustomCalibreRowSerializer
extends JsonSerializer<CustomCalibreRowDefine> {
    private static final String KEY = "key";
    private static final String CODE = "code";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String EXPRESSION = "expression";
    private static final String PARENT_CODE = "parentCode";

    public void serialize(CustomCalibreRowDefine value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(KEY, value.getKey());
        gen.writeStringField(CODE, value.getCode());
        gen.writeStringField(TITLE, value.getTitle());
        gen.writeStringField(PARENT_CODE, value.getParentCode());
        CustomCalibreValue rowValue = value.getValue();
        gen.writeNumberField(TYPE, rowValue.getValueType().value);
        switch (rowValue.getValueType()) {
            case UNITS: {
                gen.writeObjectField(EXPRESSION, rowValue.getCheckList());
                break;
            }
            case EXPRESSION: {
                gen.writeStringField(EXPRESSION, rowValue.getExpression());
                break;
            }
            case INVALIDINPUT: {
                gen.writeStringField(EXPRESSION, rowValue.getExpression());
            }
        }
        gen.writeEndObject();
    }
}

