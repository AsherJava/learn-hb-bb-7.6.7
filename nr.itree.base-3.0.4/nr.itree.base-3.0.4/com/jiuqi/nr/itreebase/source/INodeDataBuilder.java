/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.itreebase.source;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface INodeDataBuilder {
    default public void beforeCreateITreeNode(List<IBaseNodeData> rows) {
    }

    public ITree<IBaseNodeData> buildTreeNode(IBaseNodeData var1);
}

