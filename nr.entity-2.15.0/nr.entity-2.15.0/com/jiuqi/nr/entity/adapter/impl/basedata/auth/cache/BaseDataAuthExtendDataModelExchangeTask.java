/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.DataModelExchangeTask
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.cache;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.DataModelExchangeTask;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataAuthExtendDataModelExchangeTask
implements DataModelExchangeTask {
    @Autowired
    private DataModelClient dataModelClient;

    public void publish(DataModelDO dataModelDO) {
        if (!dataModelDO.getName().startsWith("MD_ORG")) {
            for (DataModelColumn column : dataModelDO.getColumns()) {
                if (!"INFORMANT_USER".equalsIgnoreCase(column.getColumnName()) || this.isDefault(column)) continue;
                this.modifyColumn(column);
                this.modifyShowField(dataModelDO);
                this.dataModelClient.pushIncrement(dataModelDO);
                return;
            }
        }
    }

    private void modifyShowField(DataModelDO dataModelDO) {
        JsonNode jsonNode;
        BaseDataDefineDTO baseDataDefine = (BaseDataDefineDTO)dataModelDO.getExtInfo("baseDataDefine");
        if (baseDataDefine == null) {
            return;
        }
        String define = baseDataDefine.getDefine();
        if (define.contains("INFORMANT_USER") && (jsonNode = (JsonNode)JacksonUtils.jsonToObject((String)define, JsonNode.class)) != null && jsonNode.has("showFields")) {
            ArrayNode showFields = (ArrayNode)jsonNode.get("showFields");
            for (JsonNode field : showFields) {
                ObjectNode showField = (ObjectNode)field;
                if (!"INFORMANT_USER".equalsIgnoreCase(showField.get("columnName").asText())) continue;
                showField.put("columnType", "UUID");
                showField.put("lengths", "[36]");
                showField.put("mappingType", 3);
                showField.put("mapping", "AUTH_USER.ID");
            }
            baseDataDefine.setDefine(JacksonUtils.objectToJson((Object)jsonNode));
        }
    }

    private void modifyColumn(DataModelColumn column) {
        column.columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).mapping("AUTH_USER.ID").mappingType(Integer.valueOf(3));
    }

    private boolean isDefault(DataModelColumn column) {
        return column.getMappingType() != null && column.getMappingType() == 3 && column.getColumnType() == DataModelType.ColumnType.UUID && column.getLengths().length == 1 && column.getLengths()[0] == 36 && "AUTH_USER.ID".equals(column.getMapping());
    }
}

