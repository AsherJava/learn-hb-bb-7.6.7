/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.grid.h5;

import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid.GridFieldList;
import com.jiuqi.np.grid.h5.H5GridCell;
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
    private static String TAG_ROW_FOOTER_COUNT = "rowFooterCount";
    private static String TAG_COL_FOOTER_COUNT = "colFooterCount";
    private static String TAG_TABLE_SETTING = "tableSetting";
    private static String TAG_ROW_LIST = "rowList";
    private List<List<H5GridCell>> cells = new ArrayList<List<H5GridCell>>();
    private List<H5MergeCell> mergeCells = new ArrayList<H5MergeCell>();
    private List<Integer> hideCols = new ArrayList<Integer>();
    private List<Integer> hideRows = new ArrayList<Integer>();
    private List<Boolean> autoSizeCols = new ArrayList<Boolean>();
    private List<Boolean> autoSizeRows = new ArrayList<Boolean>();
    private List<Integer> rowHeights = new ArrayList<Integer>();
    private List<Integer> colWidths = new ArrayList<Integer>();
    private int rowCount;
    private int colCount;
    private int rowHeaderCount;
    private int colHeaderCount;
    private int rowFooterCount;
    private int colFooterCount;

    public H5GridGenerator(GridData gridData) {
        int i;
        for (int i2 = 0; i2 < gridData.getRowCount(); ++i2) {
            ArrayList<H5GridCell> rowCells = new ArrayList<H5GridCell>();
            for (int j = 0; j < gridData.getColCount(); ++j) {
                GridCell gridCell = gridData.getCell(j, i2);
                rowCells.add(new H5GridCell(gridCell, i2, j));
            }
            this.cells.add(rowCells);
        }
        GridFieldList gridFieldList = gridData.merges();
        for (i = 0; i < gridFieldList.count(); ++i) {
            CellField cellField = gridFieldList.get(i);
            this.mergeCells.add(new H5MergeCell(cellField));
        }
        this.autoSizeCols.add(false);
        this.colWidths.add(1);
        for (i = 1; i < gridData.getColCount(); ++i) {
            if (!gridData.getColVisible(i)) {
                this.hideCols.add(i);
            }
            this.autoSizeCols.add(gridData.getColAutoSize(i));
            this.colWidths.add(gridData.getColWidths(i));
        }
        this.autoSizeRows.add(false);
        this.rowHeights.add(1);
        for (i = 1; i < gridData.getRowCount(); ++i) {
            if (!gridData.getRowVisible(i)) {
                this.hideRows.add(i);
            }
            this.autoSizeRows.add(gridData.getRowAutoSize(i));
            this.rowHeights.add(gridData.getRowHeights(i));
        }
        this.rowCount = gridData.getRowCount();
        this.colCount = gridData.getColCount();
        this.rowHeaderCount = gridData.getScrollTopRow() < 0 ? 0 : gridData.getScrollTopRow();
        this.colHeaderCount = gridData.getScrollTopCol() < 0 ? 0 : gridData.getScrollTopCol();
        this.rowFooterCount = gridData.getScrollBottomRow() < 0 ? 0 : gridData.getScrollBottomRow();
        this.colFooterCount = gridData.getScrollBottomCol() < 0 ? 0 : gridData.getScrollBottomCol();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(TAG_HIDE_COLS, this.hideCols);
        json.put(TAG_HIDE_ROWS, this.hideRows);
        json.put(TAG_AUTO_SIZE_COLS, this.autoSizeCols);
        json.put(TAG_AUTO_SIZE_ROWS, this.autoSizeRows);
        json.put(TAG_COL_WIDTHS, this.colWidths);
        json.put(TAG_ROW_HEIGHTS, this.rowHeights);
        json.put(TAG_TABLE_SETTING, (Object)this.getTableSetting());
        return json;
    }

    private JSONObject getTableSetting() throws JSONException {
        JSONObject tableSetting = new JSONObject();
        JSONArray jsonCells = new JSONArray();
        for (List<H5GridCell> rowCells : this.cells) {
            JSONArray jsonRowCells = new JSONArray();
            for (H5GridCell cell : rowCells) {
                JSONObject obj = cell.toJson();
                jsonRowCells.put((Object)obj);
            }
            jsonCells.put((Object)jsonRowCells);
        }
        JSONObject rowList = new JSONObject();
        rowList.put(TAG_ROW_LIST, (Object)jsonCells);
        tableSetting.put(TAG_CELLS, (Object)rowList);
        JSONArray jsonMergeCells = new JSONArray();
        for (H5MergeCell mergeCell : this.mergeCells) {
            JSONObject obj = new JSONObject();
            mergeCell.toJson(obj);
            jsonMergeCells.put((Object)obj);
        }
        tableSetting.put(TAG_MERGE_CELLS, (Object)jsonMergeCells);
        tableSetting.put(TAG_COL_COUNT, this.colCount);
        tableSetting.put(TAG_ROW_COUNT, this.rowCount);
        tableSetting.put(TAG_ROW_HEADER_COUNT, this.rowHeaderCount);
        tableSetting.put(TAG_COL_HEADER_COUNT, this.colHeaderCount);
        tableSetting.put(TAG_ROW_FOOTER_COUNT, this.rowFooterCount);
        tableSetting.put(TAG_COL_FOOTER_COUNT, this.colFooterCount);
        return tableSetting;
    }

    class H5MergeCell {
        private static final String TAG_COL = "col";
        private static final String TAG_ROW = "row";
        private static final String TAG_WIDTH = "width";
        private static final String TAG_HEIGHT = "height";
        private int col;
        private int row;
        private int width;
        private int height;

        public H5MergeCell(CellField cellField) {
            this.col = cellField.left;
            this.row = cellField.top;
            this.width = cellField.right - cellField.left + 1;
            this.height = cellField.bottom - cellField.top + 1;
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

        public void toJson(JSONObject json) throws JSONException {
            json.put(TAG_COL, this.col);
            json.put(TAG_ROW, this.row);
            json.put(TAG_WIDTH, this.width);
            json.put(TAG_HEIGHT, this.height);
        }
    }
}

