/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.dataset.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.dataset.defines.DataSetDefine;
import java.io.IOException;

public class DataSetSerializer
extends JsonSerializer<DataSetDefine> {
    public void serialize(DataSetDefine DataSet, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)DataSet.getId());
        gen.writeObjectField("name", (Object)DataSet.getName());
        gen.writeObjectField("title", (Object)DataSet.getTitle());
        gen.writeObjectField("parent", (Object)DataSet.getParent());
        gen.writeObjectField("type", (Object)DataSet.getType());
        gen.writeObjectField("order", (Object)DataSet.getOrder());
        gen.writeObjectField("model", (Object)DataSet.getModel());
        gen.writeObjectField("updatetime", (Object)DataSet.getUpdatetime());
        gen.writeObjectField("creator", (Object)DataSet.getCreator());
        gen.writeEndObject();
    }
}

