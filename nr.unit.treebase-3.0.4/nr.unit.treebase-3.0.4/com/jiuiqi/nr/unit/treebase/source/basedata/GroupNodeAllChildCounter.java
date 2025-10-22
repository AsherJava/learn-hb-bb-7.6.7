/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.basedata.GroupNodeDirectChildCounter;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;

public class GroupNodeAllChildCounter
extends GroupNodeDirectChildCounter {
    public GroupNodeAllChildCounter(IGroupDataTable groupDataTable, IUnitTreeEntityRowProvider dimRowProvider, IBaseNodeData locateNode) {
        super(groupDataTable, dimRowProvider, locateNode);
    }

    @Override
    protected int getShowTextNumber(IBaseNodeData nodeData) {
        if ("node@Group".equals(nodeData.get("nodeType"))) {
            return this.groupDataTable.getAllChildCount(nodeData);
        }
        return this.dimRowProvider.getAllChildCount(nodeData);
    }
}

