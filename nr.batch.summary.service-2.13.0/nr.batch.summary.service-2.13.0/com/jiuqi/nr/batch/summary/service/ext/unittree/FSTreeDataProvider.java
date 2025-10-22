/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeProvider
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultTreeNodeProvider;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;

public class FSTreeDataProvider
extends DefaultTreeNodeProvider {
    public FSTreeDataProvider(IUnitTreeEntityRowProvider dimRowProvider, IUnitTreeNodeBuilder nodeBuilder, IBaseNodeData actionNode) {
        super(dimRowProvider, nodeBuilder, actionNode);
    }
}

