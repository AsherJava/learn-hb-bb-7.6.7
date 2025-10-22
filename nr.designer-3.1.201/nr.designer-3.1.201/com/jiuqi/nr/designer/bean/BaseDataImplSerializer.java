/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.designer.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.designer.bean.BaseDataImpl;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class BaseDataImplSerializer
extends JsonSerializer<BaseDataImpl> {
    public void serialize(BaseDataImpl impl, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", impl.getId());
        gen.writeStringField("key", impl.getKey());
        gen.writeStringField("code", impl.getCode());
        gen.writeStringField("title", impl.getTitle());
        gen.writeStringField("parentid", impl.getParentid());
        gen.writeStringField("ordinal", impl.getOrdinal());
        gen.writeBooleanField("leaf", impl.isLeaf());
        String[] path = impl.getParents();
        if (path != null && path.length > 0) {
            gen.writeArrayFieldStart("parents");
            for (String p : path) {
                gen.writeObject((Object)p);
            }
            gen.writeEndArray();
        }
        Map<String, Object> valueMap = impl.getValueMap();
        Set<Map.Entry<String, Object>> entrySet = valueMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            gen.writeObjectField(entry.getKey().toLowerCase(), entry.getValue());
        }
        gen.writeEndObject();
    }
}

