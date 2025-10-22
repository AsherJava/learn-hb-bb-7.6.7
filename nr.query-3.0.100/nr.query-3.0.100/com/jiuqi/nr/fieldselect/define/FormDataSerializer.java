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
import com.jiuqi.nr.fieldselect.define.FormData;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormDataSerializer
extends JsonSerializer<FormData> {
    private static final Logger log = LoggerFactory.getLogger(FormDataSerializer.class);

    public void serialize(FormData data, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeStartObject();
            gen.writeObjectField("id", (Object)data.getId());
            gen.writeObjectField("code", (Object)data.getCode());
            gen.writeObjectField("title", (Object)data.getTitle());
            gen.writeObjectField("serialnumber", (Object)data.getSerialNum());
            gen.writeEndObject();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}

