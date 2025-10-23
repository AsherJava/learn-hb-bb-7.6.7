/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateTable;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class EventOperateTable
implements IEventOperateTable {
    private String[] colNames;
    protected int rowCount;
    protected int colCount;
    protected IEventOperateInfo[][] table;
    protected Map<String, Integer> colIdxMap;

    public EventOperateTable(String[] colNames, int rowCount) {
        if (rowCount < 0) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u884c\u5b9a\u4e49\uff1a" + rowCount + "\uff0c\u884c\u6570rowCount\u4e0d\u80fd\u5c0f\u4e8e0\u6216\u7b49\u4e8e0\uff01");
        }
        this.colNames = this.checkColNames(colNames);
        this.colIdxMap = this.resetColumnIndexMap(this.colNames);
        this.table = this.initTable(rowCount, this.colNames.length);
    }

    protected abstract IEventOperateInfo[][] initTable(int var1, int var2);

    @Override
    public void incrementRowCount(int incRowCount) {
        if (incRowCount <= 0) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u6269\u5c55\u884c\u6570\uff1a\u6269\u5c55\u884c\u6570" + incRowCount + "\u4e0d\u80fd\u5c0f\u4e8e0\u6216\u7b49\u4e8e0\uff01");
        }
        this.addRowCount(incRowCount);
    }

    protected abstract void addRowCount(int var1);

    @Override
    public void incrementColNames(String[] incColNames) {
        if (incColNames == null) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u6269\u5c55\u5217\u540d\uff1a\u6269\u5c55\u5217\u540dincColNames\uff0c\u4e0d\u80fd\u4e3anull\uff01");
        }
        String[] validIncColNames = this.checkColNames(incColNames);
        if (validIncColNames.length == 0) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u6269\u5c55\u5217\u540d\uff1a\u6269\u5c55\u5217\u540d" + Arrays.toString(incColNames) + "\uff0c\u5728\u5b9a\u4e49\u4e2d\u5df2\u5b58\u5728\uff01");
        }
        String[] newColNames = new String[this.colNames.length + validIncColNames.length];
        System.arraycopy(this.colNames, 0, newColNames, 0, this.colNames.length);
        System.arraycopy(validIncColNames, 0, newColNames, this.colNames.length, validIncColNames.length);
        this.colNames = newColNames;
        this.colIdxMap = this.resetColumnIndexMap(this.colNames);
        this.addColCount(validIncColNames.length);
    }

    protected abstract void addColCount(int var1);

    @Override
    public void insertRowData(int rowIndex, Map<String, IEventOperateInfo> insRowData) {
        if (rowIndex < 0 || rowIndex > this.rowCount) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15\uff1a\u884c\u7d22\u5f15" + rowIndex + "\uff0c\u8d8a\u754c\uff01");
        }
        if (insRowData == null || insRowData.isEmpty()) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u884c\u6570\u636e\uff1a\u63d2\u5165\u884c\u6570\u636einsRowData\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        this.insertRowData(rowIndex, this.checkInsertRowData(insRowData));
    }

    protected abstract void insertRowData(int var1, IEventOperateInfo[] var2);

    @Override
    public void updateRowData(int rowIndex, Map<String, IEventOperateInfo> uptRowData) {
        if (rowIndex < 0 || rowIndex >= this.rowCount) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15\uff1a\u884c\u7d22\u5f15" + rowIndex + "\uff0c\u8d8a\u754c\uff01");
        }
        if (uptRowData == null || uptRowData.isEmpty()) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u884c\u6570\u636e\uff1a\u884c\u6570\u636einsRowData\u4e0d\u80fd\u4e3anull\uff01");
        }
        this.updateRowData(rowIndex, this.checkUpdateRowData(rowIndex, uptRowData));
    }

    protected abstract void updateRowData(int var1, IEventOperateInfo[] var2);

    @Override
    public IEventOperateInfo[] removeRowData(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= this.rowCount) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15\uff1a\u884c\u7d22\u5f15" + rowIndex + "\uff0c\u8d8a\u754c\uff01");
        }
        return this.deleteRowData(rowIndex);
    }

    protected abstract IEventOperateInfo[] deleteRowData(int var1);

    @Override
    public IEventOperateInfo[] getRowData(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= this.rowCount) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15\uff1a\u884c\u7d22\u5f15" + rowIndex + "\uff0c\u8d8a\u754c\uff01");
        }
        return this.findRowData(rowIndex);
    }

    protected abstract IEventOperateInfo[] findRowData(int var1);

    @Override
    public IEventOperateInfo[] getColData(String colName) {
        if (!this.colIdxMap.containsKey(colName)) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5217\u540d\uff1a\u5217\u540d\u2018" + colName + "\u2019\u4e0d\u5b58\u5728\uff01");
        }
        return this.findColData(this.colIdxMap.get(colName));
    }

    protected abstract IEventOperateInfo[] findColData(int var1);

    @Override
    public void insertColData(String coverColName, String insertColName, IEventOperateInfo[] insertColData) {
        if (!this.colIdxMap.containsKey(coverColName) && !coverColName.equals(insertColName)) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u63d2\u5165\u4f4d\u7f6e\uff1a\u63d2\u5165\u4f4d\u7f6e\u5217\u2018" + coverColName + "\u2019\u4e0d\u5b58\u5728\uff01");
        }
        if (this.colIdxMap.containsKey(insertColName)) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u63d2\u5165\u5217\u540d\uff1a\u63d2\u5165\u5217\u2018" + insertColName + "\u2019\u5217\u540d\u5df2\u5b58\u5728\uff01");
        }
        if (insertColData.length != this.rowCount) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u63d2\u5165\u5217\u6570\u636e\uff1a\u63d2\u5165\u5217\u6570\u636e\u7684\u957f\u5ea6\u548c\u603b\u884c\u6570\u4e0d\u5339\u914d\uff01\u603b\u884c\u6570\uff1a" + this.rowCount + "\uff0c\u63d2\u5165\u5217\u957f\u5ea6\uff1a" + insertColData.length + "\u3002");
        }
        int colIndex = coverColName.equals(insertColName) ? this.colCount : this.colIdxMap.get(coverColName);
        String[] newColumnNames = new String[this.colCount + 1];
        System.arraycopy(this.colNames, 0, newColumnNames, 0, colIndex);
        System.arraycopy(this.colNames, colIndex, newColumnNames, colIndex + 1, this.colCount - colIndex);
        newColumnNames[colIndex] = insertColName;
        this.colNames = newColumnNames;
        this.colIdxMap = this.resetColumnIndexMap(this.colNames);
        this.insertColData(colIndex, insertColData);
    }

    protected abstract void insertColData(int var1, IEventOperateInfo[] var2);

    @Override
    public void updateColData(String updateColName, IEventOperateInfo[] updateColData) {
        if (!this.colIdxMap.containsKey(updateColName)) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u66f4\u65b0\u5217\u540d\uff1a\u63d2\u5165\u5217\u2018" + updateColName + "\u2019\u5217\u540d\u4e0d\u5b58\u5728\uff01");
        }
        if (updateColData.length != this.rowCount) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u66f4\u65b0\u5217\u6570\u636e\uff1a\u66f4\u65b0\u5217\u6570\u636e\u548c\u603b\u884c\u6570\u4e0d\u5339\u914d\uff01");
        }
        this.updateColData(this.colIdxMap.get(updateColName), updateColData);
    }

    protected abstract void updateColData(int var1, IEventOperateInfo[] var2);

    @Override
    public IEventOperateInfo[] removeColData(String colName) {
        if (!this.colIdxMap.containsKey(colName)) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5220\u9664\u5217\u540d\uff1a\u5220\u9664\u5217\u2018" + colName + "\u2019\u5217\u540d\u4e0d\u5b58\u5728\uff01");
        }
        int colIndex = this.colIdxMap.get(colName);
        String[] newColumnNames = new String[this.colCount - 1];
        System.arraycopy(this.colNames, 0, newColumnNames, 0, colIndex);
        System.arraycopy(this.colNames, colIndex + 1, newColumnNames, colIndex, this.colCount - colIndex - 1);
        this.colNames = newColumnNames;
        this.colIdxMap = this.resetColumnIndexMap(this.colNames);
        return this.removeColData(colIndex);
    }

    protected abstract IEventOperateInfo[] removeColData(int var1);

    @Override
    public void mergeATable(IEventOperateTable tableData) {
        if (tableData == null) {
            throw new IllegalArgumentException("tableData \u4e0d\u80fd\u4e3a\u7a7a\uff01\uff01");
        }
        if (this.colNames == null || tableData.getColNames() == null || this.colNames.length != tableData.getColNames().length) {
            throw new IllegalArgumentException("\u5217\u957f\u6ca1\u6709\u5bf9\u9f50\uff01\uff01");
        }
        for (int i = 0; i < this.colNames.length; ++i) {
            if (Objects.equals(this.colNames[i], tableData.getColNames()[i])) continue;
            throw new IllegalArgumentException("\u5217\u540d\u6ca1\u6709\u5bf9\u9f50\uff01\uff01");
        }
        int startRowCount = this.getRowCount();
        this.incrementRowCount(tableData.getRowCount());
        int mergeRowIdx = 0;
        for (int rowIdx = startRowCount; rowIdx < this.getRowCount(); ++rowIdx) {
            for (int colIdx = 0; colIdx < this.colNames.length; ++colIdx) {
                this.setCellValue(rowIdx, colIdx, tableData.getCellValue(mergeRowIdx, this.colNames[colIdx]));
            }
            ++mergeRowIdx;
        }
    }

    @Override
    public void setCellValue(int rowIndex, String colName, IEventOperateInfo value) {
        if (rowIndex < 0 || rowIndex >= this.rowCount) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15\uff1a" + rowIndex);
        }
        Integer colIdx = this.colIdxMap.get(colName);
        if (colIdx == null) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5217\u540d\uff1a" + colName);
        }
        this.setCellValue(rowIndex, colIdx, value);
    }

    protected abstract void setCellValue(int var1, int var2, IEventOperateInfo var3);

    @Override
    public IEventOperateInfo getCellValue(int rowIdx, String column) {
        if (rowIdx < 0 || rowIdx >= this.rowCount) {
            throw new IndexOutOfBoundsException("\u65e0\u6548\u7684\u884c\u7d22\u5f15\uff1a" + rowIdx);
        }
        Integer colIdx = this.colIdxMap.get(column);
        if (colIdx == null) {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5217\u540d\uff1a" + column);
        }
        return this.getCellValue(rowIdx, colIdx);
    }

    protected abstract IEventOperateInfo getCellValue(int var1, int var2);

    @Override
    public String[] getColNames() {
        return this.colNames != null ? this.colNames : new String[]{};
    }

    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    @Override
    public boolean isEmpty() {
        return this.rowCount == 0;
    }

    @Override
    public Map<String, Integer> getColIdxMap() {
        return this.colIdxMap;
    }

    @Override
    public IEventOperateTable subTable(int startRowIndex, int endRowIndex) {
        if (startRowIndex < 0) {
            throw new IndexOutOfBoundsException("startRowIndex\uff1a" + startRowIndex);
        }
        if (endRowIndex > this.rowCount) {
            throw new IndexOutOfBoundsException("startRowIndex\uff1a" + endRowIndex);
        }
        if (startRowIndex > endRowIndex) {
            throw new IllegalArgumentException("startRowIndex(" + startRowIndex + ") should be smaller than endRowIndex(" + endRowIndex + ")!");
        }
        EventOperateTable subTableData = this.newSubStringTable(this.colNames, endRowIndex - startRowIndex);
        int startIdx = startRowIndex;
        int rowIdx = 0;
        while (startIdx < endRowIndex) {
            for (int colIdx = 0; colIdx < this.colCount; ++colIdx) {
                subTableData.setCellValue(rowIdx, colIdx, this.getCellValue(startIdx, colIdx));
            }
            ++startIdx;
            ++rowIdx;
        }
        return subTableData;
    }

    protected abstract EventOperateTable newSubStringTable(String[] var1, int var2);

    protected String[] checkColNames(String[] colNames) {
        ArrayList<String> newColumns = new ArrayList<String>();
        if (colNames != null) {
            HashMap<String, Integer> colIdxMap = new HashMap<String, Integer>();
            if (this.colIdxMap != null) {
                colIdxMap.putAll(this.colIdxMap);
            }
            for (String column : colNames) {
                if (!StringUtils.isNotEmpty((String)column) || colIdxMap.containsKey(column)) continue;
                newColumns.add(column);
            }
        }
        return newColumns.toArray(new String[0]);
    }

    protected Map<String, Integer> resetColumnIndexMap(String[] columns) {
        HashMap<String, Integer> colIdxMap = new HashMap<String, Integer>();
        for (int i = 0; i < columns.length; ++i) {
            colIdxMap.put(columns[i], i);
        }
        return colIdxMap;
    }

    protected IEventOperateInfo[] checkInsertRowData(Map<String, IEventOperateInfo> rowData) {
        IEventOperateInfo[] newRowData = new IEventOperateInfo[this.colCount];
        for (Map.Entry<String, IEventOperateInfo> entry : rowData.entrySet()) {
            if (!this.colIdxMap.containsKey(entry.getKey())) continue;
            newRowData[this.colIdxMap.get((Object)entry.getKey()).intValue()] = entry.getValue();
        }
        return newRowData;
    }

    protected IEventOperateInfo[] checkUpdateRowData(int rowIndex, Map<String, IEventOperateInfo> rowData) {
        IEventOperateInfo[] newRowData = new IEventOperateInfo[this.colCount];
        for (String colName : this.colNames) {
            newRowData[this.colIdxMap.get((Object)colName).intValue()] = rowData.containsKey(colName) ? rowData.get(colName) : this.getCellValue(rowIndex, colName);
        }
        return newRowData;
    }

    protected final class ColumnIterator
    implements Iterator<IEventOperateInfo[]> {
        private final int rowCount;
        private final int colCount;
        private int columnIndex = 0;

        public ColumnIterator(int rowCount, int colCount) {
            this.rowCount = rowCount;
            this.colCount = colCount;
        }

        @Override
        public boolean hasNext() {
            return this.columnIndex < this.colCount;
        }

        @Override
        public IEventOperateInfo[] next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            IEventOperateInfo[] columnData = new IEventOperateInfo[this.rowCount];
            for (int i = 0; i < this.rowCount; ++i) {
                columnData[i] = EventOperateTable.this.table[i][this.columnIndex];
            }
            ++this.columnIndex;
            return columnData;
        }
    }

    protected final class RowIterator
    implements Iterator<IEventOperateInfo[]> {
        private int rowIndex = 0;
        private final int rowCount;

        public RowIterator(int rowCount) {
            this.rowCount = rowCount;
        }

        @Override
        public boolean hasNext() {
            return this.rowIndex < this.rowCount;
        }

        @Override
        public IEventOperateInfo[] next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return EventOperateTable.this.table[this.rowIndex++];
        }
    }
}

