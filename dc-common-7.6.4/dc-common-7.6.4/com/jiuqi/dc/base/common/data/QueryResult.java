/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.data;

import java.util.LinkedHashMap;
import java.util.List;

public class QueryResult {
    private LinkedHashMap<String, Integer> columns;
    private List<Object[]> rowDatas;

    public LinkedHashMap<String, Integer> getColumns() {
        return this.columns;
    }

    public void setColumns(LinkedHashMap<String, Integer> columns) {
        this.columns = columns;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public String toString() {
        return "QueryResult{floatColumns=" + this.columns + ", rowDatas=" + this.rowDatas + '}';
    }
}

