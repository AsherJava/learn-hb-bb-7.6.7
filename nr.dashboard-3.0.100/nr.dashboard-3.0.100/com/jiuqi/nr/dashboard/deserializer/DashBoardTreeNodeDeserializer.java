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
package com.jiuqi.nr.dashboard.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.dashboard.defines.DashboardTreeNode;
import java.io.IOException;

public class DashBoardTreeNodeDeserializer
extends JsonDeserializer<DashboardTreeNode> {
    public DashboardTreeNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DashboardTreeNode treeNode = new DashboardTreeNode();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("guid");
        String key = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isgroup");
        Boolean isgroup = target != null && !target.isBoolean() ? false : target.asBoolean();
        target = jNode.findValue("parentid");
        String parentId = target != null && !target.isNull() ? target.asText() : null;
        treeNode.setId(key);
        treeNode.setCode(code);
        treeNode.setTitle(title);
        treeNode.setIsgroup(isgroup);
        treeNode.setParent(parentId);
        return treeNode;
    }
}

