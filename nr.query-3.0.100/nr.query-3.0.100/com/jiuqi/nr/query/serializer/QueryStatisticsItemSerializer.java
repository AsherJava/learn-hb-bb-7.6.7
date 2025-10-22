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
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import java.io.IOException;

public class QueryStatisticsItemSerializer
extends JsonSerializer<QueryStatisticsItem> {
    public void serialize(QueryStatisticsItem StatisticsItem, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("formulaExpression", (Object)StatisticsItem.getFormulaExpression());
        gen.writeObjectField("builtIn", (Object)StatisticsItem.getBuiltIn());
        gen.writeObjectField("title", (Object)StatisticsItem.getTitle());
        gen.writeEndObject();
    }
}

