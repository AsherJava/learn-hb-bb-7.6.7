/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.grid.h5;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.h5.H5GridCell;
import com.jiuqi.bi.grid.h5.H5GridStyle;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class H5GridGenerator {
    private static String TAG_CELLS = "cells";
    private static String TAG_MERGE_CELLS = "mergeCells";
    private static String TAG_HIDE_COLS = "hideCols";
    private static String TAG_HIDE_ROWS = "hideRows";
    private static String TAG_AUTO_SIZE_COLS = "autoSizeCols";
    private static String TAG_AUTO_SIZE_ROWS = "autoSizeRows";
    private static String TAG_COL_WIDTHS = "colWidths";
    private static String TAG_ROW_HEIGHTS = "rowHeights";
    private static String TAG_ROW_COUNT = "rowCount";
    private static String TAG_COL_COUNT = "colCount";
    private static String TAG_ROW_HEADER_COUNT = "rowHeaderCount";
    private static String TAG_COL_HEADER_COUNT = "colHeaderCount";
    private static String TAG_TABLE_SETTING = "tableSetting";
    private static String TAG_ROW_LIST = "rowList";
    private GridData gridData;
    private H5GridStyle gridStyle;

    public H5GridGenerator(GridData gridData) {
        this(gridData, null);
    }

    public H5GridGenerator(GridData gridData, H5GridStyle gridStyle) {
        this.gridData = gridData;
        this.gridStyle = gridStyle;
    }

    protected GridData getGridData() {
        return this.gridData;
    }

    protected H5GridStyle getGridStyle() {
        return this.gridStyle;
    }

    public List<Integer> getHideCols() {
        ArrayList<Integer> hideCols = new ArrayList<Integer>();
        for (int i = 1; i < this.gridData.getColCount(); ++i) {
            if (this.gridData.getColVisible(i)) continue;
            hideCols.add(i);
        }
        return hideCols;
    }

    public List<Integer> getHideRows() {
        ArrayList<Integer> hideRows = new ArrayList<Integer>();
        for (int i = 1; i < this.gridData.getRowCount(); ++i) {
            if (this.gridData.getRowVisible(i)) continue;
            hideRows.add(i);
        }
        return hideRows;
    }

    public List<Boolean> getAutoSizeCols() {
        ArrayList<Boolean> autoSizeCols = new ArrayList<Boolean>();
        autoSizeCols.add(false);
        for (int i = 1; i < this.gridData.getColCount(); ++i) {
            autoSizeCols.add(this.gridData.getColAutoSize(i));
        }
        return autoSizeCols;
    }

    public List<Boolean> getAutoSizeRows() {
        ArrayList<Boolean> autoSizeRows = new ArrayList<Boolean>();
        autoSizeRows.add(false);
        for (int i = 1; i < this.gridData.getRowCount(); ++i) {
            autoSizeRows.add(this.gridData.getRowAutoSize(i));
        }
        return autoSizeRows;
    }

    public List<Integer> getColWidths() {
        ArrayList<Integer> colWidths = new ArrayList<Integer>();
        colWidths.add(1);
        for (int i = 1; i < this.gridData.getColCount(); ++i) {
            colWidths.add(this.gridData.getColWidths(i));
        }
        return colWidths;
    }

    public List<Integer> getRowHeights() {
        ArrayList<Integer> rowHeights = new ArrayList<Integer>();
        rowHeights.add(1);
        for (int i = 1; i < this.gridData.getRowCount(); ++i) {
            rowHeights.add(this.gridStyle == null ? this.gridData.getRowHeights(i) : this.gridStyle.getRowHeight());
        }
        return rowHeights;
    }

    public List<List<H5GridCell>> getRows() {
        ArrayList<List<H5GridCell>> rows = new ArrayList<List<H5GridCell>>();
        for (int i = 0; i < this.gridData.getRowCount(); ++i) {
            ArrayList<H5GridCell> row = new ArrayList<H5GridCell>();
            int lastHideRow = this.getLastHideRow(i);
            for (int j = 0; j < this.gridData.getColCount(); ++j) {
                GridCell cellEx = this.gridData.getCellEx(j, i);
                int lastHideCol = this.getLastHideCol(j);
                if (j != lastHideCol || i != lastHideRow) {
                    GridCell lastHideCellEx = this.gridData.getCellEx(lastHideCol, lastHideRow);
                    if (j != lastHideCol) {
                        cellEx.setREdge(lastHideCellEx.getREdgeColor(), lastHideCellEx.getREdgeStyle());
                    }
                    if (i != lastHideRow) {
                        cellEx.setBEdge(lastHideCellEx.getBEdgeColor(), lastHideCellEx.getBEdgeStyle());
                    }
                }
                row.add(new H5GridCell(cellEx, this.gridStyle, this.gridData));
            }
            rows.add(row);
        }
        return rows;
    }

    public List<H5MergeCell> getMergeCells() {
        ArrayList<H5MergeCell> mergeCells = new ArrayList<H5MergeCell>();
        for (int i = 0; i < this.gridData.merges().count(); ++i) {
            mergeCells.add(new H5MergeCell(this.gridData.merges().get(i)));
        }
        return mergeCells;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(TAG_HIDE_COLS, this.getHideCols());
        json.put(TAG_HIDE_ROWS, this.getHideRows());
        json.put(TAG_AUTO_SIZE_COLS, this.getAutoSizeCols());
        json.put(TAG_AUTO_SIZE_ROWS, this.getAutoSizeRows());
        json.put(TAG_COL_WIDTHS, this.getColWidths());
        json.put(TAG_ROW_HEIGHTS, this.getRowHeights());
        json.put(TAG_TABLE_SETTING, (Object)this.getTableSetting());
        return json;
    }

    private JSONObject getTableSetting() throws JSONException {
        JSONObject tableSetting = new JSONObject();
        JSONArray jsonRows = new JSONArray();
        for (List<H5GridCell> h5Row : this.getRows()) {
            JSONArray jsonRow = new JSONArray();
            for (H5GridCell h5Cell : h5Row) {
                jsonRow.put((Object)h5Cell.toJson());
            }
            jsonRows.put((Object)jsonRow);
        }
        JSONObject rowList = new JSONObject();
        rowList.put(TAG_ROW_LIST, (Object)jsonRows);
        tableSetting.put(TAG_CELLS, (Object)rowList);
        JSONArray jsonMergeCells = new JSONArray();
        for (H5MergeCell mergeCell : this.getMergeCells()) {
            jsonMergeCells.put((Object)mergeCell.toJson());
        }
        tableSetting.put(TAG_MERGE_CELLS, (Object)jsonMergeCells);
        tableSetting.put(TAG_COL_COUNT, this.gridData.getColCount());
        tableSetting.put(TAG_ROW_COUNT, this.gridData.getRowCount());
        tableSetting.put(TAG_ROW_HEADER_COUNT, this.gridData.getScrollTopRow() < 0 ? 0 : this.gridData.getScrollTopRow());
        tableSetting.put(TAG_COL_HEADER_COUNT, this.gridData.getScrollTopCol() < 0 ? 0 : this.gridData.getScrollTopCol());
        return tableSetting;
    }

    protected int getLastHideCol(int currentCol) {
        int col = currentCol;
        if (!this.gridData.getColVisible(col)) {
            return col;
        }
        ++col;
        while (col < this.gridData.getColCount() && !this.gridData.getColVisible(col)) {
            ++col;
        }
        return --col;
    }

    protected int getLastHideRow(int currentRow) {
        int row = currentRow;
        if (!this.gridData.getRowVisible(row)) {
            return row;
        }
        ++row;
        while (row < this.gridData.getRowCount() && !this.gridData.getRowVisible(row)) {
            ++row;
        }
        return --row;
    }

    class H5MergeCell {
        private static final String TAG_COL = "col";
        private static final String TAG_ROW = "row";
        private static final String TAG_WIDTH = "width";
        private static final String TAG_HEIGHT = "height";
        private CellField cellField;

        public H5MergeCell(CellField cellField) {
            this.cellField = cellField;
        }

        public JSONObject toJson() throws JSONException {
            JSONObject json = new JSONObject();
            json.put(TAG_COL, this.cellField.left);
            json.put(TAG_ROW, this.cellField.top);
            json.put(TAG_WIDTH, this.cellField.right - this.cellField.left + 1);
            json.put(TAG_HEIGHT, this.cellField.bottom - this.cellField.top + 1);
            return json;
        }
    }
}

