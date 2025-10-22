/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.INodeDataSource
 *  com.jiuqi.nr.itreebase.source.impl.NodeDataBuilderImpl
 */
package com.jiuqi.nr.batch.gather.gzw.web.tree.selector;

import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.INodeDataSource;
import com.jiuqi.nr.itreebase.source.impl.NodeDataBuilderImpl;

public class TargetDimNodeGZWBuilder
extends NodeDataBuilderImpl {
    public TargetDimNodeGZWBuilder(INodeDataSource dataProvider) {
        super(dataProvider);
    }

    public ITree<IBaseNodeData> buildTreeNode(IBaseNodeData data) {
        ITree node = super.buildTreeNode(data);
        node.setDisabled(TargetDimType.BASE_DATA.key.equals(data.getKey()) || TargetDimType.CALIBRE.key.equals(data.getKey()));
        return node;
    }
}

