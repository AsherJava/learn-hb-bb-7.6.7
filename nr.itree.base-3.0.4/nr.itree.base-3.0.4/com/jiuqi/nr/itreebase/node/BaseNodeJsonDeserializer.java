/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.itreebase.node;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.io.IOException;
import java.util.Iterator;

public class BaseNodeJsonDeserializer
extends JsonDeserializer<IBaseNodeData> {
    public IBaseNodeData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        if (!jNode.isNull()) {
            JsonNode data;
            JsonParser traverse = jNode.traverse();
            ObjectMapper mapper = (ObjectMapper)p.getCodec();
            BaseNodeDataImpl impl = (BaseNodeDataImpl)mapper.readValue(traverse, BaseNodeDataImpl.class);
            JsonNode target = jNode.findValue("key");
            impl.setKey(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue("code");
            impl.setCode(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue("title");
            impl.setTitle(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue("path");
            if (target != null && !target.isNull()) {
                impl.setPath((String[])mapper.readValue(target.traverse(), String[].class));
            }
            if ((data = jNode.findValue("data")) != null && data.isObject()) {
                Iterator iterator = jNode.findValue("data").fieldNames();
                while (iterator.hasNext()) {
                    String key = (String)iterator.next();
                    JsonNode value = data.findValue(key);
                    if (value == null) continue;
                    if (value.isTextual()) {
                        impl.put(key, (Object)value.asText());
                        continue;
                    }
                    if (value.isBoolean()) {
                        impl.put(key, (Object)value.asBoolean());
                        continue;
                    }
                    if (value.isNumber()) {
                        impl.put(key, (Object)value.asDouble());
                        continue;
                    }
                    if (!value.isNull()) continue;
                    impl.put(key, (Object)null);
                }
            }
            return impl;
        }
        return null;
    }
}

