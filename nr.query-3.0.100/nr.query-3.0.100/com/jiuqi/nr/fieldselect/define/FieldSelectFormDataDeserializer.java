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
package com.jiuqi.nr.fieldselect.define;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormData;
import java.io.IOException;

public class FieldSelectFormDataDeserializer
extends JsonDeserializer<FieldSelectFormData> {
    public FieldSelectFormData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        FieldSelectFormData formData = new FieldSelectFormData();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("formkey");
        String formKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("regions");
        String regions = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("links");
        String links = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("griddata");
        byte[] grid = target != null && !target.isNull() ? target.asText().getBytes() : null;
        formData.setFormKey(formKey);
        formData.setLinks(links);
        formData.setRegions(regions);
        formData.setGridData(grid);
        return formData;
    }
}

