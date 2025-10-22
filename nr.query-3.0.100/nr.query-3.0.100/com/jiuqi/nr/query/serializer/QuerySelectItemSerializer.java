/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.block.QuerySelectItem;
import java.io.IOException;

public class QuerySelectItemSerializer
extends JsonSerializer<QuerySelectItem> {
    public void serialize(QuerySelectItem selectItem, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("demesionId", (Object)selectItem.getDemesionId());
        gen.writeObjectField("code", (Object)selectItem.getCode());
        gen.writeObjectField("title", (Object)selectItem.getTitle());
        gen.writeObjectField("isorder", (Object)selectItem.isOrder());
        gen.writeObjectField("ordertype", (Object)selectItem.getOrderType().getName());
        gen.writeObjectField("iscustom", (Object)selectItem.getCustom());
        gen.writeObjectField("customvalue", (Object)selectItem.getCustomValue());
        gen.writeObjectField("order", (Object)selectItem.getOrder());
        gen.writeObjectField("sort", (Object)selectItem.getSort());
        gen.writeObjectField("issorted", (Object)selectItem.getIsSorted());
        gen.writeObjectField("parentpath", (Object)selectItem.getParentPath());
        gen.writeEndObject();
    }
}

