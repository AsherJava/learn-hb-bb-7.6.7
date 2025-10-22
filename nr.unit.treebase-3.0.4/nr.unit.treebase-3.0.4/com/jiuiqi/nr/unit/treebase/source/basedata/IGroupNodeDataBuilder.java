/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;

public interface IGroupNodeDataBuilder
extends IUnitTreeNodeBuilder {
    public static final String NODE_TYPE = "nodeType";

    public ITree<IBaseNodeData> buildTreeNode(IGroupDataRow var1);
}

