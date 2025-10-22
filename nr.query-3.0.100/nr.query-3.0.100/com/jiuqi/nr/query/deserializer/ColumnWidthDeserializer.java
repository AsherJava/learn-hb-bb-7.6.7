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
import com.jiuqi.nr.query.block.ColumnWidth;
import java.io.IOException;

public class ColumnWidthDeserializer
extends JsonDeserializer<ColumnWidth> {
    public ColumnWidth deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ColumnWidth col = new ColumnWidth();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("column");
        Integer column = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        target = jNode.findValue("width");
        Integer width = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        target = jNode.findValue("colTag");
        String tag = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isheader");
        Boolean isHeader = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        col.setColumn(column);
        col.setWidth(width);
        col.setIsheader(isHeader);
        col.setColTag(tag);
        return col;
    }
}

