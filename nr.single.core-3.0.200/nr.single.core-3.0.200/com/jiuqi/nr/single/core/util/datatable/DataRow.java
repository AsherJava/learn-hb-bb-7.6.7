/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util.datatable;

import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataColumnCollection;
import com.jiuqi.nr.single.core.util.datatable.DataTable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataRow {
    private int rowIndex = -1;
    private DataColumnCollection columns;
    private DataTable table;
    private Map<String, Object> itemMap = new LinkedHashMap<String, Object>();
    private Map<String, Object> itemTempMap = new LinkedHashMap<String, Object>();
    private byte[] data;
    private byte[] zipData;
    private boolean hasLoaded = false;
    private int recordNo = -1;
    private boolean bufferValid;

    public DataRow() {
        this.bufferValid = true;
    }

    public DataRow(DataTable table) {
        this.table = table;
        this.bufferValid = true;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setColumns(DataColumnCollection columns) {
        this.columns = columns;
    }

    public DataColumnCollection getColumns() {
        return this.columns;
    }

    public void setValue(int index, Object value) {
        this.setValue((DataColumn)this.columns.get(index), value);
    }

    public void setValue(String columnName, Object value) {
        DataColumn column = this.columns.get(columnName);
        if (null != column) {
            this.setValue(column, value);
        } else {
            this.getItemTempMap().put(columnName, value);
        }
    }

    public void setValue(DataColumn column, Object value) {
        if (column != null) {
            String lowerColumnName = column.getColumnName().toUpperCase();
            if (this.getItemMap().containsKey(lowerColumnName)) {
                this.getItemMap().remove(lowerColumnName);
            }
            this.getItemMap().put(lowerColumnName, column.convertTo(value));
        }
    }

    public Object getValue(int index) {
        String colName = ((DataColumn)this.columns.get(index)).getColumnName().toUpperCase();
        return this.getValue(colName);
    }

    public Object getValue(String columnName) {
        String columnName2 = columnName;
        DataColumn columnObj = null;
        if (this.columns != null && null != (columnObj = this.columns.get(columnName))) {
            columnName2 = columnObj.getColumnName();
        }
        Object obj = this.getItemMap().get(columnName2.toUpperCase());
        if (columnObj == null) {
            obj = this.getItemTempMap().get(columnName);
        }
        return obj;
    }

    public String getValueString(int index) {
        Object obj = this.getValue(index);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public String getValueString(String columnName) {
        Object obj = this.getValue(columnName);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public Map<String, Object> getItemMap() {
        if (this.itemMap == null) {
            this.itemMap = new LinkedHashMap<String, Object>();
        }
        return this.itemMap;
    }

    public Map<String, Object> getItemTempMap() {
        if (this.itemTempMap == null) {
            this.itemTempMap = new LinkedHashMap<String, Object>();
        }
        return this.itemTempMap;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void copyFrom(DataRow row) {
        this.itemMap.clear();
        for (Object c : this.columns) {
            this.itemMap.put(c.toString().toLowerCase(), row.getValue(c.toString()));
        }
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getZipData() {
        return this.zipData;
    }

    public void setZipData(byte[] zipData) {
        this.zipData = zipData;
    }

    public boolean isHasLoaded() {
        return this.hasLoaded;
    }

    public void setHasLoaded(boolean hasLoaded) {
        this.hasLoaded = hasLoaded;
    }

    public int getRecordNo() {
        return this.recordNo;
    }

    public void setRecordNo(int recordNo) {
        this.recordNo = recordNo;
    }

    public boolean isBufferValid() {
        return this.bufferValid;
    }

    public void setBufferValid(boolean bufferValid) {
        this.bufferValid = bufferValid;
    }

    public void clearItemValues() {
        this.getItemMap().clear();
        this.getItemTempMap().clear();
    }
}

