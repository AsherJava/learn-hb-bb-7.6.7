/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.tree;

import com.jiuqi.nr.migration.syncscheme.tree.SyncSchemeGroupTreeNode;
import com.jiuqi.nr.migration.syncscheme.tree.TreeNode;
import java.util.List;

public interface ISyncSchemeGroupTreeService {
    public List<TreeNode<SyncSchemeGroupTreeNode>> getRoot();

    public List<TreeNode<SyncSchemeGroupTreeNode>> getChildren(String var1);

    public List<TreeNode<SyncSchemeGroupTreeNode>> location(String var1);
}

