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
import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import java.io.IOException;

public class DataSetGroupSerializer
extends JsonSerializer<DataSetGroupDefine> {
    public void serialize(DataSetGroupDefine DataSetGroup, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)DataSetGroup.getId());
        gen.writeObjectField("title", (Object)DataSetGroup.getTitle());
        gen.writeObjectField("parent", (Object)DataSetGroup.getParent());
        gen.writeObjectField("order", (Object)DataSetGroup.getOrder());
        gen.writeObjectField("updatetime", (Object)DataSetGroup.getUpdatetime());
        gen.writeObjectField("creator", (Object)DataSetGroup.getCtreator());
        gen.writeEndObject();
    }
}

