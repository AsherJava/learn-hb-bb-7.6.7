/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.zb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.zb.ZbNodeData;
import com.jiuqi.nr.summary.tree.zb.ZbTreeNodeType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataFieldTreeNodeBuilder
implements TreeNodeBuilder<DataField> {
    private static final Logger logger = LoggerFactory.getLogger(DataFieldTreeNodeBuilder.class);
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;

    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        Object extParam = treeQueryParam.getCustomValue("ext");
        return extParam != null;
    }

    @Override
    public List<DataField> queryData(TreeQueryParam treeQueryParam) {
        JSONObject extDataParamJson = new JSONObject(treeQueryParam.getCustomValue("ext").toString());
        ZbTreeNodeType nodeType = ZbTreeNodeType.valueOf(extDataParamJson.optInt("type"));
        if (ZbTreeNodeType.TABLE.equals((Object)nodeType) || ZbTreeNodeType.DETAIL.equals((Object)nodeType)) {
            DataTable dataTable = this.dataSchemeService.getDataTable(treeQueryParam.getNodeKey());
            treeQueryParam.putCustomParam("tableName", dataTable.getCode());
            treeQueryParam.putCustomParam("tableType", (Object)nodeType);
            List tableFields = this.dataSchemeService.getDataFieldByTable(treeQueryParam.getNodeKey());
            return tableFields.stream().filter(dataField -> DataFieldKind.FIELD.equals((Object)dataField.getDataFieldKind()) || DataFieldKind.FIELD_ZB.equals((Object)dataField.getDataFieldKind())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public TreeNode buildTreeNode(DataField dataField, TreeQueryParam treeQueryParam) throws SummaryCommonException {
        TreeNode node = new TreeNode();
        node.setKey(dataField.getKey());
        node.setCode(dataField.getCode());
        node.setTitle(dataField.getTitle());
        node.setIcon("#icon-16_SHU_A_NR_zhibiao");
        node.setDraggable(true);
        node.setLeaf(true);
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", ZbTreeNodeType.ZB.getCode());
        ZbNodeData zbNodeData = new ZbNodeData();
        zbNodeData.setGatherType(dataField.getDataFieldGatherType());
        zbNodeData.setDataType(dataField.getDataFieldType());
        zbNodeData.setDecimal(dataField.getDecimal() == null ? -1 : dataField.getDecimal());
        zbNodeData.setPrecision(dataField.getPrecision() == null ? -1 : dataField.getPrecision());
        String tableName = treeQueryParam.getCustomValue("tableName").toString();
        zbNodeData.setExp(String.format("%s[%s]", tableName, dataField.getCode()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            extDataJson.put("zbInfo", (Object)new JSONObject(objectMapper.writeValueAsString((Object)zbNodeData)));
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.TREE_LOAD_FAILED, e.getMessage());
        }
        ZbTreeNodeType tableType = (ZbTreeNodeType)((Object)treeQueryParam.getCustomValue("tableType"));
        if (ZbTreeNodeType.DETAIL.equals((Object)tableType)) {
            node.setIcon("#icon-16_SHU_A_NR_ziduan");
            extDataJson.put("type", ZbTreeNodeType.FIELD.getCode());
        }
        node.setData(extDataJson.toString());
        return node;
    }
}

