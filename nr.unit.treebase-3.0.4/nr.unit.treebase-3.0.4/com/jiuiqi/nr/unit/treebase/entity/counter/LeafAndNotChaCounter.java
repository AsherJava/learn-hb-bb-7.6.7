/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.counter;

import com.jiuiqi.nr.unit.treebase.entity.counter.TreeNodeAndNotChaECounter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class LeafAndNotChaCounter
extends TreeNodeAndNotChaECounter {
    public LeafAndNotChaCounter(IEntityTable dataTable, IEntityRefer referBBLXEntity) {
        super(dataTable, referBBLXEntity);
    }

    @Override
    public int innerAllChildCount(IBaseNodeData rowData) {
        List allChildRows = this.dataTable.getAllChildRows(rowData.getKey());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!this.isLeaf(row) || this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    @Override
    public int innerDirectChildCount(IBaseNodeData rowData) {
        List allChildRows = this.dataTable.getChildRows(rowData.getKey());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!this.isLeaf(row) || this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    protected boolean isLeaf(IEntityRow row) {
        int childCount = this.dataTable.getDirectChildCount(row.getEntityKeyData());
        return childCount == 0;
    }
}

