/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.itreebase.source.task;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.impl.NodeDataBuilderImpl;

public class TaskTreeNodeBuilder
extends NodeDataBuilderImpl {
    public TaskTreeNodeBuilder(INodeDataSource dataProvider) {
        super(dataProvider);
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IBaseNodeData data) {
        ITree<IBaseNodeData> node = super.buildTreeNode(data);
        node.setDisabled("group".equals(data.get("type")));
        return node;
    }
}

