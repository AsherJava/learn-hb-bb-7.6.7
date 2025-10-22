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
package com.jiuqi.nr.analysisreport.biservice.bi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.analysisreport.biservice.bi.BiChartDefine;
import java.io.IOException;

public class BiChartDefineDeserializer
extends JsonDeserializer<BiChartDefine> {
    public BiChartDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        BiChartDefine bichart = new BiChartDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        bichart.setTitle(title);
        target = jNode.findValue("guid");
        String guid = target != null && !target.isNull() ? target.asText() : null;
        bichart.setGuid(guid);
        target = jNode.findValue("securityLevel");
        String securityLevel = target != null && !target.isNull() ? target.asText() : null;
        bichart.setSecurityLevel(securityLevel);
        return bichart;
    }
}

