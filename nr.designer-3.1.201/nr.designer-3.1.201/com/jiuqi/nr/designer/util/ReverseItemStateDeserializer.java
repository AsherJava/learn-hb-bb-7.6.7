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
 */
package com.jiuqi.nr.designer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.designer.common.ReverseItemState;
import java.io.IOException;

public class ReverseItemStateDeserializer
extends JsonDeserializer<ReverseItemState> {
    public ReverseItemState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = (ObjectMapper)p.getCodec();
        JsonNode treeNode = (JsonNode)objectMapper.readTree(p);
        ReverseItemState[] values = ReverseItemState.values();
        return values[treeNode.asInt() - 1];
    }
}

