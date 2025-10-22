/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.utils;

import java.io.Serializable;

public class ExcelMergeCell
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fristRow;
    private int fristCol;
    private int lastRow;
    private int lastCol;

    public ExcelMergeCell(int fristRow, int fristCol, int lastRow, int lastCol) {
        this.fristRow = fristRow;
        this.fristCol = fristCol;
        this.lastRow = lastRow;
        this.lastCol = lastCol;
    }

    public int getFristRow() {
        return this.fristRow;
    }

    public void setFristRow(int fristRow) {
        this.fristRow = fristRow;
    }

    public int getFristCol() {
        return this.fristCol;
    }

    public void setFristCol(int fristCol) {
        this.fristCol = fristCol;
    }

    public int getLastRow() {
        return this.lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getLastCol() {
        return this.lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }
}

