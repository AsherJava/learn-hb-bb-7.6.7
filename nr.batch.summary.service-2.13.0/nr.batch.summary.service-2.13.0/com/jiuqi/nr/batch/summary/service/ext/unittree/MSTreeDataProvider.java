/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeProvider
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeProvider;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class MSTreeDataProvider
extends DefaultTreeNodeProvider {
    public MSTreeDataProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode) {
        super(dimRowProvider, nodeBuilder, actionNode);
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        List roots = super.getRoots();
        for (ITree root : roots) {
            root.setChildren(super.getChildren((IBaseNodeData)root.getData()));
            root.setExpanded(true);
        }
        return roots;
    }
}

