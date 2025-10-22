/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.counter;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;

public interface IUnitTreeEntityRowCounter {
    public boolean isLeaf(IBaseNodeData var1);

    public int getDirectChildCount(IBaseNodeData var1);

    default public int getAllChildCount(IBaseNodeData parent) {
        return 0;
    }

    default public int getShowCountNumber(IBaseNodeData data) {
        return this.getDirectChildCount(data);
    }
}

