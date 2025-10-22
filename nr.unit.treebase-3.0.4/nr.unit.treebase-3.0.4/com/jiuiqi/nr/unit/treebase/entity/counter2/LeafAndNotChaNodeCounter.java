/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.AllChildAndNotChaENodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.List;

public class LeafAndNotChaNodeCounter
extends AllChildAndNotChaENodeCounter {
    public LeafAndNotChaNodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IEntityRefer referBBLXEntity) {
        super(context, entityDataQuery, referBBLXEntity);
    }

    @Override
    public int innerAllChildCount(IEntityRow parentRow) {
        List allChildRows = this.cacheDataTable.getAllChildRows(parentRow.getEntityKeyData());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!row.isLeaf() || this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    @Override
    public int innerDirectChildCount(IEntityRow parentRow) {
        List allChildRows = this.cacheDataTable.getChildRows(parentRow.getEntityKeyData());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (!row.isLeaf() || this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    protected boolean isLeaf(IEntityRow row) {
        int childCount = this.cacheDataTable.getDirectChildCount(row.getEntityKeyData());
        return childCount == 0;
    }
}

