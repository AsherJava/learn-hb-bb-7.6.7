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

public class ColumnOrientedOperateTable
extends EventOperateTable {
    public ColumnOrientedOperateTable(String[] colNames, int rowCount) {
        super(colNames, rowCount);
    }

    @Override
    protected IEventOperateInfo[][] initTable(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        return new IEventOperateInfo[this.rowCount][this.colCount];
    }

    @Override
    protected void addRowCount(int incRowCount) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.rowCount + incRowCount][this.colCount];
        System.arraycopy(this.table, 0, newTable, 0, this.rowCount);
        this.table = newTable;
        this.rowCount += incRowCount;
    }

    @Override
    protected void addColCount(int incColCount) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.rowCount][this.colCount + incColCount];
        for (int i = 0; i < this.rowCount; ++i) {
            System.arraycopy(this.table[i], 0, newTable[i], 0, this.table[i].length);
        }
        this.table = newTable;
        this.colCount += incColCount;
    }

    @Override
    protected void insertRowData(int rowIndex, IEventOperateInfo[] insRowData) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.rowCount + 1][this.colCount];
        System.arraycopy(this.table, 0, newTable, 0, rowIndex);
        System.arraycopy(this.table, rowIndex, newTable, rowIndex + 1, this.rowCount - rowIndex);
        newTable[rowIndex] = insRowData;
        ++this.rowCount;
        this.table = newTable;
    }

    @Override
    protected void updateRowData(int rowIndex, IEventOperateInfo[] uptRowData) {
        System.arraycopy(uptRowData, 0, this.table[rowIndex], 0, this.colCount);
    }

    @Override
    protected IEventOperateInfo[] deleteRowData(int rowIndex) {
        IEventOperateInfo[] delRowData = this.table[rowIndex];
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.rowCount - 1][this.colCount];
        System.arraycopy(this.table, 0, newTable, 0, rowIndex);
        System.arraycopy(this.table, rowIndex + 1, newTable, rowIndex, this.rowCount - rowIndex - 1);
        --this.rowCount;
        this.table = newTable;
        return delRowData;
    }

    @Override
    protected void insertColData(int colIndex, IEventOperateInfo[] insertColData) {
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.rowCount][this.colCount + 1];
        for (int i = 0; i < this.rowCount; ++i) {
            newTable[i][colIndex] = insertColData[i];
            System.arraycopy(this.table[i], 0, newTable[i], 0, colIndex);
            System.arraycopy(this.table[i], colIndex, newTable[i], colIndex + 1, this.colCount - colIndex);
        }
        ++this.colCount;
        this.table = newTable;
    }

    @Override
    protected void updateColData(int colIndex, IEventOperateInfo[] updateColData) {
        for (int i = 0; i < this.rowCount; ++i) {
            this.table[i][colIndex] = updateColData[i];
        }
    }

    @Override
    protected IEventOperateInfo[] removeColData(int colIndex) {
        IEventOperateInfo[] removeRowData = new IEventOperateInfo[this.rowCount];
        IEventOperateInfo[][] newTable = new IEventOperateInfo[this.rowCount][this.colCount - 1];
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
    protected void setCellValue(int rowIndex, int colIdx, IEventOperateInfo value) {
        this.table[rowIndex][colIdx] = value;
    }

    @Override
    protected IEventOperateInfo getCellValue(int rowIdx, int colIdx) {
        return this.table[rowIdx][colIdx];
    }

    @Override
    public IEventOperateInfo[] findRowData(int rowIndex) {
        IEventOperateInfo[] rowData = new IEventOperateInfo[this.colCount];
        System.arraycopy(this.table[rowIndex], 0, rowData, 0, this.colCount);
        return rowData;
    }

    @Override
    public IEventOperateInfo[] findColData(int colIndex) {
        IEventOperateInfo[] colData = new IEventOperateInfo[this.rowCount];
        for (int i = 0; i < this.rowCount; ++i) {
            colData[i] = this.table[i][colIndex];
        }
        return colData;
    }

    @Override
    protected EventOperateTable newSubStringTable(String[] colNames, int rowCount) {
        return new ColumnOrientedOperateTable(colNames, rowCount);
    }

    @Override
    public Iterator<IEventOperateInfo[]> rowIterator() {
        return new EventOperateTable.RowIterator(this.rowCount);
    }

    @Override
    public Iterator<IEventOperateInfo[]> columnIterator() {
        return new EventOperateTable.ColumnIterator(this.rowCount, this.colCount);
    }
}

