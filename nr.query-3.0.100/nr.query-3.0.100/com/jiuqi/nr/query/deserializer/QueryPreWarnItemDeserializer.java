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
import com.jiuqi.nr.query.block.QueryComparisonType;
import com.jiuqi.nr.query.block.QueryPreWarnItem;
import java.io.IOException;

public class QueryPreWarnItemDeserializer
extends JsonDeserializer<QueryPreWarnItem> {
    public QueryPreWarnItem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryPreWarnItem queryPreWarnItem = new QueryPreWarnItem();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("value");
        Double value = target != null && !target.isNull() ? Double.valueOf(Double.parseDouble(target.asText())) : null;
        target = jNode.findValue("color");
        String color = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("firstSign");
        String firstSign = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("secondSign");
        String secondSign = target != null && !target.isNull() ? target.asText() : null;
        queryPreWarnItem.setValue(new Double[]{value});
        queryPreWarnItem.setColor(color);
        queryPreWarnItem.setFirstSign(QueryComparisonType.valueOf(firstSign));
        queryPreWarnItem.setSecondSign(QueryComparisonType.valueOf(secondSign));
        return queryPreWarnItem;
    }
}

