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

import com.jiuiqi.nr.unit.treebase.entity.counter.DefaultUnitTreeRowCounter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public class TreeNodeAndNotChaECounter
extends DefaultUnitTreeRowCounter {
    protected static final String BBLX_CEVALUE = "1";
    protected IEntityRefer referBBLXEntity;

    public TreeNodeAndNotChaECounter(IEntityTable dataTable, IEntityRefer referBBLXEntity) {
        super(dataTable);
        this.referBBLXEntity = referBBLXEntity;
    }

    @Override
    public int innerAllChildCount(IBaseNodeData rowData) {
        List allChildRows = this.dataTable.getAllChildRows(rowData.getKey());
        int count = 0;
        for (IEntityRow row : allChildRows) {
            if (this.isChaeDW(row)) continue;
            ++count;
        }
        return count;
    }

    @Override
    protected int innerDirectChildCount(IBaseNodeData rowData) {
        List allChildRows = this.dataTable.getChildRows(rowData.getKey());
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

