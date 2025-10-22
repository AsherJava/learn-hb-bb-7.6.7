/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.enumeration.SummaryFunction;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class BSTableColumnImpl
implements BSTableColumn {
    private SummaryFunction sqlGroupFunc;
    private ColumnModelDefine columnModel;

    public BSTableColumnImpl(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    @Override
    public String getColumnName() {
        return this.columnModel.getName();
    }

    @Override
    public SummaryFunction getSQLGroupFunc() {
        return this.sqlGroupFunc;
    }

    public void setSQLGroupFunc(SummaryFunction sqlGroupFunc) {
        this.sqlGroupFunc = sqlGroupFunc;
    }

    @Override
    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }
}

