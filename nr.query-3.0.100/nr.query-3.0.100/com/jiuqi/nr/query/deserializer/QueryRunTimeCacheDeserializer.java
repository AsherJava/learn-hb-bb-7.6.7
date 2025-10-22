/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.query.service.impl.QueryRunTimeCache;
import java.io.IOException;

public class QueryRunTimeCacheDeserializer
extends JsonDeserializer<QueryRunTimeCache> {
    public QueryRunTimeCache deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryRunTimeCache cache = new QueryRunTimeCache();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue(QueryRunTimeCache.CONST_DEPTH);
        Integer depth = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        cache.setDepth(depth);
        target = jNode.findValue(QueryRunTimeCache.CONST_DIMENSIONID);
        String dimid = target != null && !target.isNull() ? target.asText() : null;
        cache.setDimensionId(dimid);
        target = jNode.findValue(QueryRunTimeCache.CONST_DIMENSIONSET);
        String dimSet = target != null && !target.isNull() ? target.asText() : null;
        ObjectMapper mapper = new ObjectMapper();
        cache.setDimensionSet((DimensionValueSet)mapper.readValue(dimSet, DimensionValueSet.class));
        target = jNode.findValue(QueryRunTimeCache.CONST_DIMNAME);
        String dimName = target != null && !target.isNull() ? target.asText() : null;
        cache.setDimName(dimName);
        target = jNode.findValue(QueryRunTimeCache.CONST_HASCHILD);
        boolean hasChild = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        cache.setHasChild(hasChild);
        target = jNode.findValue(QueryRunTimeCache.CONST_ISFIRSTDIM);
        boolean isFirst = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        cache.setIsFirstDimension(isFirst);
        target = jNode.findValue(QueryRunTimeCache.CONST_ISFROMDATA);
        boolean isFromData = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        cache.setIsFromData(isFromData);
        target = jNode.findValue(QueryRunTimeCache.CONST_ISLAST);
        boolean isLast = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        cache.setIsLast(isLast);
        target = jNode.findValue(QueryRunTimeCache.CONST_ISSUMROW);
        boolean isSumRow = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        cache.setIsSumRow(isSumRow);
        target = jNode.findValue(QueryRunTimeCache.CONST_ISTREE);
        boolean isTree = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        cache.setIsTree(isTree);
        target = jNode.findValue(QueryRunTimeCache.CONST_TITLE);
        String title = target != null && !target.isNull() ? target.asText() : null;
        cache.setTitle(title);
        return cache;
    }
}

