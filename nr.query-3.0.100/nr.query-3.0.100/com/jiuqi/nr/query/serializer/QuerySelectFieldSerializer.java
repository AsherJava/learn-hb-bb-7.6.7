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
import com.jiuqi.nr.query.block.QuerySelectField;
import java.io.IOException;

public class QuerySelectFieldSerializer
extends JsonSerializer<QuerySelectField> {
    public void serialize(QuerySelectField selectField, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("fieldType", (Object)selectField.getFiledType());
        gen.writeObjectField("gatherType", (Object)selectField.getGatherType());
        gen.writeObjectField("showFormat", (Object)selectField.getShowFormat());
        gen.writeObjectField("ishidden", (Object)selectField.isHidden());
        gen.writeObjectField("preWarns", selectField.getPreWarns());
        gen.writeObjectField("statisticsFields", (Object)selectField.getStatisticsFieldsStr());
        gen.writeObjectField("fileExtension", (Object)selectField.getFileExtension());
        gen.writeObjectField("code", (Object)selectField.getCode());
        gen.writeObjectField("title", (Object)selectField.getTitle());
        gen.writeObjectField("ismaster", (Object)selectField.getIsMaster());
        gen.writeObjectField("regionkey", (Object)selectField.getRegionKey());
        gen.writeObjectField("sort", (Object)selectField.getSort());
        gen.writeObjectField("issorted", (Object)selectField.getIsSorted());
        gen.writeObjectField("links", selectField.getLinkes());
        gen.writeObjectField("isgroupfield", (Object)selectField.getIsGroupField());
        gen.writeObjectField("isSelectByField", (Object)selectField.isSelectByField());
        gen.writeObjectField("isDeleted", (Object)selectField.getIsDeleted());
        gen.writeObjectField("tableName", (Object)selectField.getTableName());
        gen.writeObjectField("taskId", (Object)selectField.getTaskId());
        gen.writeObjectField("formSchemeId", (Object)selectField.getFormSchemeId());
        gen.writeObjectField("iscustom", (Object)selectField.getCustom());
        gen.writeObjectField("customvalue", (Object)selectField.getCustomValue());
        gen.writeObjectField("dimensionName", (Object)selectField.getDimensionName());
        gen.writeObjectField("isusershow", (Object)selectField.getIsUserShow());
        gen.writeObjectField("dotnum", (Object)selectField.getDotNum());
        gen.writeObjectField("formkey", (Object)selectField.getFormKey());
        gen.writeObjectField("datalink", (Object)selectField.getDataLink());
        gen.writeObjectField("isMergeSameCell", (Object)selectField.getIsMergeSameCell());
        gen.writeObjectField("regionKind", (Object)selectField.getRegionKind());
        gen.writeObjectField("tableKey", (Object)selectField.getTableKey());
        gen.writeObjectField("dataSheet", (Object)selectField.getDataSheet());
        gen.writeEndObject();
    }
}

