/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class TablePrimaryKeyColumn
implements ITableBizKeyColumn {
    private final String columnName;
    private ColumnModelDefine columnModel;

    public TablePrimaryKeyColumn(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
        this.columnName = columnModel.getCode();
    }

    public TablePrimaryKeyColumn(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public DataField getDataField() {
        return null;
    }

    @Override
    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public IColumnType getColumnType() {
        return IColumnType.bizKey_column;
    }
}

