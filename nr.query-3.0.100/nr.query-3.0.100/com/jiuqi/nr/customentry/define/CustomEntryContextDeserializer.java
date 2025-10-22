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
import com.jiuqi.nr.customentry.define.CustomEntryContext;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomEntryContextDeserializer
extends JsonDeserializer<CustomEntryContext> {
    private static final Logger logger = LoggerFactory.getLogger(CustomEntryContextDeserializer.class);

    public CustomEntryContext deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        CustomEntryContext context = new CustomEntryContext();
        try {
            String block;
            JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
            JsonNode target = jNode.findValue("block");
            String string = block = target != null && !target.isNull() ? target.toString() : null;
            if (block != null) {
                context.parseBlockStr(block);
            }
            String datas = (target = jNode.findValue("datas")) != null && !target.isNull() ? target.toString() : null;
            context.parseDatasStr(datas);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)"CustomEntry", (String)"\u9519\u8bef", (String)ex.getMessage());
        }
        return context;
    }
}

