/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import java.io.Serializable;
import java.util.List;

public class CardRowData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int row;
    private int totalCount;
    private List<String> cells;
    private List<Object> data;
    private Object order;
    private Object rowId;

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<String> getCells() {
        return this.cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }

    public List<Object> getData() {
        return this.data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public Object getOrder() {
        return this.order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public Object getRowId() {
        return this.rowId;
    }

    public void setRowId(Object rowId) {
        this.rowId = rowId;
    }
}

