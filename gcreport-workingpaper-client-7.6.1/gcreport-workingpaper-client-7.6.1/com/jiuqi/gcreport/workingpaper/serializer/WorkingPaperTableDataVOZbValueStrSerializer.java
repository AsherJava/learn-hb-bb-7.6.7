/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.gcreport.workingpaper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkingPaperTableDataVOZbValueStrSerializer
extends JsonSerializer<Map<String, String>> {
    private static final Logger logger = LoggerFactory.getLogger(WorkingPaperTableDataVOZbValueStrSerializer.class);

    public void serialize(Map<String, String> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(null);
        for (Map.Entry<String, String> entry : value.entrySet()) {
            try {
                gen.writeStringField(entry.getKey(), null == entry.getValue() ? "" : entry.getValue());
            }
            catch (Exception e) {
                logger.debug("ERROR:entry not found:entry.getKey()=" + entry.getKey() + ";entry.getKey()=" + entry.getKey());
            }
        }
    }
}

