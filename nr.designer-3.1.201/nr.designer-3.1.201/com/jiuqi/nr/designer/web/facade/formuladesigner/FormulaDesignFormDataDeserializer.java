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
package com.jiuqi.nr.designer.web.facade.formuladesigner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import java.io.IOException;

public class FormulaDesignFormDataDeserializer
extends JsonDeserializer<FormulaDesignFormData> {
    public FormulaDesignFormData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        FormulaDesignFormData formData = new FormulaDesignFormData();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("formkey");
        String formKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("links");
        String links = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("regions");
        String regions = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("enums");
        String enums = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("field");
        String field = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("LINEPROPS");
        String lineprops = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("griddata");
        byte[] grid = target != null && !target.isNull() ? target.asText().getBytes() : null;
        formData.setFormKey(formKey);
        formData.setLinks(links);
        formData.setGridData(grid);
        formData.setRegions(regions);
        formData.setEnums(enums);
        formData.setField(field);
        formData.setPropList(lineprops);
        return formData;
    }
}

