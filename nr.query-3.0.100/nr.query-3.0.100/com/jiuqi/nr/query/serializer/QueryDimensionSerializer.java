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
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import java.io.IOException;

public class QueryDimensionSerializer
extends JsonSerializer<QueryDimensionDefine> {
    public void serialize(QueryDimensionDefine define, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("blockid", (Object)define.getBlockId());
        gen.writeObjectField("dimensiontype", (Object)define.getDimensionType());
        gen.writeObjectField("dimensionextension", (Object)define.getDimensionExtension());
        gen.writeObjectField("ishidden", (Object)define.isHidden());
        gen.writeObjectField("showsumn", (Object)define.isShowSum());
        gen.writeObjectField("layouttype", (Object)define.getLayoutType());
        gen.writeObjectField("preposesum", (Object)define.isPreposeSum());
        if (define.getSelectItems() == null) {
            gen.writeObjectField("selectItems", define.getDefaultItems());
        } else {
            gen.writeObjectField("selectItems", define.getSelectItems());
        }
        gen.writeObjectField("defaultItems", define.getDefaultItems());
        gen.writeObjectField("selectfields", define.getSelectFields());
        gen.writeObjectField("dimensiontablekind", (Object)define.getTableKind());
        gen.writeObjectField("dimensionistree", (Object)define.getIstree());
        gen.writeObjectField("viewId", (Object)define.getViewId());
        gen.writeObjectField("periodType", (Object)define.getPeriodType());
        gen.writeObjectField("dimensiontitle", (Object)define.getTitle());
        gen.writeObjectField("itemScop", (Object)define.getItemScop());
        gen.writeObjectField("dimensionName", (Object)define.getDimensionName());
        gen.writeObjectField("isPeriodDim", (Object)define.isPeriodDim());
        gen.writeObjectField("formSchemeId", (Object)define.getFormSchemeId());
        gen.writeObjectField("taskId", (Object)define.getTaskId());
        gen.writeObjectField("links", define.getLinkes());
        gen.writeObjectField("defaultCondition", define.getDefaultCondition());
        gen.writeObjectField("selectCondition", define.getSelectCondition());
        gen.writeObjectField("extends", (Object)define.getExtend());
        if (null != define.getConditionTitle()) {
            gen.writeObjectField("conditionTitle", (Object)define.getConditionTitle());
        }
        gen.writeObjectField("fieldscop", (Object)define.getSelectScop());
        gen.writeObjectField("fieldKey", (Object)define.getFieldKey());
        gen.writeEndObject();
    }
}

