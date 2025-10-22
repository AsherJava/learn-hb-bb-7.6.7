/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dataentry.internal.overview.OverviewTreeNodeProvider;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class OverviewSelectFirstNodeProvider
extends OverviewTreeNodeProvider {
    public OverviewSelectFirstNodeProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode, IUnitTreeContext ctx) {
        super(dimRowProvider, nodeBuilder, actionNode, ctx);
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

