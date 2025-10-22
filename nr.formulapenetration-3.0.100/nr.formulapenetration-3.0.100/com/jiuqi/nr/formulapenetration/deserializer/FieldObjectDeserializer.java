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
package com.jiuqi.nr.formulapenetration.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.formulapenetration.defines.FieldObject;
import java.io.IOException;

public class FieldObjectDeserializer
extends JsonDeserializer {
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        FieldObject fieldObject = new FieldObject();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("formSchemeId");
        String formSchemeId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("iscustom");
        boolean isCustom = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("customvalue");
        String customValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("formkey");
        String formKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("datalink");
        String dataLink = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("reportName");
        String reportName = target != null && !target.isNull() ? target.asText() : null;
        fieldObject.setReportName(reportName);
        fieldObject.setFormKey(formKey);
        fieldObject.setDataLink(dataLink);
        fieldObject.setTitle(title);
        fieldObject.setCode(code);
        fieldObject.setFormSchemeId(formSchemeId);
        fieldObject.setCustom(isCustom);
        fieldObject.setCustomValue(customValue);
        return fieldObject;
    }
}

