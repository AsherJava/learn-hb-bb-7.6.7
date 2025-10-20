/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.designer.web.facade.formuladesigner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import java.io.IOException;

public class FormulaDesignFormDataSerializer
extends JsonSerializer<FormulaDesignFormData> {
    public void serialize(FormulaDesignFormData formData, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("formkey", (Object)formData.getFormKey());
        String data = new String(formData.getGridData());
        gen.writeObjectField("griddata", (Object)data);
        gen.writeObjectField("links", formData.getLinks());
        gen.writeObjectField("regions", formData.getRegions());
        gen.writeObjectField("enums", formData.getEnums());
        gen.writeObjectField("field", (Object)formData.getField());
        gen.writeObjectField("LINEPROPS", formData.getPropList());
        gen.writeEndObject();
    }
}

