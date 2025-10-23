/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateTable;
import java.util.Iterator;

public class RowOrientedOperateTable
extends EventOperateTable {
    public RowOrientedOperateTable(String[] colNames, int rowCount) {
        super(colNames, rowCount);
    }

    @Override
    protected IEventOperateInfo[][] initTable(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        return new IEventOperateInfo[this.colCount][this.rowCount];
    }

    @Override
    protected void addRowCount(int incRowCount) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.colCount][this.rowCount + incRowCount];
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            System.arraycopy(this.table[rowIdx], 0, newTable[rowIdx], 0, this.table[rowIdx].length);
        }
        this.rowCount += incRowCount;
        this.table = newTable;
    }

    @Override
    protected void addColCount(int incColCount) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.colCount + incColCount][this.rowCount];
        System.arraycopy(this.table, 0, newTable, 0, this.colCount);
        this.table = newTable;
        this.colCount += incColCount;
    }

    @Override
    protected void insertRowData(int rowIndex, IEventOperateInfo[] insRowData) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.colCount][this.rowCount + 1];
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            System.arraycopy(this.table[rowIdx], 0, newTable[rowIdx], 0, rowIndex);
            System.arraycopy(this.table[rowIdx], rowIndex, newTable[rowIdx], rowIndex + 1, this.rowCount - rowIndex);
            newTable[rowIdx][rowIndex] = insRowData[rowIdx];
        }
        ++this.rowCount;
        this.table = newTable;
    }

    @Override
    protected void updateRowData(int rowIndex, IEventOperateInfo[] uptRowData) {
        for (int rowIdx = 0; rowIdx < this.colCount; ++rowIdx) {
            this.table[rowIdx][rowIndex] = uptRowData[rowIdx];
        }
    }

    @Override
    protected IEventOperateInfo[] deleteRowData(int rowIndex) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.colCount][this.rowCount - 1];
        IEventOperateInfo[] removeRowData = new IEventOperateInfo[this.colCount];
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
    protected IEventOperateInfo[] findColData(int colIndex) {
        IEventOperateInfo[] colData = new IEventOperateInfo[this.rowCount];
        System.arraycopy(this.table[colIndex], 0, colData, 0, this.rowCount);
        return colData;
    }

    @Override
    protected void insertColData(int colIndex, IEventOperateInfo[] insertColData) {
        int i;
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.colCount + 1][this.rowCount];
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
    protected void updateColData(int colIndex, IEventOperateInfo[] updateColData) {
        this.table[colIndex] = updateColData;
    }

    @Override
    protected IEventOperateInfo[] removeColData(int colIndex) {
        IEventOperateInfo[] removeRowData = this.table[colIndex];
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.colCount - 1][this.rowCount];
        System.arraycopy(this.table, 0, newTable, 0, colIndex);
        System.arraycopy(this.table, colIndex + 1, newTable, colIndex, this.colCount - colIndex - 1);
        --this.colCount;
        this.table = newTable;
        return removeRowData;
    }

    @Override
    protected void setCellValue(int rowIndex, int colIndex, IEventOperateInfo value) {
        this.table[colIndex][rowIndex] = value;
    }

    @Override
    public IEventOperateInfo getCellValue(int rowIdx, int colIdx) {
        return this.table[colIdx][rowIdx];
    }

    @Override
    protected EventOperateTable newSubStringTable(String[] colNames, int rowCount) {
        return new RowOrientedOperateTable(colNames, rowCount);
    }

    @Override
    public IEventOperateInfo[] findRowData(int rowIndex) {
        IEventOperateInfo[] rowData = new IEventOperateInfo[this.colCount];
        for (int idx = 0; idx < this.colCount; ++idx) {
            rowData[idx] = this.table[idx][rowIndex];
        }
        return rowData;
    }

    @Override
    public Iterator<IEventOperateInfo[]> rowIterator() {
        return new EventOperateTable.ColumnIterator(this.colCount, this.rowCount);
    }

    @Override
    public Iterator<IEventOperateInfo[]> columnIterator() {
        return new EventOperateTable.RowIterator(this.colCount);
    }
}

