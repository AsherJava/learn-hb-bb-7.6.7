/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.service.common.AbstractStringTable;
import com.jiuqi.nr.workflow2.service.common.ColumnOrientedTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrimaryColumnOrientedTable
extends ColumnOrientedTable {
    private final List<String> indexColNames = new ArrayList<String>();
    private final Map<String, Integer> rowIndexMap = new HashMap<String, Integer>();
    private final Map<String, Integer> primaryColName2Indexes = new HashMap<String, Integer>();

    public PrimaryColumnOrientedTable(String[] colNames, String[] primaryColNames, int rowCount) {
        super(colNames, rowCount);
        for (String primaryColName : primaryColNames) {
            if (!this.colIdxMap.containsKey(primaryColName)) {
                throw new IllegalArgumentException("\u65e0\u6548\u7684\u7d22\u5f15\u5217" + primaryColName + "\uff0c\u8be5\u5217\u540d\u5728\u8868\u5934\u4e2d\u4e0d\u5b58\u5728\uff01\uff01");
            }
            this.indexColNames.add(primaryColName);
            this.primaryColName2Indexes.put(primaryColName, (Integer)this.colIdxMap.get(primaryColName));
        }
    }

    @Override
    protected void insertRowData(int rowIndex, String[] insRowData) {
        String primaryIndexKeySet = this.getPrimaryIndexKeySet(insRowData);
        if (this.rowIndexMap.containsKey(primaryIndexKeySet)) {
            throw new DuplicatePrimaryKeyException("\u884c\u7d22\u5f15[" + primaryIndexKeySet + "]\u5df2\u5b58\u5728\uff01\uff01");
        }
        super.insertRowData(rowIndex, insRowData);
        for (int updateIdx = rowIndex; updateIdx < this.rowCount; ++updateIdx) {
            this.rowIndexMap.put(this.getPrimaryIndexKeySet(this.getRowData(updateIdx)), updateIdx);
        }
    }

    @Override
    protected void updateRowData(int rowIndex, String[] uptRowData) {
        String primaryIndexKeySet = this.getPrimaryIndexKeySet(uptRowData);
        if (!this.rowIndexMap.containsKey(primaryIndexKeySet)) {
            throw new PrimaryKeyNotFoundException("\u4e3b\u952e\u5217" + primaryIndexKeySet + "\u4e0d\u5b58\u5728\uff01\uff01");
        }
        if (this.rowIndexMap.get(primaryIndexKeySet) == rowIndex) {
            super.updateRowData((int)this.rowIndexMap.get(primaryIndexKeySet), uptRowData);
        }
    }

    @Override
    protected String[] deleteRowData(int rowIndex) {
        String[] deleteRowData = super.deleteRowData(rowIndex);
        for (int updateIdx = rowIndex; updateIdx < this.rowCount; ++updateIdx) {
            this.rowIndexMap.put(this.getPrimaryIndexKeySet(this.getRowData(updateIdx)), updateIdx);
        }
        return deleteRowData;
    }

    @Override
    protected void setCellValue(int rowIndex, int colIdx, String value) {
        if (this.primaryColName2Indexes.containsValue(colIdx)) {
            this.rowIndexMap.remove(this.getPrimaryIndexKeySet(this.getRowData(rowIndex)));
            super.setCellValue(rowIndex, colIdx, value);
            String primaryIndexKeySet = this.getPrimaryIndexKeySet(this.getRowData(rowIndex));
            if (this.rowIndexMap.containsKey(primaryIndexKeySet)) {
                this.removeRowData(rowIndex);
                throw new DuplicatePrimaryKeyException("\u884c\u7d22\u5f15[" + primaryIndexKeySet + "]\u5df2\u5b58\u5728\uff01\uff01");
            }
            this.rowIndexMap.put(primaryIndexKeySet, rowIndex);
        } else {
            super.setCellValue(rowIndex, colIdx, value);
        }
    }

    @Override
    public int[] findRowIndex(Map<String, String> rowFilter) {
        String primaryIndexKeySet = this.getPrimaryIndexKeySet(rowFilter);
        if (this.rowIndexMap.containsKey(primaryIndexKeySet)) {
            return new int[]{this.rowIndexMap.get(primaryIndexKeySet)};
        }
        return super.findRowIndex(rowFilter);
    }

    @Override
    protected AbstractStringTable newSubStringTable(String[] colNames, int rowCount) {
        return new PrimaryColumnOrientedTable(colNames, this.indexColNames.toArray(new String[0]), rowCount);
    }

    protected String getPrimaryIndexKeySet(Map<String, String> rowFilter) {
        return this.indexColNames.stream().map(rowFilter::get).collect(Collectors.joining("-"));
    }

    protected String getPrimaryIndexKeySet(String[] rowData) {
        return this.indexColNames.stream().map(colName -> rowData[this.primaryColName2Indexes.get(colName)]).collect(Collectors.joining("-"));
    }

    protected static class IncompletePrimaryKeyException
    extends RuntimeException {
        public IncompletePrimaryKeyException(String message) {
            super(message);
        }
    }

    protected static class PrimaryKeyNotFoundException
    extends RuntimeException {
        public PrimaryKeyNotFoundException(String message) {
            super(message);
        }
    }

    protected static class DuplicatePrimaryKeyException
    extends RuntimeException {
        public DuplicatePrimaryKeyException(String message) {
            super(message);
        }
    }
}

