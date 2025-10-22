/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.query.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.query.service.AiConfigs;
import java.io.IOException;
import java.util.Map;

class AiConfigDeserializer
extends JsonDeserializer<AiConfigs> {
    AiConfigDeserializer() {
    }

    public AiConfigs deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String periodType;
        String dimensions;
        AiConfigs config = new AiConfigs();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("dimensions");
        String string = dimensions = target != null && !target.isNull() ? target.toString() : null;
        if (dimensions != null) {
            ObjectMapper mapper = new ObjectMapper();
            config.dimensions = (Map)mapper.readValue(dimensions, (TypeReference)new TypeReference<Map<String, String>>(){});
        }
        String schemes = (target = jNode.findValue("schemes")) != null && !target.isNull() ? target.toString() : null;
        config.SetSchemesStr(schemes);
        target = jNode.findValue("periodType");
        config.periodType = periodType = target != null && !target.isNull() ? target.asText() : null;
        return config;
    }
}

