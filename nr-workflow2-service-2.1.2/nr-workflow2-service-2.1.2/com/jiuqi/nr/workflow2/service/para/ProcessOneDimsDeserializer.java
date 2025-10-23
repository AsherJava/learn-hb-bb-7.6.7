/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.TreeNode
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProcessOneDimsDeserializer
extends JsonDeserializer<Set<ProcessOneDim>> {
    public Set<ProcessOneDim> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper)jsonParser.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(jsonParser);
        HashSet<ProcessOneDim> dims = new HashSet<ProcessOneDim>();
        if (node != null && node.isArray()) {
            for (JsonNode element : node) {
                ProcessOneDim processRangeDims = (ProcessOneDim)mapper.treeToValue((TreeNode)element, ProcessOneDim.class);
                dims.add(processRangeDims);
            }
        }
        return dims;
    }
}

