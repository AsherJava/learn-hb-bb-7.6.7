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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkingPaperTableDataVOZbValueSerializer
extends JsonSerializer<Map<String, BigDecimal>> {
    private static final Logger logger = LoggerFactory.getLogger(WorkingPaperTableDataVOZbValueSerializer.class);

    public void serialize(Map<String, BigDecimal> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        gen.writeObject(null);
        for (Map.Entry<String, BigDecimal> entry : value.entrySet()) {
            try {
                gen.writeStringField(entry.getKey(), null == entry.getValue() ? "" : (BigDecimal.ZERO.compareTo(entry.getValue()) == 0 ? "0.00" : df.format(entry.getValue())));
            }
            catch (Exception e) {
                logger.debug("ERROR:entry not found:entry.getKey()=" + entry.getKey() + ";entry.getKey()=" + entry.getKey());
            }
        }
    }
}

