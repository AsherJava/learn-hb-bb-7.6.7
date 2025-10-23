/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo.search;

import com.jiuqi.nr.summary.vo.ResourceNode;
import com.jiuqi.nr.summary.vo.TreeNode;
import java.util.List;

public class SummarySearchPosition {
    private TreeNode treeNode;
    private List<ResourceNode> resourceNodes;
    private TreeNode selectedTreeNode;

    public TreeNode getTreeNode() {
        return this.treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public List<ResourceNode> getResourceNodes() {
        return this.resourceNodes;
    }

    public void setResourceNodes(List<ResourceNode> resourceNodes) {
        this.resourceNodes = resourceNodes;
    }

    public TreeNode getSelectedTreeNode() {
        return this.selectedTreeNode;
    }

    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
    }
}

