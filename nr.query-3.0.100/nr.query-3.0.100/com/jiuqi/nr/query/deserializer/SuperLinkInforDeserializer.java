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
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.block.LinkOpenMode;
import com.jiuqi.nr.query.block.LinkType;
import com.jiuqi.nr.query.block.SuperLinkInfor;
import java.io.IOException;

public class SuperLinkInforDeserializer
extends JsonDeserializer<SuperLinkInfor> {
    public SuperLinkInfor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SuperLinkInfor linkInfor = new SuperLinkInfor();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("type");
        LinkType linkType = target != null && !target.isNull() ? LinkType.valueOf(target.asText()) : null;
        target = jNode.findValue("openmode");
        LinkOpenMode openMode = target != null && !target.isNull() ? LinkOpenMode.valueOf(target.asText()) : null;
        target = jNode.findValue("param");
        String params = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("target");
        String url = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("linkname");
        String linkName = target != null && !target.isNull() ? target.asText() : null;
        linkInfor.setLinkType(linkType);
        linkInfor.setOpenMode(openMode);
        linkInfor.setParameters(params);
        linkInfor.setTarget(url);
        linkInfor.setLinkName(linkName);
        return linkInfor;
    }
}

