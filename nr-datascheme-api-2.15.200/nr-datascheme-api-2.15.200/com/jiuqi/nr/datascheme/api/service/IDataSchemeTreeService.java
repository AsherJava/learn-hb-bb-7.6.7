/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import java.util.List;

public interface IDataSchemeTreeService<N extends INode> {
    public List<N> search(TreeSearchQuery var1);

    public List<ITree<N>> getRootTree(NodeType var1, String var2);

    public List<ITree<N>> getChildTree(NodeType var1, N var2);

    public List<ITree<N>> getSpecifiedTree(NodeType var1, N var2, String var3);

    public List<ITree<N>> getSchemeGroupRootTree(NodeType var1);

    public List<ITree<N>> getQuerySchemeGroupRootTree(NodeType var1);

    public List<ITree<N>> getSchemeGroupChildTree(NodeType var1, N var2);

    public List<ITree<N>> getSchemeGroupSpecifiedTree(NodeType var1, N var2);

    public List<ITree<N>> getRootTree(String var1, int var2, NodeFilter var3);

    public List<ITree<N>> getChildTree(N var1, int var2, NodeFilter var3);

    public List<ITree<N>> getSpecifiedTree(N var1, String var2, int var3, NodeFilter var4);

    public List<ITree<N>> getSchemeGroupRootTree(int var1, NodeFilter var2);

    public List<ITree<N>> getSchemeGroupChildTree(N var1, int var2, NodeFilter var3);

    public List<ITree<N>> getSchemeGroupSpecifiedTree(N var1, int var2, NodeFilter var3);

    public List<ITree<N>> getCheckBoxSchemeGroupRootTree(NodeType var1, int var2, NodeFilter var3);

    public List<ITree<N>> getCheckBoxSchemeGroupChildTree(NodeType var1, N var2, int var3, NodeFilter var4);

    public List<ITree<N>> getCheckBoxSchemeSpecifiedTree(NodeType var1, N var2, int var3, NodeFilter var4);

    public List<ITree<N>> getCheckBoxSchemeGroupRootTree(int var1, int var2, NodeFilter var3);

    public List<ITree<N>> getCheckBoxSchemeGroupChildTree(int var1, N var2, int var3, NodeFilter var4);

    public List<ITree<N>> getCheckBoxSchemeSpecifiedTree(int var1, N var2, int var3, NodeFilter var4);
}

