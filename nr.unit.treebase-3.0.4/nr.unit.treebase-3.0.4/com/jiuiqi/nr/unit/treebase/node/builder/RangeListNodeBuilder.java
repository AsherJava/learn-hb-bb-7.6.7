/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class RangeListNodeBuilder
implements IUnitTreeNodeBuilder {
    private IUnitTreeNodeBuilder baseNodeBuilder;

    public RangeListNodeBuilder(IUnitTreeNodeBuilder baseNodeBuilder) {
        this.baseNodeBuilder = baseNodeBuilder;
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.baseNodeBuilder.beforeCreateITreeNode(rows);
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> treeNode = this.baseNodeBuilder.buildTreeNode(row);
        treeNode.setLeaf(true);
        return treeNode;
    }
}

