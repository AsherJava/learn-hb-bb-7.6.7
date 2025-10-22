/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

public class ErrorPosInfo {
    private int row;
    private int col;
    private String value;
    private String message;

    public ErrorPosInfo(int row, int col, String message, String value) {
        this.row = row;
        this.col = col;
        this.message = message;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

