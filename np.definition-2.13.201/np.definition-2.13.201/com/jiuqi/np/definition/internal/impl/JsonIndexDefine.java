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
import com.jiuqi.np.definition.facade.TableIndexDefine;
import com.jiuqi.np.definition.internal.impl.TableIndexDefineImpl;
import java.io.IOException;

public class JsonIndexDefine
extends JsonDeserializer<TableIndexDefine> {
    public TableIndexDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken currentToken = p.getCurrentToken();
        if (currentToken == JsonToken.START_ARRAY) {
            return ctxt.readValue(p, Object.class) == null ? null : null;
        }
        if (currentToken == JsonToken.START_OBJECT) {
            return (TableIndexDefine)ctxt.readValue(p, TableIndexDefineImpl.class);
        }
        return null;
    }
}

