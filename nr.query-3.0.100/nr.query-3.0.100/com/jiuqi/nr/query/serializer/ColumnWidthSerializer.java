/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.block.ColumnWidth;
import java.io.IOException;

public class ColumnWidthSerializer
extends JsonSerializer<ColumnWidth> {
    public void serialize(ColumnWidth col, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("column", (Object)col.getColumn());
        gen.writeObjectField("width", (Object)col.getWidth());
        gen.writeObjectField("colTag", (Object)col.getColTag());
        gen.writeObjectField("isheader", (Object)col.getIsheader());
        gen.writeEndObject();
    }
}

