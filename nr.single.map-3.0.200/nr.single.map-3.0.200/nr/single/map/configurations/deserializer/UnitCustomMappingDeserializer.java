/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package nr.single.map.configurations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import nr.single.map.configurations.bean.UnitCustomMapping;

public class UnitCustomMappingDeserializer
extends JsonDeserializer<UnitCustomMapping> {
    public UnitCustomMapping deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        UnitCustomMapping customMapping = new UnitCustomMapping();
        JsonNode target = jNode.get("netUnitKey");
        String netKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("netUnitCode");
        String netCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("netUnitName");
        String netName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("bblx");
        String bblx = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("singleUnitCode");
        String singleCode = target != null && !target.isNull() ? target.asText() : null;
        customMapping.setNetUnitKey(netKey);
        customMapping.setNetUnitCode(netCode);
        customMapping.setNetUnitName(netName);
        customMapping.setBblx(bblx);
        customMapping.setSingleUnitCode(singleCode);
        return customMapping;
    }
}

