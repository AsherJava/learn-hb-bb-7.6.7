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
import com.jiuqi.nr.query.block.QueryEntityData;
import java.io.IOException;

public class QueryEntityDataDeserializer
extends JsonDeserializer<QueryEntityData> {
    public QueryEntityData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryEntityData entityData = new QueryEntityData();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        entityData.setId(id);
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : "";
        entityData.setTitle(title);
        target = jNode.findValue("isLeaf");
        boolean isLeaf = target != null && !target.isNull() ? Boolean.valueOf(target.asText()) : false;
        entityData.setIsLeaf(isLeaf);
        target = jNode.findValue("title");
        int childCount = target != null && !target.isNull() ? Integer.getInteger(target.asText()) : 0;
        entityData.setChildCount(childCount);
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : "";
        entityData.setCode(code);
        return entityData;
    }
}

