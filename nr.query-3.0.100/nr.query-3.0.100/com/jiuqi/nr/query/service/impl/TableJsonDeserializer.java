/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.np.definition.common.TableKind
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.nr.query.service.impl.TableWebObject;
import java.io.IOException;
import java.util.Iterator;

public class TableJsonDeserializer
extends JsonDeserializer<TableWebObject> {
    public TableWebObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TableWebObject tbObj = new TableWebObject();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.get("key");
        String key = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("order");
        String order = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("groupKey");
        String groupKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("isRelease");
        boolean isRelease = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.get("tableName");
        String tableName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("tableKind");
        int tbVal = target != null && !target.isNull() ? target.asInt() : 2;
        target = jNode.get("dictTreeStruct");
        String dict = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("alias");
        String alias = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("referEntityKey");
        String referEntityKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("referEntViewKey");
        String referEntViewKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("editable");
        boolean editable = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.get("readable");
        boolean readable = target != null && !target.isNull() ? target.asBoolean() : false;
        tbObj.setKey(key);
        tbObj.setCode(code);
        tbObj.setTitle(title);
        tbObj.setGroupKey(groupKey);
        tbObj.setOrder(order);
        tbObj.setRelease(isRelease);
        tbObj.setTableName(tableName);
        tbObj.setTableKind(TableKind.forValue((int)tbVal));
        tbObj.setDictTreeStruct(dict);
        tbObj.setAlias(alias);
        tbObj.setReferEntityKey(referEntityKey);
        tbObj.setReferEntViewKey(referEntViewKey);
        tbObj.setEditable(editable);
        tbObj.setReadable(readable);
        JsonNode jFields = jNode.get("fields");
        Iterator fieldNames = jNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = (String)fieldNames.next();
            JsonNode jsonNode = jNode.get(fieldName);
            Object value = null;
            value = jsonNode.isBoolean() ? Boolean.valueOf(jsonNode.asBoolean()) : jsonNode.asText();
            tbObj.setFieldValue(fieldName, 1, value);
        }
        return tbObj;
    }
}

