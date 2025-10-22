/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.def;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeProvider;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class DefaultSelectFirstNodeProvider
extends DefaultTreeNodeProvider {
    private static final String IS_CHILD_COUNT_LOADED = "isChildCountLoaded";

    public DefaultSelectFirstNodeProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode) {
        super(dimRowProvider, nodeBuilder, actionNode);
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
                ((IBaseNodeData)firstNode.getData()).put(IS_CHILD_COUNT_LOADED, (Object)true);
            }
        }
        return roots;
    }
}

