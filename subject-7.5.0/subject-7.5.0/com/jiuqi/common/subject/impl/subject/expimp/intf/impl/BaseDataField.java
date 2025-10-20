/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf.impl;

import com.jiuqi.common.subject.impl.subject.data.DataType;
import com.jiuqi.va.domain.datamodel.DataModelType;

public class BaseDataField {
    private String columnName;
    private String columnTitle;
    private String columnType;
    private Boolean required;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnTitle() {
        return this.columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public DataType getDataType() {
        DataType dataType = null;
        if (DataModelType.ColumnType.UUID.equals((Object)this.columnType) || DataModelType.ColumnType.NVARCHAR.equals((Object)this.columnType) || DataModelType.ColumnType.CLOB.equals((Object)this.columnType)) {
            dataType = DataType.STRING;
        } else if (DataModelType.ColumnType.INTEGER.equals((Object)this.columnType)) {
            dataType = DataType.INT;
        } else if (DataModelType.ColumnType.NUMERIC.equals((Object)this.columnType)) {
            dataType = DataType.DOUBLE;
        } else if (DataModelType.ColumnType.DATE.equals((Object)this.columnType)) {
            dataType = DataType.DATE;
        } else if (DataModelType.ColumnType.TIMESTAMP.equals((Object)this.columnType)) {
            dataType = DataType.DATETIME;
        }
        return dataType;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}

