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
package com.jiuqi.nr.efdc.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.efdc.bean.EntityDataObject;
import java.io.IOException;
import java.util.Iterator;

public class EntityDataDeserializer
extends JsonDeserializer<EntityDataObject> {
    public EntityDataObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        EntityDataObject entData = new EntityDataObject();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("key");
        String key = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("parentId");
        String parentId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("viewKey");
        String viewkey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("versionBeginDate");
        String beginDate = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("versionEndDate");
        String endDate = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("childCount");
        int childCount = target != null && !target.isNumber() ? target.asInt() : 0;
        entData.setKey(key);
        entData.setCode(code);
        entData.setTitle(title);
        entData.setViewKey(viewkey);
        entData.setChildCount(childCount);
        entData.setParentId(parentId);
        entData.setSuccess(true);
        if (beginDate != null) {
            entData.setVersionBeginDate(Long.valueOf(beginDate));
        }
        if (endDate != null) {
            entData.setVersionEndDate(Long.valueOf(endDate));
        }
        Iterator fieldNames = jNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = (String)fieldNames.next();
            JsonNode attrNode = jNode.get(fieldName);
            Object value = attrNode.asText("");
            if (attrNode.isBoolean()) {
                value = attrNode.asBoolean();
            }
            entData.setFieldValue(fieldName, value);
        }
        return entData;
    }
}

