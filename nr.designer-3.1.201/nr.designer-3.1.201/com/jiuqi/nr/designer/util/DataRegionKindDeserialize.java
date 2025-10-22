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
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.designer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.common.DataRegionKind;
import java.io.IOException;

public class DataRegionKindDeserialize
extends JsonDeserializer<DataRegionKind> {
    public DataRegionKind deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode treeNode = (JsonNode)mapper.readTree(p);
        return DataRegionKind.values()[treeNode.asInt()];
    }
}

