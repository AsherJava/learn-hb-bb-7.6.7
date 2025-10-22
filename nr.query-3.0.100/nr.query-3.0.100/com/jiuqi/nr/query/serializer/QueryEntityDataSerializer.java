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
import com.jiuqi.nr.query.block.QueryEntityData;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.util.CollectionUtils;

public class QueryEntityDataSerializer
extends JsonSerializer<QueryEntityData> {
    public void serialize(QueryEntityData entityData, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)entityData.getId());
        gen.writeObjectField("title", (Object)entityData.getTitle());
        gen.writeObjectField("isLeaf", (Object)entityData.getIsLeaf());
        gen.writeObjectField("childcount", (Object)entityData.getChildCount());
        gen.writeObjectField("childs", entityData.getChilds());
        gen.writeObjectField("code", (Object)entityData.getCode());
        if (!CollectionUtils.isEmpty(entityData.getChilds())) {
            ArrayList children = new ArrayList();
            gen.writeObjectField("children", children);
        }
        gen.writeEndObject();
    }
}

