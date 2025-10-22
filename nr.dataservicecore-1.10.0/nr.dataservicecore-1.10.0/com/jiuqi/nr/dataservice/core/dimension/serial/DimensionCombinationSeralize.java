/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.dataservice.core.dimension.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.serial.SerialzeUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public class DimensionCombinationSeralize
extends JsonSerializer<DimensionCombination> {
    public void serialize(DimensionCombination dimensionCombination, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("FV");
        gen.writeStartArray();
        Collection<String> names = dimensionCombination.getNames();
        int idxDW = -1;
        FixedDimensionValue dwDimensionValue = dimensionCombination.getDWDimensionValue();
        int idx = -1;
        ObjectMapper mapper = SerialzeUtil.getObjectMapper();
        for (String name : names) {
            gen.writeStartObject();
            ++idx;
            FixedDimensionValue dimensionValue = dimensionCombination.getFixedDimensionValue(name);
            if (idxDW == -1 && dwDimensionValue != null && dimensionValue.getName().equals(dwDimensionValue.getName())) {
                idxDW = idx;
            }
            gen.writeStringField("N", dimensionValue.getName());
            if (dimensionValue.getValue() instanceof Date) {
                gen.writeStringField("V", "##DATE##" + mapper.writeValueAsString(dimensionValue.getValue()));
            } else {
                gen.writeStringField("V", mapper.writeValueAsString(dimensionValue.getValue()));
            }
            gen.writeStringField("E", dimensionValue.getEntityID());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeNumberField("FD", idxDW);
        gen.writeEndObject();
    }
}

