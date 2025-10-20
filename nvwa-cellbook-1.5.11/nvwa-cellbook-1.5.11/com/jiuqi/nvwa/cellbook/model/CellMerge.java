/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;

public class CellMerge
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rowIndex;
    private int columnIndex;
    private int rowSpan;
    private int columnSpan;

    public CellMerge(int rowIndex, int columnIndex, int rowSpan, int columnSpan) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowSpan() {
        return this.rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getColumnSpan() {
        return this.columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.columnIndex;
        result = 31 * result + this.columnSpan;
        result = 31 * result + this.rowIndex;
        result = 31 * result + this.rowSpan;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CellMerge other = (CellMerge)obj;
        if (this.columnIndex != other.columnIndex) {
            return false;
        }
        if (this.columnSpan != other.columnSpan) {
            return false;
        }
        if (this.rowIndex != other.rowIndex) {
            return false;
        }
        return this.rowSpan == other.rowSpan;
    }

    public String toString() {
        return "CellMerge [rowIndex=" + this.rowIndex + ", columnIndex=" + this.columnIndex + ", rowSpan=" + this.rowSpan + ", columnSpan=" + this.columnSpan + "]";
    }
}

