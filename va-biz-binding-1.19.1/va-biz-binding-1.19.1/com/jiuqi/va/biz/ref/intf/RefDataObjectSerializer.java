/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.va.biz.ref.intf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.va.biz.ref.intf.RefDataObject;
import java.io.IOException;

public class RefDataObjectSerializer
extends JsonSerializer<RefDataObject> {
    public void serialize(RefDataObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.isNull()) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            gen.writeObjectField("name", (Object)value.getName());
            gen.writeObjectField("title", (Object)value.getTitle());
            String showTitle = value.getShowTitle();
            if (showTitle != null) {
                gen.writeObjectField("showTitle", (Object)showTitle);
            }
            gen.writeEndObject();
        }
    }
}

