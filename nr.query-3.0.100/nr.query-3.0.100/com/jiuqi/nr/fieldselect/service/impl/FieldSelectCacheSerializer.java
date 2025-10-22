/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.fieldselect.service.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectCache;
import java.io.IOException;

public class FieldSelectCacheSerializer
extends JsonSerializer<FieldSelectCache> {
    public void serialize(FieldSelectCache define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("schemeKey", (Object)define.getSchemeKey());
        gen.writeObjectField("taskKey", (Object)define.getTaskKey());
        gen.writeObjectField("fieldCount", (Object)define.getFieldCount());
        gen.writeObjectField("masterKeys", (Object)define.getMasterKeys());
        gen.writeObjectField("relatedTasks", define.getRelatedTasks());
        gen.writeObjectField("bizFields", define.getBizFields());
        gen.writeObjectField("masterDimensions", (Object)define.getMasterDimensions());
        gen.writeObjectField("bizFieldKeys", (Object)define.getBizFieldKeys());
        gen.writeObjectField("periodType", (Object)define.getPeriodType());
        gen.writeEndObject();
    }
}

