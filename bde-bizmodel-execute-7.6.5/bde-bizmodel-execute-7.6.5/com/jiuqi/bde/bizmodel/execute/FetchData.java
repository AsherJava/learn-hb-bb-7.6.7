/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FetchData {
    private Map<String, Integer> columns;
    private List<Object[]> rowDatas;

    public FetchData() {
    }

    public FetchData(Map<String, Integer> columns, List<Object[]> rowDatas) {
        this.columns = columns;
        this.rowDatas = rowDatas;
    }

    public Map<String, Integer> getColumns() {
        return this.columns;
    }

    public void setColumns(Map<String, Integer> columns) {
        this.columns = columns;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public String getString(Object[] rowData, String column) {
        if (this.columns.get(column) == null) {
            return "";
        }
        return (String)rowData[this.columns.get(column)];
    }

    public BigDecimal getBigDecimal(Object[] rowData, String column) {
        if (this.columns.get(column) == null) {
            return BigDecimal.ZERO;
        }
        Object data = rowData[this.columns.get(column)];
        if (data instanceof Integer) {
            return new BigDecimal((Integer)data);
        }
        if (data instanceof BigDecimal) {
            return (BigDecimal)data;
        }
        return new BigDecimal(String.valueOf(data));
    }
}

