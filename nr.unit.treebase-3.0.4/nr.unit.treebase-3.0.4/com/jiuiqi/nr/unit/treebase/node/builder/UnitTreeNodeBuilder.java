/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;

public class UnitTreeNodeBuilder
extends DefaultTreeNodeBuilder {
    private static final String KEY_OF_ALL_CHILD_COUNT = "allChildCount";

    public UnitTreeNodeBuilder(IUnitTreeEntityRowProvider dimRowProvider, IconSourceProvider iconProvider) {
        super(dimRowProvider, iconProvider);
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        return super.buildTreeNode(row);
    }
}

