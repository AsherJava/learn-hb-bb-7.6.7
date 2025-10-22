/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.query.block.QuerySelectField;
import java.io.IOException;

public class QuerySelectFieldDeserializer
extends JsonDeserializer<QuerySelectField> {
    public QuerySelectField deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QuerySelectField selectField = new QuerySelectField();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("fieldType");
        String filedType = target != null && !target.isNull() ? target.asText() : "FIELD_TYPE_GENERAL";
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("gatherType");
        String gatherType = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("showFormat");
        String showFormat = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("ishidden");
        boolean isHidden = target != null && !target.isNull() ? Boolean.valueOf(Boolean.valueOf(target.asText())) : null;
        target = jNode.findValue("preWarns");
        String preWarns = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("statisticsFields");
        String statisticsFields = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fileExtension");
        String fileExtension = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("ismaster");
        String isMaster = target != null && !target.isNull() ? target.asText() : "";
        target = jNode.findValue("regionkey");
        String regionKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("sort");
        String sortinfor = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("issorted");
        boolean isSorted = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("links");
        String linkes = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("isgroupfield");
        boolean isGroupField = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("isSelectByField");
        boolean isSelectByField = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("isDeleted");
        boolean isDeleted = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("tableName");
        String tableName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("formSchemeId");
        String formSchemeId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("taskId");
        String taskId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("iscustom");
        boolean isCustom = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("customvalue");
        String customValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dimensionName");
        String dimensioinName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isusershow");
        boolean isUserShow = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("dotnum");
        int dotNUm = target != null && !target.isNull() ? target.asInt() : 2;
        target = jNode.findValue("formkey");
        String formKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("datalink");
        String dataLink = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isMergeSameCell");
        boolean isMergeSameCell = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("regionKind");
        String regionKind = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("tableKey");
        String tableKey = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dataSheet");
        String dataSheet = target != null && !target.isNull() ? target.asText() : null;
        selectField.setDotNum(dotNUm);
        selectField.setFormKey(formKey);
        selectField.setDataLink(dataLink);
        if (regionKind != null) {
            selectField.setRegionKind(DataRegionKind.valueOf((String)regionKind));
        }
        selectField.setIsUserShow(isUserShow);
        selectField.setDimensionName(dimensioinName);
        selectField.setIsDeleted(isDeleted);
        selectField.setFiledType(FieldType.valueOf((String)filedType));
        if (!StringUtils.isEmpty((String)gatherType)) {
            selectField.setGatherType(FieldGatherType.valueOf((String)gatherType));
        }
        selectField.setShowFormat(showFormat);
        selectField.setHidden(isHidden);
        selectField.setPreWarnsStr(preWarns);
        selectField.setFileExtension(fileExtension);
        selectField.setTitle(title);
        selectField.setCode(code);
        selectField.setIsMaster(isMaster);
        selectField.setRegionKey(regionKey);
        selectField.setIsSorted(isSorted);
        selectField.setSort(sortinfor);
        selectField.setLinkes(linkes);
        selectField.setIsGroupField(isGroupField);
        selectField.setSelectByField(isSelectByField);
        selectField.setTableName(tableName);
        selectField.setStatisticsFieldsStr(statisticsFields);
        selectField.setFormSchemeId(formSchemeId);
        selectField.setTaskId(taskId);
        selectField.setCustom(isCustom);
        selectField.setCustomValue(customValue);
        selectField.setIsMergeSameCell(isMergeSameCell);
        selectField.setTableKey(tableKey);
        selectField.setDataSheet(dataSheet);
        return selectField;
    }
}

