/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.message.manager;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.message.manager.Constants;
import java.io.IOException;
import java.time.Instant;

@Deprecated
public class InstantJsonSerializer
extends JsonSerializer<Instant> {
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = Constants.FMT.format(value);
        gen.writeString(str);
    }
}

