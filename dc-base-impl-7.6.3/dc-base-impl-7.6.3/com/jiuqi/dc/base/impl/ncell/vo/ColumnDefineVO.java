/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.ncell.vo;

import com.jiuqi.dc.base.impl.ncell.vo.ColumnValueType;

public class ColumnDefineVO {
    private String columnCode;
    private String columnName;
    private ColumnValueType valueType;
    private int length;
    private int getDigits;
    private String refTableName;

    public String getColumnCode() {
        return this.columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColumnValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ColumnValueType valueType) {
        this.valueType = valueType;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getGetDigits() {
        return this.getDigits;
    }

    public void setGetDigits(int getDigits) {
        this.getDigits = getDigits;
    }

    public String getRefTableName() {
        return this.refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }
}

