/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.summary.tree.sumSolu;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import java.util.List;

public class SolutionGroupTreeNodeBuilder
implements TreeNodeBuilder<SummarySolutionGroup> {
    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        return true;
    }

    @Override
    public List<SummarySolutionGroup> queryData(TreeQueryParam treeQueryParam) {
        IRuntimeSummarySolutionService runtimeSolutionService = (IRuntimeSummarySolutionService)SpringBeanUtils.getBean(IRuntimeSummarySolutionService.class);
        return runtimeSolutionService.getSummarySolutionGroupByGroup(treeQueryParam != null ? treeQueryParam.getNodeKey() : null);
    }

    @Override
    public TreeNode buildTreeNode(SummarySolutionGroup group, TreeQueryParam treeQueryParam) {
        TreeNode treeNode = new TreeNode();
        treeNode.setKey(group.getKey());
        treeNode.setTitle(group.getTitle());
        treeNode.setIcon("#icon16_SHU_A_NW_fenzu");
        treeNode.setDisabled(true);
        return treeNode;
    }
}

