/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.dataresource.service;

import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.ITree;
import java.util.List;

public interface IDataResourceTreeService<N extends DataResourceNode> {
    public List<ITree<N>> getRootTree(NodeType var1, String var2);

    public List<ITree<N>> getChildTree(NodeType var1, N var2);

    public List<ITree<N>> getSpecifiedTree(NodeType var1, N var2, String var3);

    public List<N> search(TreeSearchQuery var1);

    public List<ITree<N>> getRootTree(NodeType var1);

    public List<ITree<N>> getGroupChildTree(NodeType var1, N var2);

    public List<ITree<N>> getGroupSpecifiedTree(NodeType var1, N var2);
}

