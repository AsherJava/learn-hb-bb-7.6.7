/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean;

public class WarningInfo {
    private int row;
    private int col;
    private String color;

    public WarningInfo(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

