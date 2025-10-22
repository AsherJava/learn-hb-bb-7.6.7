/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.itreebase.source.impl;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.impl.TreeNodeProviderImpl;
import java.util.List;

public class TreeWithFirstSelectNodeProvider
extends TreeNodeProviderImpl {
    public TreeWithFirstSelectNodeProvider(INodeDataSource dataProvider, INodeDataBuilder nodeBuilder, IBaseNodeData actionNode) {
        super(dataProvider, nodeBuilder, actionNode);
    }

    @Override
    public List<ITree<IBaseNodeData>> getRoots() {
        List<ITree<IBaseNodeData>> roots = super.getRoots();
        if (this.actionNode == null && roots.size() > 0) {
            ITree<IBaseNodeData> firstNode = roots.get(0);
            firstNode.setSelected(true);
            if (!firstNode.isLeaf()) {
                firstNode.setExpanded(true);
                firstNode.setChildren(this.getChildren((IBaseNodeData)firstNode.getData()));
            }
        }
        return roots;
    }
}

