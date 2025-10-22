/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

public class MergeCell {
    private int col;
    private int row;
    private int width;
    private int height;

    public MergeCell(int col, int row, int width, int height) {
        this.col = col;
        this.row = row;
        this.width = width;
        this.height = height;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

