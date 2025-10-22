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
package com.jiuqi.nr.fieldselect.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectCache;
import java.io.IOException;

public class FieldSelectCacheDeserializer
extends JsonDeserializer<FieldSelectCache> {
    public FieldSelectCache deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        FieldSelectCache define = new FieldSelectCache();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("schemeKey");
        String schemeKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldCount");
        int fieldCount = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        target = jNode.findValue("masterKeys");
        String masterKeys = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("taskKey");
        String taskKey = target != null && !target.isNull() ? target.asText() : null;
        define.setFieldCount(fieldCount);
        define.setMasterKeys(masterKeys);
        define.setSchemeKey(schemeKey);
        define.setTaskKey(taskKey);
        return define;
    }
}

