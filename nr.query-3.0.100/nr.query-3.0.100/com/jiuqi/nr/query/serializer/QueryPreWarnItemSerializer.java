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
import com.jiuqi.nr.query.block.QueryPreWarnItem;
import java.io.IOException;

public class QueryPreWarnItemSerializer
extends JsonSerializer<QueryPreWarnItem> {
    public void serialize(QueryPreWarnItem define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("value", (Object)define.getValue());
        gen.writeObjectField("color", (Object)define.getColor());
        gen.writeObjectField("firstSign", (Object)define.getFirstSign());
        gen.writeObjectField("secondSign", (Object)define.getSecondSign());
        gen.writeEndObject();
    }
}

