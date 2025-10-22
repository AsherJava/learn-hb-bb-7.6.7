/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.counter;

import com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class LeafNodeCounter
extends DefaultUnitTreeRowCounter {
    public LeafNodeCounter(IEntityTable dataTable) {
        super(dataTable);
    }

    @Override
    public int innerAllChildCount(IBaseNodeData rowData) {
        List allChildRows = this.dataTable.getAllChildRows(rowData.getKey());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!this.checkRow(row)) continue;
            ++count;
        }
        return count;
    }

    @Override
    protected int innerDirectChildCount(IBaseNodeData rowData) {
        List allChildRows = this.dataTable.getChildRows(rowData.getKey());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!this.checkRow(row)) continue;
            ++count;
        }
        return count;
    }

    protected boolean checkRow(IEntityRow row) {
        int childCount = this.dataTable.getDirectChildCount(row.getEntityKeyData());
        return childCount == 0;
    }
}

