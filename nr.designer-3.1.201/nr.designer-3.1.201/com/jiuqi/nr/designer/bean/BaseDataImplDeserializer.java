/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.designer.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.designer.bean.BaseDataImpl;
import java.io.IOException;
import java.util.Iterator;

public class BaseDataImplDeserializer
extends JsonDeserializer<BaseDataImpl> {
    public BaseDataImpl deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        BaseDataImpl impl = new BaseDataImpl();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("key");
        String key = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("parentid");
        String parentid = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("ordinal");
        String ordinal = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("leaf");
        boolean leaf = target != null && !target.isNull() ? target.asBoolean() : false;
        JsonNode jPath = jNode.get("parents");
        if (jPath != null && jPath.isArray()) {
            ArrayNode nodes = (ArrayNode)jPath;
            String[] path = new String[nodes.size()];
            for (int i = 0; i < nodes.size(); ++i) {
                JsonNode jsonNode = nodes.get(i);
                path[i] = jsonNode.asText();
            }
            impl.setParents(path);
        }
        impl.setId(id);
        impl.setKey(key);
        impl.setCode(code);
        impl.setTitle(title);
        impl.setParentid(parentid);
        impl.setOrdinal(ordinal);
        impl.setLeaf(leaf);
        Iterator fieldNames = jNode.fieldNames();
        while (fieldNames.hasNext() && !fieldNames.equals("refs")) {
            String fieldName = (String)fieldNames.next();
            String value = jNode.get(fieldName).asText("");
            impl.setFieldValue(fieldName, value);
        }
        return impl;
    }
}

