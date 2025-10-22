/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.batch.summary.storage.entity.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue;
import com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomCalibreRowDeserializer
extends JsonDeserializer<CustomCalibreRowDefine> {
    private static final String KEY = "key";
    private static final String CODE = "code";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String EXPRESSION = "expression";
    private static final String PARENT_CODE = "parentCode";

    public CustomCalibreRowDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        CustomCalibreRowDefine ccRow = new CustomCalibreRowDefine();
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue(KEY);
        ccRow.setKey(target != null && !target.isNull() ? target.asText() : null);
        target = jNode.findValue(CODE);
        ccRow.setCode(target != null && !target.isNull() ? target.asText() : null);
        target = jNode.findValue(TITLE);
        ccRow.setTitle(target != null && !target.isNull() ? target.asText() : null);
        target = jNode.findValue(PARENT_CODE);
        ccRow.setParentCode(target != null && !target.isNull() ? target.asText() : null);
        ccRow.setValue(this.getCalibreValue(mapper, jNode));
        return ccRow;
    }

    private CustomCalibreValue getCalibreValue(ObjectMapper mapper, JsonNode jNode) throws JsonProcessingException {
        CustomCalibreValue calibreValue = new CustomCalibreValue();
        JsonNode target = jNode.findValue(TYPE);
        Integer type = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        ConditionValueType valueType = ConditionValueType.valueOf(type);
        if (valueType != null) {
            calibreValue.setValueType(valueType);
            switch (valueType) {
                case EXPRESSION: {
                    calibreValue.setExpression(this.getExpression(jNode));
                    break;
                }
                case UNITS: {
                    calibreValue.setCheckList(this.getCheckUnits(mapper, jNode));
                }
            }
        }
        return calibreValue;
    }

    private String getExpression(JsonNode jNode) {
        JsonNode target = jNode.findValue(EXPRESSION);
        return target != null && !target.isNull() ? target.asText() : null;
    }

    private List<String> getCheckUnits(ObjectMapper mapper, JsonNode jNode) {
        ArrayList<String> units = null;
        JsonNode target = jNode.findValue(EXPRESSION);
        if (target != null && target.isArray()) {
            units = new ArrayList<String>();
            ArrayNode arrNode = (ArrayNode)target;
            for (int i = 0; i < arrNode.size(); ++i) {
                JsonNode node = arrNode.get(i);
                if (node == null || node.isNull()) continue;
                units.add(node.asText());
            }
        }
        return units;
    }
}

