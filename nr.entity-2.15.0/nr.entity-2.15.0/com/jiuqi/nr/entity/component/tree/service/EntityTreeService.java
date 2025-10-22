/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.entity.component.tree.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import java.util.List;

public interface EntityTreeService {
    public List<ITree<TreeNode>> initTree(TreeParam var1);

    public List<ITree<TreeNode>> getChildrenNodes(TreeParam var1);

    public List<TreeNode> searchNodes(TreeParam var1);

    public List<ITree<TreeNode>> locationTreeNode(TreeParam var1);

    public List<String> getAllChildrenNodes(TreeParam var1);
}

