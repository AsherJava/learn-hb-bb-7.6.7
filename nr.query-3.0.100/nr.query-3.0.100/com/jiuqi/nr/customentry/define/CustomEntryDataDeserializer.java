/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.customentry.define;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.customentry.define.CustomEntryData;
import com.jiuqi.nr.customentry.define.CustomEntryType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomEntryDataDeserializer
extends JsonDeserializer<CustomEntryData> {
    private static final Logger logger = LoggerFactory.getLogger(CustomEntryDataDeserializer.class);

    public CustomEntryData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        CustomEntryData data = new CustomEntryData();
        try {
            String cells;
            JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
            JsonNode target = jNode.findValue("type");
            String type = target != null && !target.isNull() ? target.asText() : "MODIFY";
            data.setType(CustomEntryType.valueOf(type));
            target = jNode.findValue("cells");
            String string = cells = target != null && !target.isNull() ? target.toString() : null;
            if (cells != null) {
                data.parseCellsStr(cells);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)"CustomEntry", (String)"\u9519\u8bef", (String)ex.getMessage());
        }
        return data;
    }
}

