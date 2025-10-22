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
import com.jiuqi.nr.query.service.impl.TableWebObject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class TableJsonSerializer
extends JsonSerializer<TableWebObject> {
    public void serialize(TableWebObject tb, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("key", (Object)tb.getKey());
        gen.writeStringField("code", tb.getCode());
        gen.writeStringField("title", tb.getTitle());
        gen.writeObjectField("groupKey", (Object)tb.getGroupKey());
        gen.writeBooleanField("isRelease", tb.isRelease());
        gen.writeStringField("tableName", tb.getTableName());
        gen.writeStringField("dictTreeStruct", tb.getDictTreeStruct());
        gen.writeStringField("alias", tb.getAlias());
        gen.writeBooleanField("editable", tb.isEditable());
        gen.writeBooleanField("readable", tb.isReadable());
        if (tb.getReferEntityKey() != null) {
            gen.writeStringField("referEntityKey", tb.getReferEntityKey());
        }
        if (tb.getReferEntViewKey() != null) {
            gen.writeStringField("referEntViewKey", tb.getReferEntViewKey());
        }
        if (tb.getTableKind() != null) {
            gen.writeNumberField("tableKind", tb.getTableKind().getValue());
        }
        Map<String, Object> valueMap = tb.getValueMap();
        Set<Map.Entry<String, Object>> entrySet = valueMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            gen.writeObjectField(entry.getKey(), entry.getValue());
        }
        gen.writeEndObject();
    }
}

