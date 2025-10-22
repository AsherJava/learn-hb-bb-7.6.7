/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.service.impl.EntityDataObject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class EntityDataSerialize
extends JsonSerializer<EntityDataObject> {
    public void serialize(EntityDataObject entData, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("key", entData.getKey());
        gen.writeStringField("code", entData.getCode());
        gen.writeStringField("title", entData.getTitle());
        gen.writeStringField("parentId", entData.getParentId());
        gen.writeObjectField("viewKey", (Object)entData.getViewKey());
        gen.writeObjectField("path", (Object)entData.getPath());
        gen.writeNumberField("childCount", entData.getChildCount());
        gen.writeObjectField("tags", entData.getTags());
        gen.writeStringField("nodeType", entData.getNodeType());
        gen.writeStringField("versionBeginDate", String.valueOf(entData.getVersionBeginDate()));
        gen.writeStringField("versionEndDate", String.valueOf(entData.getVersionEndDate()));
        gen.writeBooleanField("success", entData.isSuccess());
        Map<String, Object> valueMap = entData.getValueMap();
        Set<Map.Entry<String, Object>> entrySet = valueMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            if ("key".equals(key) || "code".equals(key) || "title".equals(key) || "path".equals(key) || "parentId".equals(key) || "viewKey".equals(key)) continue;
            gen.writeObjectField(key, entry.getValue());
        }
        gen.writeEndObject();
    }
}

