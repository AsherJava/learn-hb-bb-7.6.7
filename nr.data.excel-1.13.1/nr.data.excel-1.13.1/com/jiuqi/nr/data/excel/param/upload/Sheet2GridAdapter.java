/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param.upload;

import com.jiuqi.nr.data.excel.utils.ExcelMergeCell;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sheet2GridAdapter {
    private static final Logger log = LoggerFactory.getLogger(Sheet2GridAdapter.class);
    private int rowCount;
    private int colCount;
    private final List<ExcelMergeCell> sheetMergeds;
    private final Map<String, Map<String, Object>> sheetCells;
    private Map<String, Map<String, String>> sheetCellFormats;
    private Map<Integer, String> tabNames;
    private int matchStartRow = 0;
    private int matchStartCol = 0;
    private int matchEndRow = this.rowCount;
    private int matchEndCol = this.colCount;

    public Sheet2GridAdapter(int rowCount, int colCount, Map<String, Map<String, Object>> sheetCells, List<ExcelMergeCell> sheetMergeds) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.sheetCells = sheetCells;
        this.sheetMergeds = sheetMergeds;
    }

    public Sheet2GridAdapter(int rowCount, int colCount, Map<String, Map<String, Object>> sheetCells, Map<String, Map<String, String>> sheetCellFormats, List<ExcelMergeCell> sheetMergeds) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.sheetCells = sheetCells;
        this.sheetCellFormats = sheetCellFormats;
        this.sheetMergeds = sheetMergeds;
    }

    public Object getShowText(int row, int col) {
        Object showText = null;
        try {
            Map<String, Object> rowMap = this.sheetCells.get(String.valueOf(row));
            if (rowMap != null && (showText = rowMap.get(String.valueOf(col))) == null) {
                return "";
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return showText;
    }

    public String getCellFormat(int row, int col) {
        String format = null;
        try {
            Map<String, String> rowMap = this.sheetCellFormats.get(String.valueOf(row));
            if (rowMap != null) {
                format = rowMap.get(String.valueOf(col));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return format;
    }

    public boolean isMergeCell(int row, int col) {
        for (ExcelMergeCell mergeCell : this.sheetMergeds) {
            if (row < mergeCell.getFristRow() || col < mergeCell.getFristCol() || row > mergeCell.getLastRow() || col > mergeCell.getLastCol()) continue;
            return true;
        }
        return false;
    }

    public boolean isMergeCellFirstCell(int row, int col) {
        for (ExcelMergeCell mergeCell : this.sheetMergeds) {
            if (row != mergeCell.getFristRow() || col != mergeCell.getFristCol()) continue;
            return true;
        }
        return false;
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

    public Map<Integer, String> getTabNames() {
        return this.tabNames;
    }

    public void setTabNames(Map<Integer, String> tabNames) {
        this.tabNames = tabNames;
    }
}

