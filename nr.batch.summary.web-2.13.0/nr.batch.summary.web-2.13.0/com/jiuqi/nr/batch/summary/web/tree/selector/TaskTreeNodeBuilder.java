/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.INodeDataSource
 *  com.jiuqi.nr.itreebase.source.impl.NodeDataBuilderImpl
 */
package com.jiuqi.nr.batch.summary.web.tree.selector;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.impl.NodeDataBuilderImpl;

public class TaskTreeNodeBuilder
extends NodeDataBuilderImpl {
    public TaskTreeNodeBuilder(INodeDataSource dataProvider) {
        super(dataProvider);
    }

    public ITree<IBaseNodeData> buildTreeNode(IBaseNodeData data) {
        ITree node = super.buildTreeNode(data);
        node.setDisabled("group".equals(data.get("type")));
        return node;
    }
}

