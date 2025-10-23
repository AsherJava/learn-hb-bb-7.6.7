/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.summary.tree.sumSolu;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import java.util.List;

public class SolutionTreeNodeBuilder
implements TreeNodeBuilder<SummarySolution> {
    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        return true;
    }

    @Override
    public List<SummarySolution> queryData(TreeQueryParam treeQueryParam) {
        IRuntimeSummarySolutionService runtimeSumSolutionService = (IRuntimeSummarySolutionService)SpringBeanUtils.getBean(IRuntimeSummarySolutionService.class);
        return runtimeSumSolutionService.getSummarySolutionDefinesByGroup(treeQueryParam != null ? treeQueryParam.getNodeKey() : null);
    }

    @Override
    public TreeNode buildTreeNode(SummarySolution solution, TreeQueryParam treeQueryParam) {
        TreeNode treeNode = new TreeNode();
        treeNode.setKey(solution.getKey());
        treeNode.setCode(solution.getName());
        treeNode.setTitle(solution.getTitle());
        treeNode.setIcon("#icon-_GJZzidingyihuizong");
        treeNode.setLeaf(true);
        return treeNode;
    }
}

