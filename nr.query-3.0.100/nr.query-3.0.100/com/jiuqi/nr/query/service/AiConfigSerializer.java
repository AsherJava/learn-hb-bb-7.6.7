/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.service.AiConfigs;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AiConfigSerializer
extends JsonSerializer<AiConfigs> {
    private static final Logger logger = LoggerFactory.getLogger(AiConfigSerializer.class);

    AiConfigSerializer() {
    }

    public void serialize(AiConfigs cofig, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeStartObject();
            gen.writeObjectField("dimensions", cofig.dimensions);
            gen.writeObjectField("schemes", cofig.schemes);
            gen.writeObjectField("periodType", (Object)cofig.periodType);
            gen.writeEndObject();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}

