/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;

public class GridOperator {
    private GridOperator() {
    }

    public static GridCell getAbsoluteCell(GridData grid, int col, int row) {
        return grid.internalGetCell(col, row);
    }

    public static void setAbsoluteCell(GridData grid, GridCell cell) {
        grid.internalSetCell(cell);
    }

    public static int[] getLEdgeStyles(GridData grid, int col, int row) {
        if (col == 0) {
            return new int[]{0};
        }
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col - 1, row);
            return new int[]{cell.getREdgeStyle()};
        }
        int[] styles = new int[field.bottom - field.top + 1];
        for (int r = field.top; r <= field.bottom; ++r) {
            GridCell cell = grid.internalGetCell(field.left - 1, r);
            styles[r - field.top] = cell.getREdgeStyle();
        }
        return styles;
    }

    public static int[] getLEdgeColors(GridData grid, int col, int row) {
        if (col == 0) {
            return new int[]{0};
        }
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col - 1, row);
            return new int[]{cell.getREdgeColor()};
        }
        int[] colors = new int[field.bottom - field.top + 1];
        for (int r = field.top; r <= field.bottom; ++r) {
            GridCell cell = grid.internalGetCell(field.left - 1, r);
            colors[r - field.top] = cell.getREdgeColor();
        }
        return colors;
    }

    public static int[] getTEdgeStyles(GridData grid, int col, int row) {
        if (row == 0) {
            return new int[]{0};
        }
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col, row - 1);
            return new int[]{cell.getBEdgeStyle()};
        }
        int[] styles = new int[field.right - field.left + 1];
        for (int c = field.left; c <= field.right; ++c) {
            GridCell cell = grid.internalGetCell(c, field.top - 1);
            styles[c - field.left] = cell.getBEdgeStyle();
        }
        return styles;
    }

    public static int[] getTEdgeColors(GridData grid, int col, int row) {
        if (row == 0) {
            return new int[]{0};
        }
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col, row - 1);
            return new int[]{cell.getBEdgeColor()};
        }
        int[] colors = new int[field.right - field.left + 1];
        for (int c = field.left; c <= field.right; ++c) {
            GridCell cell = grid.internalGetCell(c, field.top - 1);
            colors[c - field.left] = cell.getBEdgeColor();
        }
        return colors;
    }

    public static int[] getREdgeStyles(GridData grid, int col, int row) {
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col, row);
            return new int[]{cell.getREdgeStyle()};
        }
        int[] styles = new int[field.bottom - field.top + 1];
        for (int r = field.top; r <= field.bottom; ++r) {
            GridCell cell = grid.internalGetCell(field.right, r);
            styles[r - field.top] = cell.getREdgeStyle();
        }
        return styles;
    }

    public static int[] getREdgeColors(GridData grid, int col, int row) {
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col, row);
            return new int[]{cell.getREdgeColor()};
        }
        int[] colors = new int[field.bottom - field.top + 1];
        for (int r = field.top; r <= field.bottom; ++r) {
            GridCell cell = grid.internalGetCell(field.right, r);
            colors[r - field.top] = cell.getREdgeColor();
        }
        return colors;
    }

    public static int[] getBEdgeStyles(GridData grid, int col, int row) {
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col, row);
            return new int[]{cell.getBEdgeStyle()};
        }
        int[] styles = new int[field.right - field.left + 1];
        for (int c = field.left; c <= field.right; ++c) {
            GridCell cell = grid.internalGetCell(c, field.bottom);
            styles[c - field.left] = cell.getBEdgeStyle();
        }
        return styles;
    }

    public static int[] getBEdgeColors(GridData grid, int col, int row) {
        CellField field = grid.getCellField(col, row);
        if (field == null) {
            GridCell cell = grid.internalGetCell(col, row);
            return new int[]{cell.getBEdgeColor()};
        }
        int[] colors = new int[field.right - field.left + 1];
        for (int c = field.left; c <= field.right; ++c) {
            GridCell cell = grid.internalGetCell(c, field.bottom);
            colors[c - field.left] = cell.getBEdgeColor();
        }
        return colors;
    }
}

