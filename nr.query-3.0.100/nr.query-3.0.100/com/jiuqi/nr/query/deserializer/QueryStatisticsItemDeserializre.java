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
import com.jiuqi.nr.query.block.QueryStatisticsItem;
import java.io.IOException;

public class QueryStatisticsItemDeserializre
extends JsonDeserializer<QueryStatisticsItem> {
    public QueryStatisticsItem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryStatisticsItem statisticsItem = new QueryStatisticsItem();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("formulaExpression");
        String formulaExpression = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("builtIn");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        statisticsItem.setFormulaExpression(formulaExpression);
        statisticsItem.setBuiltIn(code);
        statisticsItem.setTitle(title);
        return statisticsItem;
    }
}

