/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.authz.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.authz.bean.RoleWebImpl;
import java.io.IOException;

public class RoleJsonSerializer
extends JsonSerializer<RoleWebImpl> {
    public void serialize(RoleWebImpl value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)value.getId());
        gen.writeStringField("code", value.getCode());
        gen.writeObjectField("parentId", (Object)value.getParentId());
        gen.writeStringField("groupFlag", String.valueOf(value.getGroupFlag()));
        gen.writeStringField("title", value.getTitle());
        gen.writeStringField("description", value.getDescription());
        gen.writeStringField("index", String.valueOf(value.getIndex()));
        gen.writeStringField("key", value.getKey());
        gen.writeEndObject();
    }
}

