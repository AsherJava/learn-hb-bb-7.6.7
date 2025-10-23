/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.zb;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.zb.ZbTreeNodeType;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTableTreeNodeBuilder
implements TreeNodeBuilder<DataTable> {
    @Autowired
    private SummaryParamService summaryParamService;

    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        Object extParam = treeQueryParam.getCustomValue("ext");
        return extParam != null;
    }

    @Override
    public List<DataTable> queryData(TreeQueryParam treeQueryParam) {
        JSONObject extDataParamJson = new JSONObject(treeQueryParam.getCustomValue("ext").toString());
        ZbTreeNodeType nodeType = ZbTreeNodeType.valueOf(extDataParamJson.optInt("type"));
        if (ZbTreeNodeType.DATASCHEME.equals((Object)nodeType)) {
            return this.summaryParamService.getAllDataTable(treeQueryParam.getNodeKey());
        }
        return Collections.emptyList();
    }

    @Override
    public TreeNode buildTreeNode(DataTable dataTable, TreeQueryParam treeQueryParam) {
        TreeNode node = new TreeNode();
        node.setKey(dataTable.getKey());
        node.setCode(dataTable.getCode());
        node.setTitle(dataTable.getTitle());
        node.setIcon("#icon-16_SHU_A_NR_zhibiaobiao");
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", ZbTreeNodeType.TABLE.getCode());
        if (DataTableType.DETAIL.equals((Object)dataTable.getDataTableType())) {
            node.setIcon("#icon-16_SHU_A_NR_mingxibiao");
            extDataJson.put("type", ZbTreeNodeType.DETAIL.getCode());
        }
        node.setData(extDataJson.toString());
        return node;
    }
}

