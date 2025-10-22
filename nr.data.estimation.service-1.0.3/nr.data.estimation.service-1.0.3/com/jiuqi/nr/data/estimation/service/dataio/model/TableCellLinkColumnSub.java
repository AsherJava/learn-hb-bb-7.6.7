/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.model.TableCellLinkColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class TableCellLinkColumnSub
extends TableCellLinkColumn {
    private final ColumnModelDefine oriColumnModel;

    public TableCellLinkColumnSub(ColumnModelDefine oriColumnModel) {
        this.oriColumnModel = oriColumnModel;
    }

    public ColumnModelDefine getOriColumnModel() {
        return this.oriColumnModel;
    }

    @Override
    public String getColumnName() {
        return this.getOriColumnModel().getID();
    }
}

