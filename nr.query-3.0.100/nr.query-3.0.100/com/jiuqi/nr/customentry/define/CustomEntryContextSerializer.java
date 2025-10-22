/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.customentry.define;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.customentry.define.CustomEntryContext;
import java.io.IOException;

public class CustomEntryContextSerializer
extends JsonSerializer<CustomEntryContext> {
    public void serialize(CustomEntryContext define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("block", (Object)define.getBlock());
        gen.writeStringField("datas", define.getDatasStr());
        gen.writeEndObject();
    }
}

