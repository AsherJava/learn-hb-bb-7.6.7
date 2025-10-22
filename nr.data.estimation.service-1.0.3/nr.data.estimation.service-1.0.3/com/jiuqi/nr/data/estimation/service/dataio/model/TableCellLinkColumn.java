/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class TableCellLinkColumn
implements ITableCellLinkColumn {
    private DataField dataField;
    private DataLinkDefine linkDefine;
    private ColumnModelDefine columnModel;
    private IColumnType columnType;

    @Override
    public DataField getDataField() {
        return this.dataField;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
        switch (dataField.getDataFieldKind()) {
            case FIELD_ZB: 
            case FIELD: {
                this.columnType = IColumnType.normal_column;
                break;
            }
            case TABLE_FIELD_DIM: {
                this.columnType = IColumnType.bizKey_column;
            }
        }
    }

    @Override
    public DataLinkDefine getLinkDefine() {
        return this.linkDefine;
    }

    public void setLinkDefine(DataLinkDefine linkDefine) {
        this.linkDefine = linkDefine;
    }

    @Override
    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    @Override
    public String getColumnName() {
        if (this.columnType == IColumnType.bizKey_column) {
            return this.columnModel.getCode();
        }
        return this.columnModel.getID();
    }

    @Override
    public IColumnType getColumnType() {
        return this.columnType;
    }
}

