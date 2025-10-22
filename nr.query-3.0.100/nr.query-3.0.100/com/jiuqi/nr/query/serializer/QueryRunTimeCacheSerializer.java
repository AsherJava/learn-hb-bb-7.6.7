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
import com.jiuqi.nr.query.service.impl.QueryRunTimeCache;
import java.io.IOException;

public class QueryRunTimeCacheSerializer
extends JsonSerializer<QueryRunTimeCache> {
    public void serialize(QueryRunTimeCache cache, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField(QueryRunTimeCache.CONST_DEPTH, (Object)cache.getDepth());
        gen.writeObjectField(QueryRunTimeCache.CONST_DIMENSIONID, (Object)cache.getDimensionId());
        gen.writeObjectField(QueryRunTimeCache.CONST_DIMENSIONSET, (Object)cache.getDimensionSet().toString());
        gen.writeObjectField(QueryRunTimeCache.CONST_DIMNAME, (Object)cache.getDimName());
        gen.writeObjectField(QueryRunTimeCache.CONST_HASCHILD, (Object)cache.getHasChild());
        gen.writeObjectField(QueryRunTimeCache.CONST_ISFIRSTDIM, (Object)cache.getIsFirstDimension());
        gen.writeObjectField(QueryRunTimeCache.CONST_ISFROMDATA, (Object)cache.getIsFromData());
        gen.writeObjectField(QueryRunTimeCache.CONST_ISLAST, (Object)cache.getIsLast());
        gen.writeObjectField(QueryRunTimeCache.CONST_ISSUMROW, (Object)cache.getIsSumRow());
        gen.writeObjectField(QueryRunTimeCache.CONST_ISTREE, (Object)cache.getIsTree());
        gen.writeObjectField(QueryRunTimeCache.CONST_TITLE, (Object)cache.getTitle());
        gen.writeEndObject();
    }
}

