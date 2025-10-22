/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.io.IOException;
import org.json.JSONObject;

public class VariableMapDeserializer
extends JsonDeserializer<JSONObject> {
    public JSONObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode target = (JsonNode)p.getCodec().readTree(p);
        if (!target.isNull()) {
            return JavaBeanUtils.toJSONObject((String)target.toString());
        }
        return null;
    }
}

