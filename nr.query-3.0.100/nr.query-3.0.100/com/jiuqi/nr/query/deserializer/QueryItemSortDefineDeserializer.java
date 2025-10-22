/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.block.QueryItemSortDefine;
import com.jiuqi.nr.query.block.QuerySortType;
import java.io.IOException;

public class QueryItemSortDefineDeserializer
extends JsonDeserializer<QueryItemSortDefine> {
    public QueryItemSortDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryItemSortDefine sortInfo = new QueryItemSortDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("sorttype");
        QuerySortType type = target != null && !target.isNull() ? QuerySortType.parse(target.asText()) : QuerySortType.SORT_ASC;
        target = jNode.findValue("filters");
        String filters = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("data");
        String data = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("relation");
        String fr = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("filtervalues");
        String fvs = target != null && !target.isNull() ? target.toString() : null;
        sortInfo.setSortType(type);
        sortInfo.setFilterConditionStr(filters);
        sortInfo.setDataStr(data);
        sortInfo.setFilterRelation(fr);
        sortInfo.setFilterValues(fvs);
        return sortInfo;
    }
}

