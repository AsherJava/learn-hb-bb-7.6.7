/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.preview.config;

import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.sumSolu.SolutionTreeNodeBuilder;
import com.jiuqi.nr.summary.vo.NodeType;
import org.json.JSONObject;

public class ConfigSolutionTreeNodeBuilder
extends SolutionTreeNodeBuilder {
    @Override
    public TreeNode buildTreeNode(SummarySolution solution, TreeQueryParam treeQueryParam) {
        TreeNode treeNode = super.buildTreeNode(solution, treeQueryParam);
        treeNode.setLeaf(false);
        treeNode.setDisabled(false);
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", NodeType.SUMMARY_SOLUTION.getCode());
        treeNode.setData(extDataJson.toString());
        return treeNode;
    }
}

