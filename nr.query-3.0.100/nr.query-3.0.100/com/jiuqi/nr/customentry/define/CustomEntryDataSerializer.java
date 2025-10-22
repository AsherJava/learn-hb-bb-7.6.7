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
import com.jiuqi.nr.customentry.define.CustomEntryData;
import java.io.IOException;

public class CustomEntryDataSerializer
extends JsonSerializer<CustomEntryData> {
    public void serialize(CustomEntryData define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("type", (Object)define.getType());
        gen.writeObjectField("cells", define.getCells());
        gen.writeEndObject();
    }
}

