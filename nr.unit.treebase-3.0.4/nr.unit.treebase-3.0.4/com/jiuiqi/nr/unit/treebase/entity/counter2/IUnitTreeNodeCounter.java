/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.Map;

public interface IUnitTreeNodeCounter {
    public Map<String, Integer> getRootNodeCountMap();

    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData var1);

    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData var1);
}

