/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 */
package com.jiuqi.np.definition.internal.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class JsonString
extends JsonDeserializer<String> {
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken currentToken = p.getCurrentToken();
        if (currentToken == JsonToken.START_ARRAY) {
            String[] values = (String[])ctxt.readValue(p, String[].class);
            String ret = null;
            if (values != null) {
                for (String v : values) {
                    ret = ret == null ? v : ret + ";" + v;
                }
            }
            return ret;
        }
        if (currentToken == JsonToken.START_OBJECT) {
            return (String)ctxt.readValue(p, String.class);
        }
        return null;
    }
}

