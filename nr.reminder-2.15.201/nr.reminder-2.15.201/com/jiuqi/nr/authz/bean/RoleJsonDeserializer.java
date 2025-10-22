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
package com.jiuqi.nr.authz.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.authz.bean.RoleWebImpl;
import java.io.IOException;

public class RoleJsonDeserializer
extends JsonDeserializer<RoleWebImpl> {
    public RoleWebImpl deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        RoleWebImpl impl = new RoleWebImpl();
        JsonNode node = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = node.get("parentId");
        String parentId = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("groupFlag");
        Boolean groupFlag = target != null && !target.isNull() ? target.asBoolean() : false;
        target = node.get("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("description");
        String description = target != null && !target.isNull() ? target.asText() : null;
        target = node.get("key");
        String key = target != null && !target.isNull() ? target.asText() : null;
        impl.setParentId(parentId);
        impl.setId(id);
        impl.setCode(code);
        impl.setGroupFlag(groupFlag);
        impl.setTitle(title);
        impl.setDescription(description);
        impl.setKey(key);
        return impl;
    }
}

