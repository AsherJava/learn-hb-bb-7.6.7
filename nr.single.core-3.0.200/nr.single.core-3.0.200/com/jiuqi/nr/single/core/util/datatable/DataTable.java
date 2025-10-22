/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.util.datatable;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataColumnCollection;
import com.jiuqi.nr.single.core.util.datatable.DataExpression;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataRowCollection;
import java.util.ArrayList;
import java.util.List;

public final class DataTable {
    private DataRowCollection rows;
    private DataColumnCollection columns = new DataColumnCollection();
    private String tableName;
    private boolean readOnly = false;
    private int nextRowIndex = 0;
    private DataExpression dataExpression;
    private Object tag;

    public DataTable() {
        this.rows = new DataRowCollection();
        this.rows.setColumns(this.columns);
        this.dataExpression = new DataExpression(this);
    }

    public DataTable(String dataTableName) {
        this();
        this.tableName = dataTableName;
    }

    public int getTotalCount() {
        return this.rows.size();
    }

    public int getRowCount() {
        return this.rows.size();
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataRowCollection getRows() {
        return this.rows;
    }

    public DataColumnCollection getColumns() {
        return this.columns;
    }

    public Object getValue(int row, String colName) {
        return ((DataRow)this.rows.get(row)).getValue(colName);
    }

    public Object getValue(int row, int col) {
        return ((DataRow)this.rows.get(row)).getValue(col);
    }

    public DataRow newRow() throws DbfException {
        DataRow tempRow = new DataRow(this);
        this.nextRowIndex = this.nextRowIndex < this.rows.size() ? this.rows.size() : this.nextRowIndex;
        tempRow.setColumns(this.columns);
        tempRow.setRowIndex(this.nextRowIndex++);
        tempRow.setBufferValid(true);
        return tempRow;
    }

    public void setValue(int row, int col, Object value) {
        ((DataRow)this.rows.get(row)).setValue(col, value);
    }

    public void setValue(int row, String colName, Object value) {
        ((DataRow)this.rows.get(row)).setValue(colName, value);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return this.tag;
    }

    public DataColumn addColumn(String columnName, int dataType) throws DbfException {
        return this.columns.addColumn(columnName, dataType);
    }

    public boolean addRow(DataRow row) throws DbfException {
        if (row.getRowIndex() > this.rows.size()) {
            row.setRowIndex(this.rows.size());
        }
        return this.rows.add(row);
    }

    public List<DataRow> select(String filterString) {
        ArrayList<DataRow> rows = new ArrayList<DataRow>();
        if (StringUtils.isNotEmpty((String)filterString)) {
            for (Object row : this.rows) {
                DataRow currentRow = (DataRow)row;
                if (!((Boolean)this.dataExpression.compute(filterString, currentRow.getItemMap())).booleanValue()) continue;
                rows.add(currentRow);
            }
            return rows;
        }
        return this.rows;
    }

    public DataTable select(String filterString, String[] columns, boolean distinct) throws DbfException {
        DataTable result = new DataTable();
        List<DataRow> rows = this.select(filterString);
        for (String c : columns) {
            DataColumn dc = this.columns.get(c);
            DataColumn newDc = new DataColumn(dc.getColumnName(), dc.getDataType());
            newDc.setCaptionName(dc.getCaptionName());
            result.columns.add(newDc);
        }
        for (DataRow r : rows) {
            DataRow newRow = result.newRow();
            newRow.copyFrom(r);
            result.addRow(newRow);
        }
        return result;
    }

    public DataTable select(String tableName, String selectField, String filterString, String groupField) {
        DataTable result = new DataTable();
        return result;
    }

    public Object compute(String expression, String filter) {
        return this.dataExpression.compute(expression, this.select(filter));
    }

    public Object max(String columns, String filter) {
        return null;
    }

    public Object min(String columns, String filter) {
        return null;
    }

    public Object avg(String columns, String filter) {
        return null;
    }

    public Object max(String columns, String filter, String groupBy) {
        return null;
    }

    public Object min(String columns, String filter, String groupBy) {
        return null;
    }

    public Object avg(String columns, String filter, String groupBy) {
        return null;
    }

    private List<DataColumn> getColumns(String colString) {
        ArrayList<DataColumn> columns = new ArrayList<DataColumn>();
        return columns;
    }

    public void Clear() {
    }
}

