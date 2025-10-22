/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.ITableCellValue;
import com.jiuqi.nr.data.estimation.service.dataio.ITableDataSet;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableCellValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class TableDataSet
implements ITableDataSet {
    private static final long serialVersionUID = 8601052734209279491L;
    private String[] columns;
    private ITableCellValue[][] dataTable;
    private Map<String, Integer> colIdxMap;

    public TableDataSet() {
        this(new String[0]);
    }

    public TableDataSet(String[] columns) {
        this.columns = columns;
        this.dataTable = new ITableCellValue[0][columns.length];
        this.colIdxMap = this.getColumnIndexMap(columns);
    }

    @Override
    public boolean isEmpty() {
        return this.dataTable.length == 0;
    }

    @Override
    public int getRowCount() {
        return this.dataTable.length;
    }

    @Override
    public String[] getColumns() {
        return this.columns != null ? this.columns : new String[]{};
    }

    @Override
    public ITableCellValue getCellValue(int rowIdx, String column) {
        if (rowIdx < 0 || rowIdx >= this.dataTable.length) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15" + rowIdx);
        }
        Integer colIdx = this.colIdxMap.get(column);
        if (colIdx == null) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5217\u5b9a\u4e49: " + column);
        }
        return this.dataTable[rowIdx][colIdx];
    }

    @Override
    public Iterator<ITableCellValue[]> rowIterator() {
        return new RowIterator();
    }

    @Override
    public Iterator<ITableCellValue[]> columnIterator() {
        return new ColumnIterator();
    }

    public void appendRows(int rowCount) {
        if (rowCount <= 0) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u6269\u5c55\u884c\u6570: " + rowCount);
        }
        this.dataTable = (ITableCellValue[][])Arrays.copyOf(this.dataTable, this.dataTable.length + rowCount);
        for (int rowIdx = this.dataTable.length - rowCount; rowIdx < this.dataTable.length; ++rowIdx) {
            this.dataTable[rowIdx] = new ITableCellValue[this.columns.length];
        }
    }

    public void appendCols(String[] newColumns) {
        if (newColumns == null || newColumns.length == 0) {
            return;
        }
        List<String> cs = Arrays.asList(this.columns);
        ArrayList<String> ncs = new ArrayList<String>();
        for (String column : newColumns) {
            if (cs.contains(column)) continue;
            ncs.add(column);
        }
        newColumns = ncs.toArray(new String[0]);
        this.columns = Arrays.copyOf(this.columns, this.columns.length + newColumns.length);
        System.arraycopy(newColumns, 0, this.columns, this.columns.length - newColumns.length, newColumns.length);
        this.colIdxMap = this.getColumnIndexMap(this.columns);
        ITableCellValue[][] newData = new ITableCellValue[this.dataTable.length][this.columns.length];
        for (int i = 0; i < this.dataTable.length; ++i) {
            System.arraycopy(this.dataTable[i], 0, newData[i], 0, this.dataTable[i].length);
        }
        this.dataTable = newData;
    }

    public void setCellValue(int rowIdx, String column, ITableCellValue value) {
        if (rowIdx < 0) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15: " + rowIdx);
        }
        Integer colIdx = this.colIdxMap.get(column);
        if (colIdx == null) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5217\u5b9a\u4e49: " + column);
        }
        if (rowIdx >= this.dataTable.length) {
            this.appendRows(rowIdx - this.dataTable.length + 1);
        }
        this.dataTable[rowIdx][colIdx.intValue()] = value;
    }

    public void setCellValue(int rowIdx, String column, Object newCellValue) {
        this.setCellValue(rowIdx, column, TableCellValue.getInstance(newCellValue));
    }

    public void setCellValue(int rowIdx, String column, Object newCellValue, Object oldCellValue) {
        this.setCellValue(rowIdx, column, TableCellValue.getInstance(newCellValue, oldCellValue));
    }

    private Map<String, Integer> getColumnIndexMap(String[] columns) {
        HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < columns.length; ++i) {
            columnIndexMap.put(columns[i], i);
        }
        return columnIndexMap;
    }

    public void toString(StringBuilder info) {
        for (String columnName : this.columns) {
            info.append(" | ");
            info.append(columnName);
        }
        info.append('\n');
        Iterator<ITableCellValue[]> iterator = this.rowIterator();
        while (iterator.hasNext()) {
            ITableCellValue[] nextRow;
            for (ITableCellValue cellValue : nextRow = iterator.next()) {
                info.append(" | ");
                info.append("[");
                info.append(cellValue.getOldValue());
                info.append(",");
                info.append(cellValue.getNewValue());
                info.append("]");
            }
            info.append('\n');
        }
    }

    private class ColumnIterator
    implements Iterator<ITableCellValue[]> {
        private int columnIndex = 0;

        private ColumnIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.columnIndex < TableDataSet.this.columns.length;
        }

        @Override
        public ITableCellValue[] next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            ITableCellValue[] columnData = new ITableCellValue[TableDataSet.this.dataTable.length];
            for (int i = 0; i < TableDataSet.this.dataTable.length; ++i) {
                columnData[i] = TableDataSet.this.dataTable[i][this.columnIndex];
            }
            ++this.columnIndex;
            return columnData;
        }
    }

    private class RowIterator
    implements Iterator<ITableCellValue[]> {
        private int rowIndex = 0;

        private RowIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.rowIndex < TableDataSet.this.dataTable.length;
        }

        @Override
        public ITableCellValue[] next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return TableDataSet.this.dataTable[this.rowIndex++];
        }
    }
}

