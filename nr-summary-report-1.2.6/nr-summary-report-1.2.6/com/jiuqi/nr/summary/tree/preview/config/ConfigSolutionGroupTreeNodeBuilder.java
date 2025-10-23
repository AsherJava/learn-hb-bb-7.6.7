/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.preview.config;

import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.sumSolu.SolutionGroupTreeNodeBuilder;
import com.jiuqi.nr.summary.vo.NodeType;
import org.json.JSONObject;

public class ConfigSolutionGroupTreeNodeBuilder
extends SolutionGroupTreeNodeBuilder {
    @Override
    public TreeNode buildTreeNode(SummarySolutionGroup group, TreeQueryParam treeQueryParam) {
        TreeNode treeNode = super.buildTreeNode(group, treeQueryParam);
        treeNode.setDisabled(false);
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", NodeType.SUMMARY_SOLUTION_GROUP.getCode());
        treeNode.setData(extDataJson.toString());
        return treeNode;
    }
}

