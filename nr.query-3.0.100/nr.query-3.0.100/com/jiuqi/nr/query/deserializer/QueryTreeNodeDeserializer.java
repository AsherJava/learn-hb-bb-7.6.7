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
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.querymodal.QueryModalTreeNode;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.io.IOException;

public class QueryTreeNodeDeserializer
extends JsonDeserializer<QueryModalTreeNode> {
    public QueryModalTreeNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryModalTreeNode treeNode = new QueryModalTreeNode();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("key");
        String key = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isgroup");
        Boolean isgroup = target != null && !target.isBoolean() ? false : target.asBoolean();
        target = jNode.findValue("updatetime");
        Long updatetime = target != null && !target.isLong() ? Long.valueOf(target.asLong()) : null;
        target = jNode.findValue("parentid");
        String parentId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("taskid");
        String taskId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("viewid");
        String viewId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("type");
        Integer type = target != null && !target.isNull() ? Integer.valueOf(Integer.parseInt(target.asText())) : null;
        target = jNode.findValue("modeltype");
        QueryModelType modelType = target != null && !target.isNull() ? QueryModelType.valueOf(target.toString()) : QueryModelType.OWNER;
        target = jNode.findValue("treetype");
        String treeType = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("auth");
        String auth = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("modelCategory");
        String category = target != null && !target.isNull() ? target.asText() : null;
        treeNode.setId(key);
        treeNode.setCode(code);
        treeNode.setTitle(title);
        treeNode.setIsgroup(isgroup);
        treeNode.setUpdatetime(updatetime);
        treeNode.setParentId(parentId);
        treeNode.setTaskid(taskId);
        treeNode.setViewid(viewId);
        treeNode.setNodeType(type);
        treeNode.setModelType(modelType);
        treeNode.setTreeType(treeType);
        treeNode.setAuth(auth);
        treeNode.setModelCategory(category);
        return treeNode;
    }
}

