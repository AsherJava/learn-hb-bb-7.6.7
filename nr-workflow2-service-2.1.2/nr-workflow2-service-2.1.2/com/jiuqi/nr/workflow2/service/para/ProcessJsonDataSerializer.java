/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.json.JSONObject;

public class ProcessJsonDataSerializer
extends JsonSerializer<JSONObject> {
    public void serialize(JSONObject jsonObject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (jsonObject != null) {
            jsonGenerator.writeObject((Object)jsonObject.toMap());
        }
    }
}

