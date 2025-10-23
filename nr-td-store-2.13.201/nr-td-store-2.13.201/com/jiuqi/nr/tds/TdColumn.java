/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.nvwa.memdb.api.model.DBColumn
 */
package com.jiuqi.nr.tds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.nvwa.memdb.api.model.DBColumn;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TdColumn {
    protected String name;
    protected int dataType;
    protected int precision;
    protected int scale = -1;
    protected boolean nullable = true;

    public TdColumn() {
    }

    public TdColumn(String name, int dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public TdColumn(String name, int dataType, int size) {
        this.name = name;
        this.dataType = dataType;
        this.precision = size;
    }

    public TdColumn(String name, int dataType, boolean nullable) {
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
    }

    public TdColumn(String name, int dataType, int precision, int scale) {
        this.name = name;
        this.dataType = dataType;
        this.precision = precision;
        this.scale = scale;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public DBColumn toDBColumn() {
        DBColumn dbColumn = new DBColumn();
        dbColumn.setName(this.getName());
        dbColumn.setDataType(this.getDataType());
        dbColumn.setPrecision(this.getPrecision());
        dbColumn.setScale(this.getScale());
        return dbColumn;
    }

    public static TdColumn of(DBColumn dbColumn) {
        return new TdColumn(dbColumn.getName(), dbColumn.getDataType(), dbColumn.getPrecision(), dbColumn.getScale());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Column[").append("name='").append(this.name).append("'").append(", type=").append(DataTypes.dataTypeToString((int)this.dataType));
        if (this.precision > 0) {
            sb.append(",  precision=").append(this.precision);
            if (this.scale >= 0) {
                sb.append("(").append(this.scale).append(")");
            }
        }
        sb.append(",  nullable=").append(this.nullable).append("]");
        return sb.toString();
    }
}

