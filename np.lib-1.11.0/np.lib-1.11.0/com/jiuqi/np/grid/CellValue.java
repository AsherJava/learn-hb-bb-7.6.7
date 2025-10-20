/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid;

class CellValue {
    int col;
    int row;
    String value;

    public CellValue() {
    }

    public CellValue(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public CellValue(int col, int row, String value) {
        this.col = col;
        this.row = row;
        this.value = value;
    }
}

