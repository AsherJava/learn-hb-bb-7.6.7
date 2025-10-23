/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.ObjectCodec
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import java.io.IOException;

public class BusinessObjectDeserializer
extends JsonDeserializer<IBusinessObject> {
    private final ObjectMapper mapper = new ObjectMapper();

    public IBusinessObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec oc = p.getCodec();
        JsonNode rootNode = (JsonNode)oc.readTree(p);
        JsonNode dimensionNode = rootNode.get("dimensions");
        DimensionCombination dimensions = (DimensionCombination)this.mapper.readValue(dimensionNode.toPrettyString(), DimensionCombination.class);
        JsonNode formKeyNode = rootNode.get("formKey");
        if (formKeyNode != null) {
            return new FormObject(dimensions, formKeyNode.asText());
        }
        JsonNode formGroupKeyNode = rootNode.get("formGroupKey");
        if (formGroupKeyNode != null) {
            return new FormGroupObject(dimensions, formGroupKeyNode.asText());
        }
        return new DimensionObject(dimensions);
    }
}

