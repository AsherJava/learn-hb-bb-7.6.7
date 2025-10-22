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
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.internal.bean.HandleSavePojo;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleSavePojoDeserializer
extends JsonDeserializer<HandleSavePojo> {
    private static final Logger logger = LoggerFactory.getLogger(HandleSavePojoDeserializer.class);

    public HandleSavePojo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String formCode;
        ArrayNode arr;
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        HandleSavePojo pojo = new HandleSavePojo();
        JsonNode target = jNode.get("zbFields");
        if (target != null && !target.isNull()) {
            ArrayList<SingleFileFieldInfoImpl> zbInfos = new ArrayList<SingleFileFieldInfoImpl>();
            pojo.setZbFields(zbInfos);
            arr = (ArrayNode)target;
            arr.forEach(zb -> {
                try {
                    SingleFileFieldInfoImpl info = (SingleFileFieldInfoImpl)ctxt.readValue(zb.traverse(p.getCodec()), SingleFileFieldInfoImpl.class);
                    zbInfos.add(info);
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        if ((target = jNode.get("formulaInfos")) != null && !target.isNull()) {
            ArrayList<SingleFileFormulaItemImpl> formulas = new ArrayList<SingleFileFormulaItemImpl>();
            pojo.setFormulaInfos(formulas);
            arr = (ArrayNode)target;
            arr.forEach(formula -> {
                try {
                    SingleFileFormulaItemImpl info = (SingleFileFormulaItemImpl)ctxt.readValue(formula.traverse(p.getCodec()), SingleFileFormulaItemImpl.class);
                    formulas.add(info);
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        if ((target = jNode.get("mapRule")) != null && !target.isNull()) {
            ArrayList<RuleMap> mapRule = new ArrayList<RuleMap>();
            pojo.setMapRule(mapRule);
            arr = (ArrayNode)target;
            arr.forEach(rule -> {
                try {
                    RuleMap ruleMap = (RuleMap)ctxt.readValue(rule.traverse(p.getCodec()), RuleMap.class);
                    mapRule.add(ruleMap);
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        if ((target = jNode.get("mapping")) != null && !target.isNull()) {
            UnitMapping mapping = (UnitMapping)mapper.readValue(target.traverse(p.getCodec()), UnitMapping.class);
            pojo.setMapping(mapping);
        }
        if ((target = jNode.get("config")) != null && !target.isNull()) {
            MappingConfig config = (MappingConfig)mapper.readValue(target.traverse(p.getCodec()), MappingConfig.class);
            pojo.setConfig(config);
        }
        String configKey = (target = jNode.get("mappingConfigKey")) != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("formulaSchemeKey");
        String schemeKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.get("formCode");
        String string = formCode = target != null && !target.isNull() ? target.asText() : null;
        if (!"".equals(configKey) && configKey != null) {
            pojo.setMappingConfigKey(configKey);
        }
        if (!"".equals(schemeKey) && schemeKey != null) {
            pojo.setFormulaSchemeKey(schemeKey);
        }
        pojo.setFormCode(formCode);
        return pojo;
    }
}

