/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.model.TableDimensionColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class TableDimensionColumnSub
extends TableDimensionColumn {
    private final ColumnModelDefine oriColumnModel;

    public TableDimensionColumnSub(ColumnModelDefine columnModel, ColumnModelDefine oriColumnModel) {
        super(columnModel);
        this.oriColumnModel = oriColumnModel;
    }

    public ColumnModelDefine getOriColumnModel() {
        return this.oriColumnModel;
    }
}

