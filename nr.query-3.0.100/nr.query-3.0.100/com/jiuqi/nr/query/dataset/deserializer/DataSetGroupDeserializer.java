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
import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import java.io.IOException;

public class DataSetGroupDeserializer
extends JsonDeserializer<DataSetGroupDefine> {
    public DataSetGroupDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DataSetGroupDefine dataSetGroup = new DataSetGroupDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("parent");
        String parent = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("order");
        String order = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("creator");
        String creator = target != null && !target.isNull() ? target.asText() : null;
        dataSetGroup.setId(id);
        dataSetGroup.setOrder(order);
        dataSetGroup.setParent(parent);
        dataSetGroup.setTitle(title);
        dataSetGroup.setCreator(creator);
        return dataSetGroup;
    }
}

