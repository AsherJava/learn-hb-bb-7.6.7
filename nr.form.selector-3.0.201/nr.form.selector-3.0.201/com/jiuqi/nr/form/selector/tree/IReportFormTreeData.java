/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import java.util.List;

public interface IReportFormTreeData {
    public List<ITree<IReportTreeNode>> getRoots(String var1);

    public List<ITree<IReportTreeNode>> getChildren(String var1);

    public List<ITree<IReportTreeNode>> getTree(String var1);
}

