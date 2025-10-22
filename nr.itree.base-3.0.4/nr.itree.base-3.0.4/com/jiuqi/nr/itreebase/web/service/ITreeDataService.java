/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.itreebase.web.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.context.ITreeContextData;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage;
import java.util.List;

public interface ITreeDataService {
    public List<ITree<IBaseNodeData>> getTree(ITreeContextData var1);

    public List<ITree<IBaseNodeData>> getChildren(ITreeContextData var1);

    public ISearchNodeDataPage searchingNodes(ITreeContextData var1);
}

