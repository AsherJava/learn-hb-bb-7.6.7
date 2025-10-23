/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import com.jiuqi.nr.zb.scheme.internal.tree.ITree;
import com.jiuqi.nr.zb.scheme.internal.tree.TreeNodeQueryParam;
import java.util.List;

public interface IZbSchemeTreeService {
    public List<ITree<INode>> queryZbSchemeGroupTree(TreeNodeQueryParam var1);

    public List<ITree<INode>> queryZbGroupTree(TreeNodeQueryParam var1);

    public List<ITree<INode>> filterZbInfo(TreeNodeQueryParam var1);
}

