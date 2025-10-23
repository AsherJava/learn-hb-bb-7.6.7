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
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProcessRangeDimsDeserializer
extends JsonDeserializer<Set<ProcessRangeDims>> {
    public Set<ProcessRangeDims> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper)jsonParser.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(jsonParser);
        HashSet<ProcessRangeDims> dims = new HashSet<ProcessRangeDims>();
        if (node != null && node.isArray()) {
            for (JsonNode element : node) {
                ProcessRangeDims processRangeDims = (ProcessRangeDims)mapper.treeToValue((TreeNode)element, ProcessRangeDims.class);
                dims.add(processRangeDims);
            }
        }
        return dims;
    }
}

