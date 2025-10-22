/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.fieldselect.define;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.fieldselect.define.FormGroupData;
import java.io.IOException;

public class FormGroupDataSerializer
extends JsonSerializer<FormGroupData> {
    public void serialize(FormGroupData data, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)data.getId());
        gen.writeObjectField("title", (Object)data.getTitle());
        gen.writeObjectField("forms", data.getForms());
        gen.writeEndObject();
    }
}

