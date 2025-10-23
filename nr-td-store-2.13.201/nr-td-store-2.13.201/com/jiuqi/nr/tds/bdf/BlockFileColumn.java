/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nvwa.memdb.api.model.DBColumn
 */
package com.jiuqi.nr.tds.bdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nvwa.memdb.api.model.DBColumn;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BlockFileColumn
extends TdColumn {
    private String columnName;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public BlockFileColumn() {
    }

    @Override
    public DBColumn toDBColumn() {
        DBColumn dbColumn = new DBColumn();
        dbColumn.setName(this.getColumnName());
        dbColumn.setDataType(this.getDataType());
        if (this.getDataType() == 2) {
            dbColumn.setPrecision(14);
        } else if (this.getDataType() == 6) {
            dbColumn.setPrecision(this.getPrecision() * 4);
        } else {
            dbColumn.setPrecision(this.getPrecision());
        }
        dbColumn.setScale(this.getScale());
        return dbColumn;
    }

    public BlockFileColumn(TdColumn column) {
        this.name = column.getName();
        this.dataType = column.getDataType();
        this.precision = column.getPrecision();
        this.scale = column.getScale();
        this.nullable = column.isNullable();
        this.columnName = column.getName().contains(".") ? column.getName().replace(".", "_") : column.getName();
    }
}

