/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm;
import java.io.IOException;

public class EstimationFormDeserializer
extends JsonDeserializer<EstimationForm> {
    public EstimationForm deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        EstimationForm form = new EstimationForm();
        JsonNode target = jNode.findValue("formId");
        form.setFormId(target != null && !target.isNull() ? target.asText() : null);
        target = jNode.findValue("formType");
        form.setFormType(target != null && !target.isNull() ? target.asText() : null);
        return form;
    }
}

