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
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import java.io.IOException;

public class SchemeGroupSerializer
extends JsonSerializer<SummaryGroupDefine> {
    private static final String KEY = "key";
    private static final String TITLE = "title";
    private static final String TASK = "task";
    private static final String GROUP = "group";

    public void serialize(SummaryGroupDefine value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(KEY, value.getKey());
        gen.writeStringField(TITLE, value.getTitle());
        gen.writeStringField(TASK, value.getTask());
        gen.writeStringField(GROUP, value.getParent());
        gen.writeEndObject();
    }
}

