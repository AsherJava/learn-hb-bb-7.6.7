/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.batch.summary.storage.entity.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import java.io.IOException;

public class SchemeGroupDeserializer
extends JsonDeserializer<SummaryGroupDefine> {
    private static final String KEY = "key";
    private static final String TITLE = "title";
    private static final String TASK = "task";
    private static final String GROUP = "group";

    public SummaryGroupDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        if (!jNode.isNull()) {
            SummaryGroupDefine impl = new SummaryGroupDefine();
            JsonNode target = jNode.findValue(KEY);
            impl.setKey(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(TITLE);
            impl.setTitle(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(TASK);
            impl.setTask(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(GROUP);
            impl.setParent(target != null && !target.isNull() ? target.asText() : null);
            return impl;
        }
        return null;
    }
}

