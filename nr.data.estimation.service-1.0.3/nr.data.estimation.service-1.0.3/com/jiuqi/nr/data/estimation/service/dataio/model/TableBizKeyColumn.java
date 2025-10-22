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

public class TableBizKeyColumn
implements ITableBizKeyColumn {
    private DataField dataField;
    private IColumnType columnType;
    private final ColumnModelDefine columnModel;

    public TableBizKeyColumn(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    @Override
    public String getColumnName() {
        return this.columnModel.getCode();
    }

    @Override
    public IColumnType getColumnType() {
        return this.columnType;
    }

    public void setColumnType(IColumnType columnType) {
        this.columnType = columnType;
    }

    @Override
    public DataField getDataField() {
        return this.dataField;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
    }

    @Override
    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }
}

