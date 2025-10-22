/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.dataservice.core.dimension.serial;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.serial.SerialzeUtil;
import java.io.IOException;
import java.util.Date;

public class DimensionCombinationDeserialize
extends JsonDeserializer<DimensionCombination> {
    public DimensionCombination deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = SerialzeUtil.getObjectMapper();
        JsonNode node = (JsonNode)mapper.readTree(p);
        JsonNode jsonNode = node.get("FV");
        int dwIdx = node.get("FD").asInt();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        if (jsonNode.isArray()) {
            ArrayNode valueArray = (ArrayNode)jsonNode;
            for (int i = 0; i < valueArray.size(); ++i) {
                JsonNode jsonNode1 = valueArray.get(i);
                String name = jsonNode1.get("N").asText();
                String entityID = jsonNode1.get("E").asText();
                String value = jsonNode1.get("V").textValue();
                if (dwIdx == i) {
                    if (value != null && value.startsWith("##DATE##")) {
                        value = value.substring("##DATE##".length());
                        builder.setDWValue(new FixedDimensionValue(name, entityID, mapper.readValue(value, Date.class)));
                        continue;
                    }
                    builder.setDWValue(new FixedDimensionValue(name, entityID, mapper.readValue(value, Object.class)));
                    continue;
                }
                if (value != null && value.startsWith("##DATE##")) {
                    value = value.substring("##DATE##".length());
                    builder.setValue(new FixedDimensionValue(name, entityID, mapper.readValue(value, Date.class)));
                    continue;
                }
                builder.setValue(new FixedDimensionValue(name, entityID, mapper.readValue(value, Object.class)));
            }
        }
        return builder.getCombination();
    }
}

