/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.grid;

public class StringGrid {
    private int colCount = 1;
    private int rowCount = 1;
    private int row;
    private int col;
    private String[][] data;

    public String getCells(int aCol, int aRow) {
        return this.data[aRow][aCol];
    }

    public void setCells(int aCol, int aRow, String value) {
        this.data[aRow][aCol] = value;
    }

    public void resetColRowCounts(int aColCount, int aRowCount) {
        this.colCount = aColCount;
        this.rowCount = aRowCount;
        this.data = new String[aRowCount][aColCount];
    }

    public int getColCount() {
        return this.colCount;
    }

    public void setColCount(int colCount) {
        this.resetColRowCounts(colCount, this.rowCount);
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.resetColRowCounts(this.colCount, rowCount);
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}

