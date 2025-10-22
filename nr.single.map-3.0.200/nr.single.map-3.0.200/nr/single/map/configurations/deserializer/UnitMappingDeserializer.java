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
import java.util.Iterator;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitMappingDeserializer
extends JsonDeserializer<UnitMapping> {
    private static final Logger logger = LoggerFactory.getLogger(UnitMappingDeserializer.class);

    public UnitMapping deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        UnitMapping mapping = new UnitMapping();
        JsonNode target = jNode.get("unitInfos");
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            ArrayList<UnitCustomMapping> unitInfos = new ArrayList<UnitCustomMapping>();
            arr.forEach(e -> {
                try {
                    UnitCustomMapping info = (UnitCustomMapping)mapper.readValue(e.traverse(p.getCodec()), UnitCustomMapping.class);
                    unitInfos.add(info);
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage(), e1);
                }
            });
            mapping.setUnitInfos(unitInfos);
        }
        if ((target = jNode.get("periodMapping")) != null && !target.isNull()) {
            Iterator iterator = target.fieldNames();
            while (iterator.hasNext()) {
                String fieldName = (String)iterator.next();
                String value = target.get(fieldName).asText();
                mapping.addPeriod(fieldName, value);
            }
        }
        return mapping;
    }
}

