/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.impl;

public class ReportDataImpl {
    private byte[] gridBytes;
    private int colCount = 0;
    private int rowCount = 0;

    public int getColCount() {
        return this.colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public final void setData(byte[] bytes) {
        this.gridBytes = bytes;
    }

    public final byte[] getGridBytes() {
        return this.gridBytes;
    }
}

