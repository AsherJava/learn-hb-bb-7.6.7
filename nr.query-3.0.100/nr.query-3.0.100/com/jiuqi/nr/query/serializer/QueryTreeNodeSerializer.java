/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.querymodal.QueryModalTreeNode;
import java.io.IOException;

public class QueryTreeNodeSerializer
extends JsonSerializer<QueryModalTreeNode> {
    public void serialize(QueryModalTreeNode treeNode, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("key", (Object)treeNode.getId());
        gen.writeStringField("code", treeNode.getCode());
        gen.writeStringField("title", treeNode.getTitle());
        gen.writeObjectField("parentid", (Object)treeNode.getParentId());
        gen.writeObjectField("isgroup", (Object)treeNode.isIsgroup());
        gen.writeObjectField("updatetime", (Object)treeNode.getUpdatetime());
        gen.writeObjectField("taskid", (Object)treeNode.getId());
        gen.writeObjectField("viewid", (Object)treeNode.getViewid());
        gen.writeObjectField("type", (Object)treeNode.getNodeType());
        gen.writeObjectField("modeltype", (Object)treeNode.getModelType());
        gen.writeObjectField("treetype", (Object)treeNode.getTreeType());
        gen.writeObjectField("auth", (Object)treeNode.getAuth());
        gen.writeObjectField("modelCategory", (Object)treeNode.getModelCategory());
        gen.writeEndObject();
    }
}

