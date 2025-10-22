/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.query.dataset.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.dataset.defines.DataSetDefine;
import java.io.IOException;

public class DataSetDefineDeserializer
extends JsonDeserializer<DataSetDefine> {
    public DataSetDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DataSetDefine dataSet = new DataSetDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("name");
        String name = target != null && !target.isNull() ? target.asText().toUpperCase() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("parent");
        String parent = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("order");
        String order = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("model");
        String model = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("type");
        String type = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("creator");
        String creator = target != null && !target.isNull() ? target.asText() : null;
        dataSet.setId(id);
        dataSet.setModel(model);
        dataSet.setName(name);
        dataSet.setOrder(order);
        dataSet.setParent(parent);
        dataSet.setTitle(title);
        dataSet.setType(type);
        dataSet.setCreator(creator);
        return dataSet;
    }
}

