/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print;

import com.jiuqi.va.query.print.TableCellProp;

public class TableCellData {
    private String cellData;
    private int rowIndex;
    private int columnIndex;
    private int spanRow;
    private int spanColumn;
    private TableCellProp cellProp;

    public TableCellData(String cellData, TableCellProp cellProp) {
        this.cellData = cellData;
        this.cellProp = cellProp;
        this.setCellProp(cellProp);
    }

    public TableCellData(String cellData) {
        this.cellData = cellData;
    }

    public TableCellData() {
    }

    public void setCellProp(TableCellProp cellProp) {
        this.cellProp = cellProp;
        this.columnIndex = cellProp.getColumnIndex();
        this.rowIndex = cellProp.getRowIndex();
        this.spanColumn = cellProp.getSpanColumn();
        this.spanRow = cellProp.getSpanRow();
    }

    public String getCellData() {
        return this.cellData;
    }

    public void setCellData(String cellData) {
        this.cellData = cellData;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getSpanRow() {
        return this.spanRow;
    }

    public void setSpanRow(int spanRow) {
        this.spanRow = spanRow;
    }

    public int getSpanColumn() {
        return this.spanColumn;
    }

    public void setSpanColumn(int spanColumn) {
        this.spanColumn = spanColumn;
    }

    public TableCellProp getCellProp() {
        return this.cellProp;
    }
}

