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
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package nr.single.map.configurations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import nr.single.map.configurations.bean.SkipUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkipUnitDeserializer
extends JsonDeserializer<SkipUnit> {
    private static final Logger logger = LoggerFactory.getLogger(SkipUnitDeserializer.class);

    public SkipUnit deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        SkipUnit unit = new SkipUnit();
        JsonNode target = jNode.get("unitKey");
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            ArrayList<String> ari = new ArrayList<String>();
            arr.forEach(e -> {
                try {
                    String unitKey = (String)mapper.readValue(e.traverse(p.getCodec()), String.class);
                    ari.add(unitKey);
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage(), e1);
                }
            });
            unit.setUnitKey(ari);
        }
        String formula = (target = jNode.get("formula")) != null && !target.isNull() ? target.asText() : null;
        unit.setFormula(formula);
        return unit;
    }
}

