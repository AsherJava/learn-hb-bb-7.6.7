/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 */
package com.jiuqi.nr.datascheme.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jiuqi.nr.datascheme.common.Consts;
import java.io.IOException;
import java.time.Instant;

public class InstantJsonDeserializer
extends JsonDeserializer<Instant> {
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Instant.from(Consts.FMT.parse(p.getText()));
    }
}

