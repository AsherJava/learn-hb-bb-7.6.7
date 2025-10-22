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

public class TableDimensionColumn
implements ITableBizKeyColumn {
    private DataField dataField;
    private ColumnModelDefine columnModel;

    public TableDimensionColumn(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    @Override
    public String getColumnName() {
        return this.columnModel.getCode();
    }

    @Override
    public IColumnType getColumnType() {
        return IColumnType.bizKey_column;
    }

    @Override
    public DataField getDataField() {
        return this.dataField;
    }

    @Override
    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }
}

