/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.bean;

public class Header {
    private int startX;
    private int startY;
    private int row;
    private int column;

    public Header() {
    }

    public Header(int startX, int startY, int row, int column) {
        this.startX = startX;
        this.startY = startY;
        this.row = row;
        this.column = column;
    }

    public int getStartX() {
        return this.startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"startX\":").append(this.startX);
        sb.append(",\"startY\":").append(this.startY);
        sb.append(",\"row\":").append(this.row);
        sb.append(",\"column\":").append(this.column);
        sb.append('}');
        return sb.toString();
    }
}

