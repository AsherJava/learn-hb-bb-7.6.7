/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.dashboard.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.dashboard.defines.DashboardTreeNode;
import java.io.IOException;

public class DashboradNodeSerializer
extends JsonSerializer<DashboardTreeNode> {
    public void serialize(DashboardTreeNode treeNode, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("guid", (Object)treeNode.getId());
        gen.writeStringField("code", treeNode.getCode());
        gen.writeStringField("title", treeNode.getTitle());
        gen.writeObjectField("parentid", (Object)treeNode.getParent());
        gen.writeObjectField("isgroup", (Object)treeNode.getIsgroup());
        gen.writeEndObject();
    }
}

