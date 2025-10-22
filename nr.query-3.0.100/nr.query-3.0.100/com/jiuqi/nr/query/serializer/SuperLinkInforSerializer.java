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
import com.jiuqi.nr.query.block.SuperLinkInfor;
import java.io.IOException;

public class SuperLinkInforSerializer
extends JsonSerializer<SuperLinkInfor> {
    public void serialize(SuperLinkInfor infor, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("type", (Object)infor.getLinkType());
        gen.writeObjectField("openmode", (Object)infor.getOpenMode());
        gen.writeObjectField("target", (Object)infor.getTarget());
        gen.writeObjectField("param", (Object)infor.getParametersStr());
        gen.writeObjectField("linkname", (Object)infor.getLinkName());
        gen.writeEndObject();
    }
}

