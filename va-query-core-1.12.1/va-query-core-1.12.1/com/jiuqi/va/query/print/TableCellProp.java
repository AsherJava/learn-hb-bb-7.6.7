/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print;

import com.jiuqi.va.query.print.GridCellProp;
import com.jiuqi.va.query.print.TableRowTypeEnum;

public class TableCellProp
extends GridCellProp {
    private TableRowTypeEnum rowType;

    public TableRowTypeEnum getRowType() {
        return this.rowType;
    }

    public void setRowType(TableRowTypeEnum rowType) {
        this.rowType = rowType;
    }
}

