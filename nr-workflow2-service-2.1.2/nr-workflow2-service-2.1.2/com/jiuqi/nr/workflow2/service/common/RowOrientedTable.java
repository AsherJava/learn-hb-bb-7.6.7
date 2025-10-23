/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.service.common.AbstractStringTable;
import java.util.Iterator;

public class RowOrientedTable
extends AbstractStringTable {
    public RowOrientedTable(String[] colNames, int rowCount) {
        super(colNames, rowCount);
    }

    @Override
    protected String[][] initTable(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        return new String[this.colCount][this.rowCount];
    }

    @Override
    protected void addRowCount(int incRowCount) {
        String[][] newTable = new String[this.colCount][this.rowCount + incRowCount];
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            System.arraycopy(this.table[rowIdx], 0, newTable[rowIdx], 0, this.table[rowIdx].length);
        }
        this.rowCount += incRowCount;
        this.table = newTable;
    }

    @Override
    protected void addColCount(int incColCount) {
        String[][] newTable = new String[this.colCount + incColCount][this.rowCount];
        System.arraycopy(this.table, 0, newTable, 0, this.colCount);
        this.table = newTable;
        this.colCount += incColCount;
    }

    @Override
    protected void insertRowData(int rowIndex, String[] insRowData) {
        String[][] newTable = new String[this.colCount][this.rowCount + 1];
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            System.arraycopy(this.table[rowIdx], 0, newTable[rowIdx], 0, rowIndex);
            System.arraycopy(this.table[rowIdx], rowIndex, newTable[rowIdx], rowIndex + 1, this.rowCount - rowIndex);
            newTable[rowIdx][rowIndex] = insRowData[rowIdx];
        }
        ++this.rowCount;
        this.table = newTable;
    }

    @Override
    protected void updateRowData(int rowIndex, String[] uptRowData) {
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            this.table[rowIdx][rowIndex] = uptRowData[rowIdx];
        }
    }

    @Override
    protected String[] deleteRowData(int rowIndex) {
        String[][] newTable = new String[this.colCount][this.rowCount - 1];
        String[] removeRowData = new String[this.colCount];
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            System.arraycopy(this.table[rowIdx], 0, newTable[rowIdx], 0, rowIndex);
            System.arraycopy(this.table[rowIdx], rowIndex + 1, newTable[rowIdx], rowIndex, this.rowCount - rowIndex - 1);
            removeRowData[rowIdx] = this.table[rowIdx][rowIndex];
        }
        --this.rowCount;
        this.table = newTable;
        return removeRowData;
    }

    @Override
    protected String[] findColData(int colIndex) {
        String[] colData = new String[this.rowCount];
        System.arraycopy(this.table[colIndex], 0, colData, 0, this.rowCount);
        return colData;
    }

    @Override
    protected void insertColData(int colIndex, String[] insertColData) {
        int i;
        String[][] newTable = new String[this.colCount + 1][this.rowCount];
        for (i = 0; i < colIndex; ++i) {
            System.arraycopy(this.table[i], 0, newTable[i], 0, this.rowCount);
        }
        newTable[colIndex] = insertColData;
        for (i = colIndex; i < this.colCount; ++i) {
            System.arraycopy(this.table[i], 0, newTable[i + 1], 0, this.rowCount);
        }
        ++this.colCount;
        this.table = newTable;
    }

    @Override
    protected void updateColData(int colIndex, String[] updateColData) {
        this.table[colIndex] = updateColData;
    }

    @Override
    protected String[] removeColData(int colIndex) {
        String[] removeRowData = this.table[colIndex];
        String[][] newTable = new String[this.colCount - 1][this.rowCount];
        System.arraycopy(this.table, 0, newTable, 0, colIndex);
        System.arraycopy(this.table, colIndex + 1, newTable, colIndex, this.colCount - colIndex - 1);
        --this.colCount;
        this.table = newTable;
        return removeRowData;
    }

    @Override
    protected void setCellValue(int rowIndex, int colIndex, String value) {
        this.table[colIndex][rowIndex] = value;
    }

    @Override
    public String getCellValue(int rowIdx, int colIdx) {
        return this.table[colIdx][rowIdx];
    }

    @Override
    protected AbstractStringTable newSubStringTable(String[] colNames, int rowCount) {
        return new RowOrientedTable(colNames, rowCount);
    }

    @Override
    public String[] findRowData(int rowIndex) {
        String[] rowData = new String[this.colCount];
        for (int idx = 0; idx < this.colCount; ++idx) {
            rowData[idx] = this.table[idx][rowIndex];
        }
        return rowData;
    }

    @Override
    public Iterator<String[]> rowIterator() {
        return new AbstractStringTable.ColumnIterator(this, this.colCount, this.rowCount);
    }

    @Override
    public Iterator<String[]> columnIterator() {
        return new AbstractStringTable.RowIterator(this, this.colCount);
    }
}

