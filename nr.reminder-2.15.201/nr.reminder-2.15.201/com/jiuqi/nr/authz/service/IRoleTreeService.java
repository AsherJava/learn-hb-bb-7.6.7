/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.authz.service;

import com.jiuqi.nr.authz.bean.RoleQueryParam;
import com.jiuqi.nr.authz.bean.RoleTreeNode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface IRoleTreeService {
    public List<RoleTreeNode> getRolesByIds(List<String> var1);

    default public List<ITree<RoleTreeNode>> getRootNode() {
        return this.getRootNode(false);
    }

    public List<ITree<RoleTreeNode>> getRootNode(boolean var1);

    public List<ITree<RoleTreeNode>> getChildNode(RoleTreeNode var1, boolean var2);

    default public List<ITree<RoleTreeNode>> getChildNode(RoleTreeNode parent) {
        return this.getChildNode(parent, false);
    }

    public List<ITree<RoleTreeNode>> getSearchTree(RoleTreeNode var1, boolean var2);

    default public List<ITree<RoleTreeNode>> getSearchTree(RoleTreeNode node) {
        return this.getSearchTree(node, false);
    }

    public List<RoleTreeNode> searchRoleByFuzzyQuery(RoleQueryParam var1);
}

