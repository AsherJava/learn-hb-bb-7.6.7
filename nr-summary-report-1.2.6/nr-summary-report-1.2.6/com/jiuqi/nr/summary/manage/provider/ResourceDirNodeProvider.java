/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.manage.provider;

import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;
import com.jiuqi.nr.summary.vo.SummarySolutionNodeData;
import com.jiuqi.nr.summary.vo.TreeNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceDirNodeProvider {
    @Autowired
    private IDesignSummarySolutionService designSolutionService;

    public List<TreeNode> getRoots() {
        ArrayList<TreeNode> roots = new ArrayList<TreeNode>();
        TreeNode summarySolutionRoot = this.buildSummarySolutionRoot();
        summarySolutionRoot.addAllChild(this.getSummarySolutionGroups(null));
        summarySolutionRoot.addAllChild(this.getSummarySolutions(null));
        roots.add(summarySolutionRoot);
        return roots;
    }

    public List<TreeNode> getChilds(String parentKey) {
        ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
        nodes.addAll(this.getSummarySolutionGroups(parentKey));
        nodes.addAll(this.getSummarySolutions(parentKey));
        return nodes;
    }

    private List<TreeNode> getSummarySolutionGroups(String parentKey) {
        return this.designSolutionService.getSummarySolutionGroupByGroup(parentKey).stream().sorted(Comparator.comparing(SummarySolutionGroup::getModifyTime)).map(this::buildFromSummarySolutionGroup).collect(Collectors.toList());
    }

    private List<TreeNode> getSummarySolutions(String parentKey) {
        return this.designSolutionService.getSummarySolutionsByGroup(parentKey).stream().sorted(Comparator.comparing(SummarySolution::getModifyTime)).map(this::buildFromSummarySolution).collect(Collectors.toList());
    }

    private TreeNode buildSummarySolutionRoot() {
        TreeNode root = new TreeNode("_summary_solution_root_group_", "\u6240\u6709\u6c47\u603b\u65b9\u6848", false, false, true);
        CustomNodeData nodeData = new CustomNodeData(NodeType.SUMMARY_SOLUTION_ROOT_GROUP);
        root.setData(nodeData);
        return root;
    }

    private TreeNode buildFromSummarySolutionGroup(SummarySolutionGroup summarySolutionGroup) {
        TreeNode treeNode = new TreeNode(summarySolutionGroup.getKey(), summarySolutionGroup.getTitle());
        CustomNodeData nodeData = new CustomNodeData(NodeType.SUMMARY_SOLUTION_GROUP);
        treeNode.setData(nodeData);
        return treeNode;
    }

    private TreeNode buildFromSummarySolution(SummarySolution summarySolution) {
        TreeNode treeNode = new TreeNode(summarySolution.getKey(), summarySolution.getTitle());
        SummarySolutionNodeData nodeData = new SummarySolutionNodeData(summarySolution.getMainTask());
        treeNode.setData(nodeData);
        treeNode.setLeaf(true);
        return treeNode;
    }
}

