/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  org.json.JSONObject
 */
package com.jiuqi.nr.itreebase.context;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.json.JSONObject;

public class ITreeVariableDataSerializer
extends JsonSerializer<JSONObject> {
    public void serialize(JSONObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null && !value.isEmpty()) {
            gen.writeObject((Object)value.toMap());
        }
    }
}

