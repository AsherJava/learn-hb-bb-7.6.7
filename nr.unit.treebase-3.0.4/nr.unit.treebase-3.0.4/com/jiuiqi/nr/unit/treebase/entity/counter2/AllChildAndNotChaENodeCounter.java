/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.DefaultUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.List;

public class AllChildAndNotChaENodeCounter
extends DefaultUnitTreeNodeCounter {
    public static final String BBLX_CEVALUE = "1";
    protected IEntityRefer referBBLXEntity;

    public AllChildAndNotChaENodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, IEntityRefer referBBLXEntity) {
        super(context, entityDataQuery);
        this.referBBLXEntity = referBBLXEntity;
    }

    @Override
    public int innerAllChildCount(IEntityRow parentRow) {
        List allChildRows = this.cacheDataTable.getAllChildRows(parentRow.getEntityKeyData());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    @Override
    protected int innerDirectChildCount(IEntityRow parentRow) {
        List allChildRows = this.cacheDataTable.getChildRows(parentRow.getEntityKeyData());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    protected boolean isChaeDW(IEntityRow row) {
        if (null != this.referBBLXEntity) {
            return BBLX_CEVALUE.equals(row.getAsString(this.referBBLXEntity.getOwnField()));
        }
        return false;
    }
}

