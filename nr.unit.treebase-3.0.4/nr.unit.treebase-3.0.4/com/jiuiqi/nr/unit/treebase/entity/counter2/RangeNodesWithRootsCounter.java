/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.DefaultUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeNodesWithRootsCounter
extends DefaultUnitTreeNodeCounter {
    private final List<String> rootRange;

    public RangeNodesWithRootsCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> rootRange) {
        super(context, entityDataQuery);
        this.rootRange = rootRange;
    }

    @Override
    public Map<String, Integer> getRootNodeCountMap() {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(this.context, this.rootRange);
        List rootRows = readDataTable.getRootRows();
        Map<String, Integer> countMap = this.buildCountMapByRows(this.entityDataQuery, this.context, rootRows, rootRows);
        this.isFullyBuiltTree = true;
        return countMap;
    }
}

