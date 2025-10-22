/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.itreebase.source.impl;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataBuilder;
import com.jiuqi.nr.itreebase.source.INodeDataSource;

public class NodeDataBuilderImpl
implements INodeDataBuilder {
    protected INodeDataSource dataSource;

    public NodeDataBuilderImpl(INodeDataSource dataProvider) {
        this.dataSource = dataProvider;
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IBaseNodeData data) {
        ITree node = new ITree((INode)data);
        node.setLeaf(this.dataSource.isLeaf(data));
        return node;
    }
}

