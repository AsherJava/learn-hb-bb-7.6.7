/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.excelUpload;

import java.util.Map;

public class Sheet2GridAdapter {
    private int rowCount;
    private int colCount;
    private String[][] showTexts;
    private boolean[][] isMergeCell;
    private Map<String, String> sheetCells;
    private int matchStartRow = 0;
    private int matchStartCol = 0;
    private int matchEndRow = this.rowCount;
    private int matchEndCol = this.colCount;

    public Sheet2GridAdapter(Map<String, String> sheetCells, Map<String, String> sheetMergeds) {
        this.rowCount = Integer.valueOf(sheetCells.get("LAST_ROW_NUM"));
        this.colCount = Integer.valueOf(sheetCells.get("LAST_COL_NUM"));
        this.sheetCells = sheetCells;
        this.showTexts = new String[this.rowCount][this.colCount];
        this.isMergeCell = new boolean[this.rowCount][this.colCount];
        for (int r = 0; r < this.rowCount; ++r) {
            for (int c = 0; c < this.colCount; ++c) {
                String key = r + "," + c;
                if (this.sheetCells.containsKey(key)) {
                    this.showTexts[r][c] = this.sheetCells.get(key);
                }
                this.isMergeCell[r][c] = sheetMergeds.containsKey(key);
            }
        }
    }

    public String getShowText(int row, int col) {
        String showText = null;
        try {
            showText = this.showTexts[row][col];
        }
        catch (Exception exception) {
            // empty catch block
        }
        return showText;
    }

    public boolean isMergeCell(int row, int col) {
        return this.isMergeCell[row][col];
    }

    public boolean isMergeCellFirstCell(int row, int col) {
        String showText = this.showTexts[row][col];
        if (row >= 1 && col >= 1) {
            return !showText.equals(this.showTexts[row - 1][col]) && !showText.equals(this.showTexts[row][col - 1]);
        }
        if (row >= 1) {
            return !showText.equals(this.showTexts[row - 1][col]);
        }
        if (col >= 1) {
            return !showText.equals(this.showTexts[row][col - 1]);
        }
        return row == 0 && col == 0;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getColCount() {
        return this.colCount;
    }

    public int getMatchStartRow() {
        return this.matchStartRow;
    }

    public void setMatchStartRow(int matchStartRow) {
        this.matchStartRow = matchStartRow;
    }

    public int getMatchEndRow() {
        return this.matchEndRow;
    }

    public void setMatchEndRow(int matchEndRow) {
        this.matchEndRow = matchEndRow;
    }

    public int getMatchStartCol() {
        return this.matchStartCol;
    }

    public void setMatchStartCol(int matchStartCol) {
        this.matchStartCol = matchStartCol;
    }

    public int getMatchEndCol() {
        return this.matchEndCol;
    }

    public void setMatchEndCol(int matchEndCol) {
        this.matchEndCol = matchEndCol;
    }
}

