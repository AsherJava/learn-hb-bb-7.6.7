/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.service.common.AbstractStringTable;
import java.util.Iterator;

public class ColumnOrientedTable
extends AbstractStringTable {
    public ColumnOrientedTable(String[] colNames, int rowCount) {
        super(colNames, rowCount);
    }

    @Override
    protected String[][] initTable(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        return new String[this.rowCount][this.colCount];
    }

    @Override
    protected void addRowCount(int incRowCount) {
        String[][] newTable = new String[this.rowCount + incRowCount][this.colCount];
        System.arraycopy(this.table, 0, newTable, 0, this.rowCount);
        this.table = newTable;
        this.rowCount += incRowCount;
    }

    @Override
    protected void addColCount(int incColCount) {
        String[][] newTable = new String[this.rowCount][this.colCount + incColCount];
        for (int i = 0; i < this.rowCount; ++i) {
            System.arraycopy(this.table[i], 0, newTable[i], 0, this.table[i].length);
        }
        this.table = newTable;
        this.colCount += incColCount;
    }

    @Override
    protected void insertRowData(int rowIndex, String[] insRowData) {
        String[][] newTable = new String[this.rowCount + 1][this.colCount];
        System.arraycopy(this.table, 0, newTable, 0, rowIndex);
        System.arraycopy(this.table, rowIndex, newTable, rowIndex + 1, this.rowCount - rowIndex);
        newTable[rowIndex] = insRowData;
        ++this.rowCount;
        this.table = newTable;
    }

    @Override
    protected void updateRowData(int rowIndex, String[] uptRowData) {
        System.arraycopy(uptRowData, 0, this.table[rowIndex], 0, this.colCount);
    }

    @Override
    protected String[] deleteRowData(int rowIndex) {
        String[] delRowData = this.table[rowIndex];
        String[][] newTable = new String[this.rowCount - 1][this.colCount];
        System.arraycopy(this.table, 0, newTable, 0, rowIndex);
        System.arraycopy(this.table, rowIndex + 1, newTable, rowIndex, this.rowCount - rowIndex - 1);
        --this.rowCount;
        this.table = newTable;
        return delRowData;
    }

    @Override
    protected void insertColData(int colIndex, String[] insertColData) {
        String[][] newTable = new String[this.rowCount][this.colCount + 1];
        for (int i = 0; i < this.rowCount; ++i) {
            newTable[i][colIndex] = insertColData[i];
            System.arraycopy(this.table[i], 0, newTable[i], 0, colIndex);
            System.arraycopy(this.table[i], colIndex, newTable[i], colIndex + 1, this.colCount - colIndex);
        }
        ++this.colCount;
        this.table = newTable;
    }

    @Override
    protected void updateColData(int colIndex, String[] updateColData) {
        for (int i = 0; i < this.rowCount; ++i) {
            this.table[i][colIndex] = updateColData[i];
        }
    }

    @Override
    protected String[] removeColData(int colIndex) {
        String[] removeRowData = new String[this.rowCount];
        String[][] newTable = new String[this.rowCount][this.colCount - 1];
        for (int i = 0; i < this.rowCount; ++i) {
            System.arraycopy(this.table[i], 0, newTable[i], 0, colIndex);
            removeRowData[i] = this.table[i][colIndex];
            System.arraycopy(this.table[i], colIndex + 1, newTable[i], colIndex, this.colCount - colIndex - 1);
        }
        --this.colCount;
        this.table = newTable;
        return removeRowData;
    }

    @Override
    protected void setCellValue(int rowIndex, int colIdx, String value) {
        this.table[rowIndex][colIdx] = value;
    }

    @Override
    protected String getCellValue(int rowIdx, int colIdx) {
        return this.table[rowIdx][colIdx];
    }

    @Override
    public String[] findRowData(int rowIndex) {
        String[] rowData = new String[this.colCount];
        System.arraycopy(this.table[rowIndex], 0, rowData, 0, this.colCount);
        return rowData;
    }

    @Override
    public String[] findColData(int colIndex) {
        String[] colData = new String[this.rowCount];
        for (int i = 0; i < this.rowCount; ++i) {
            colData[i] = this.table[i][colIndex];
        }
        return colData;
    }

    @Override
    protected AbstractStringTable newSubStringTable(String[] colNames, int rowCount) {
        return new ColumnOrientedTable(colNames, rowCount);
    }

    @Override
    public Iterator<String[]> rowIterator() {
        return new AbstractStringTable.RowIterator(this, this.rowCount);
    }

    @Override
    public Iterator<String[]> columnIterator() {
        return new AbstractStringTable.ColumnIterator(this, this.rowCount, this.colCount);
    }
}

