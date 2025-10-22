/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.fieldselect.define;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormData;
import java.io.IOException;

public class FieldSelectFormDataSerializer
extends JsonSerializer<FieldSelectFormData> {
    public void serialize(FieldSelectFormData formData, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("formkey", (Object)formData.getFormKey());
        String data = new String(formData.getGridData());
        gen.writeObjectField("griddata", (Object)data);
        gen.writeObjectField("links", formData.getLinks());
        gen.writeObjectField("regions", formData.getRegions());
        gen.writeEndObject();
    }
}

