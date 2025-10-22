/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.HashMap;
import java.util.Map;

public class RangeNodesWithListCounter
implements IUnitTreeNodeCounter {
    public RangeNodesWithListCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
    }

    @Override
    public Map<String, Integer> getRootNodeCountMap() {
        return new HashMap<String, Integer>();
    }

    @Override
    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData parent) {
        return new HashMap<String, Integer>();
    }

    @Override
    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData locateNode) {
        return new HashMap<String, Integer>();
    }
}

