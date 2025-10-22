/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.formulapenetration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.formulapenetration.defines.FieldObject;
import java.io.IOException;

public class FieldObjectSerializer
extends JsonSerializer<FieldObject> {
    public void serialize(FieldObject fieldObject, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("code", (Object)fieldObject.getCode());
        gen.writeObjectField("title", (Object)fieldObject.getTitle());
        gen.writeObjectField("formSchemeId", (Object)fieldObject.getFormSchemeId());
        gen.writeObjectField("iscustom", (Object)fieldObject.getCustom());
        gen.writeObjectField("customvalue", (Object)fieldObject.getCustomValue());
        gen.writeObjectField("formkey", (Object)fieldObject.getFormKey());
        gen.writeObjectField("datalink", (Object)fieldObject.getDataLink());
        gen.writeObjectField("reportName", (Object)fieldObject.getReportName());
        gen.writeEndObject();
    }
}

