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
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import java.io.IOException;

public class QueryItemSortDefineSerializer
extends JsonSerializer<QueryItemSortDefine> {
    public void serialize(QueryItemSortDefine sortInfo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("sorttype", (Object)sortInfo.getSortType());
        gen.writeObjectField("filters", sortInfo.getFilterCondition());
        gen.writeObjectField("data", sortInfo.getData());
        gen.writeObjectField("relation", (Object)sortInfo.getFilterRelation());
        gen.writeObjectField("filtervalues", sortInfo.getFilterValues());
        gen.writeEndObject();
    }
}

