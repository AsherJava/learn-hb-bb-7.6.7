/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.preview;

import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.sumSolu.SolutionGroupTreeNodeBuilder;
import com.jiuqi.nr.summary.vo.NodeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FlexibleSolutionGroupTreeNodeBuilder
extends SolutionGroupTreeNodeBuilder {
    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        if (treeQueryParam != null) {
            String nodeKey = treeQueryParam.getNodeKey();
            ArrayList soluGroupKeys = (ArrayList)treeQueryParam.getCustomValue("sumSoluGroups");
            if (!CollectionUtils.isEmpty(soluGroupKeys)) {
                if (StringUtils.hasLength(nodeKey)) {
                    treeQueryParam.putCustomParam("queryChild", true);
                    int type = (Integer)treeQueryParam.getCustomValue("type");
                    return type == NodeType.SUMMARY_SOLUTION_GROUP.getCode();
                }
                treeQueryParam.putCustomParam("queryChild", false);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SummarySolutionGroup> queryData(TreeQueryParam treeQueryParam) {
        List<SummarySolutionGroup> solutionGroups = super.queryData(treeQueryParam);
        ArrayList soluGroups = (ArrayList)treeQueryParam.getCustomValue("sumSoluGroups");
        boolean queryChild = (Boolean)treeQueryParam.getCustomValue("queryChild");
        return queryChild ? solutionGroups : solutionGroups.stream().filter(group -> soluGroups.contains(group.getKey())).collect(Collectors.toList());
    }

    @Override
    public TreeNode buildTreeNode(SummarySolutionGroup group, TreeQueryParam treeQueryParam) {
        TreeNode treeNode = super.buildTreeNode(group, treeQueryParam);
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", NodeType.SUMMARY_SOLUTION_GROUP.getCode());
        treeNode.setData(extDataJson.toString());
        return treeNode;
    }
}

