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
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.block.DimensionItemScop;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.common.QueryLayoutType;
import java.io.IOException;
import org.springframework.util.StringUtils;

public class QueryDimensionDeserializer
extends JsonDeserializer<QueryDimensionDefine> {
    public QueryDimensionDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryDimensionDefine define = new QueryDimensionDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("blockid");
        String blockid = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dimensiontype");
        String dimensiontype = target != null && !target.isNull() ? target.asText() : "qdt_Null";
        target = jNode.findValue("dimensionextension");
        String dimensionextension = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("ishidden");
        Boolean ishidden = target != null && !target.isNull() ? Boolean.valueOf(target.asText()) : false;
        target = jNode.findValue("showsumn");
        Boolean isShowSumn = target != null && !target.isNull() ? Boolean.valueOf(target.asText()) : true;
        target = jNode.findValue("layouttype");
        String layouttype = target != null && !target.isNull() ? target.asText() : "LYT_COL";
        target = jNode.findValue("preposesum");
        Boolean preposesum = target != null && !target.isNull() ? Boolean.valueOf(target.asText()) : true;
        target = jNode.findValue("selectItems");
        String selectItems = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("selectfields");
        String selectFields = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("viewId");
        String viewId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dimensiontablekind");
        String tablekind = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dimensionistree");
        String istree = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("periodType");
        String periodType = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dimensiontitle");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("itemScop");
        String itemScop = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("defaultItems");
        String defaultItems = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("dimensionName");
        String dimensionName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isPeriodDim");
        Boolean isPeriodDim = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("links");
        String linkes = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("taskId");
        String taskId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("formSchemeId");
        String formSchemeId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("selectCondition");
        String selectCondition = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("defaultCondition");
        String defaultCondition = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("extends");
        String extendsval = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("conditionTitle");
        String conditionTitle = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldscop");
        String selectScop = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldKey");
        String fieldKey = target != null && !target.isNull() ? target.asText() : null;
        define.setSelectScop(selectScop);
        define.setExtend(extendsval);
        define.setBlockId(blockid);
        QueryDimensionType type = QueryDimensionType.valueOf(dimensiontype);
        define.setDimensionType(type);
        define.setDimensionExtension(dimensionextension);
        define.setHidden(ishidden);
        define.setShowSum(isShowSumn);
        define.setLayoutType(QueryLayoutType.valueOf(layouttype));
        define.setPreposeSum(preposesum);
        define.setTableKind(tablekind);
        define.setIstree(istree);
        define.setPeriodType(periodType);
        define.setTitle(title);
        if (type != QueryDimensionType.QDT_FIELD) {
            define.setDimensionName(dimensionName);
        }
        define.setIsPeriodDim(isPeriodDim);
        define.setLinkes(linkes);
        define.setTaskId(taskId);
        define.setFormSchemeId(formSchemeId);
        define.setItemScop(itemScop == null ? DimensionItemScop.DIS_DEFAULT : DimensionItemScop.valueOf(itemScop));
        if (selectItems != null) {
            define.setSelectItems(selectItems);
        }
        if (selectFields != null && !selectFields.equals("[]")) {
            define.setSelectFields(selectFields);
        }
        if (viewId != null && viewId != "") {
            define.setViewId(viewId);
        }
        if (defaultItems != null) {
            define.setDefaultItems(defaultItems);
            if (selectItems == null || selectItems.indexOf("{") < 0) {
                define.setSelectItems(defaultItems);
            }
        }
        if (!StringUtils.isEmpty(selectCondition) && !selectCondition.equals("{}")) {
            define.setSelectCondition(selectCondition);
        }
        if (!StringUtils.isEmpty(defaultCondition) && !defaultCondition.equals("{}")) {
            define.setDefaultCondition(defaultCondition);
        }
        if (conditionTitle != null) {
            define.setConditionTitle(conditionTitle);
        }
        define.setFieldKey(fieldKey);
        return define;
    }
}

