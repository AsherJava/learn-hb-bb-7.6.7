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
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;

public class RuleMapDeserializer
extends JsonDeserializer<RuleMap> {
    public RuleMap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        RuleMap rule = new RuleMap();
        JsonNode target = jNode.get("rule");
        if (target != null && !target.isNull()) {
            RuleKind kind = (RuleKind)((Object)mapper.readValue(target.traverse(p.getCodec()), RuleKind.class));
            rule.setRule(kind);
        }
        String singleCode = (target = jNode.get("singleCode")) != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("netCode");
        String netCode = target != null && !target.isNull() ? target.asText() : null;
        rule.setSingleCode(singleCode);
        rule.setNetCode(netCode);
        return rule;
    }
}

