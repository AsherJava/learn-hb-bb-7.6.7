/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.DefaultUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public class OnlyLeafNodeCounter
extends DefaultUnitTreeNodeCounter {
    public OnlyLeafNodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        super(context, entityDataQuery);
    }

    @Override
    public int innerAllChildCount(IEntityRow parentRow) {
        List allChildRows = this.cacheDataTable.getAllChildRows(parentRow.getEntityKeyData());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!row.isLeaf()) continue;
            ++count;
        }
        return count;
    }

    @Override
    protected int innerDirectChildCount(IEntityRow parentRow) {
        List allChildRows = this.cacheDataTable.getChildRows(parentRow.getEntityKeyData());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!row.isLeaf()) continue;
            ++count;
        }
        return count;
    }

    protected boolean isLeaf(IEntityRow row) {
        int childCount = this.cacheDataTable.getDirectChildCount(row.getEntityKeyData());
        return childCount == 0;
    }
}

