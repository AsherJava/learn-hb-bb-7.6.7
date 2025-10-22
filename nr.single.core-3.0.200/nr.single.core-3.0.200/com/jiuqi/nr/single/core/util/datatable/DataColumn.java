/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util.datatable;

import com.jiuqi.nr.single.core.util.datatable.DataTable;

public class DataColumn {
    private boolean readOnly;
    private DataTable table;
    private String columnName;
    private String mapColumnName;
    private String captionName;
    private int columnIndex;
    private int dataType;
    private String dataTypeName;
    private char dataTypeChar;
    private int precision;
    private int decimal;

    public DataColumn() {
        this("default1");
    }

    public DataColumn(int dataType) {
        this("default1", dataType);
    }

    public DataColumn(String columnName) {
        this(columnName, 0);
    }

    public DataColumn(String columnName, int dataType) {
        this.setDataType(dataType);
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCaptionName() {
        return this.captionName;
    }

    public void setCaptionName(String captionName) {
        this.captionName = captionName;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public String getDataTypeName() {
        return this.dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public Object convertTo(Object value) {
        return value;
    }

    public String toString() {
        return this.columnName;
    }

    public String getMapColumnName() {
        return this.mapColumnName;
    }

    public void setMapColumnName(String mapColumnName) {
        this.mapColumnName = mapColumnName;
    }

    public char getDataTypeChar() {
        return this.dataTypeChar;
    }

    public void setDataTypeChar(char dataTypeChar) {
        this.dataTypeChar = dataTypeChar;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }
}

