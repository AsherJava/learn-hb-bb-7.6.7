/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridConsts
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.build.adjust;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridConsts;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public final class WorkSheetAdjustor {
    private EngineWorksheet worksheet;
    private List<GridArea> areas = new ArrayList<GridArea>();
    private BitSet rowExpanding;
    private int colExpandingStart;

    public EngineWorksheet getWorksheet() {
        return this.worksheet;
    }

    public void setWorksheet(EngineWorksheet worksheet) {
        this.worksheet = worksheet;
    }

    public List<GridArea> getAreas() {
        return this.areas;
    }

    public void adjust() throws ReportBuildException {
        this.scanAreas();
        if (this.colExpandingStart == -1) {
            return;
        }
        this.adjustContent();
    }

    private void scanAreas() {
        this.rowExpanding = new BitSet(this.worksheet.getGridData().getRowCount());
        this.colExpandingStart = -1;
        for (GridArea area : this.areas) {
            if (!(area instanceof ExpandingArea)) continue;
            this.scanExpandingArea((ExpandingArea)area);
        }
    }

    private void scanExpandingArea(ExpandingArea area) {
        Region region;
        if (!area.getColAxis().isEmpty()) {
            region = area.getColAxis().getRegion();
            if (this.colExpandingStart == -1 || this.colExpandingStart > region.left()) {
                this.colExpandingStart = region.left();
            }
        }
        region = area.getRegion();
        for (int row = region.top(); row <= region.bottom(); ++row) {
            this.rowExpanding.set(row);
        }
    }

    private void adjustContent() throws ReportBuildException {
        RowRegionIterator i = new RowRegionIterator();
        while (i.hasNext()) {
            int[] range = i.next();
            this.adjustCells(range[0], range[1]);
        }
    }

    private void adjustCells(int top, int bottom) throws ReportBuildException {
        GridData grid = this.worksheet.getGridData();
        for (int row = top; row <= bottom; ++row) {
            for (int col = this.colExpandingStart; col < grid.getColCount(); ++col) {
                CellField field = grid.expandCell(col, row);
                if (!this.isAdjustable(col, row, field) || StringUtils.isEmpty((String)grid.getCellData(field.left, field.top)) && grid.getObj(field.left, field.top) == null) continue;
                this.adjustCell(field, bottom);
            }
        }
    }

    private boolean isAdjustable(int col, int row, CellField field) {
        if (field.left == col && field.top == row) {
            return true;
        }
        if (field.left < this.colExpandingStart) {
            return field.right == col && field.top == row;
        }
        return false;
    }

    private void adjustCell(CellField field, int rawLastRow) throws ReportBuildException {
        ResultGridData resultGrid = this.worksheet.getResultGrid();
        int firstLeft = resultGrid.locateNewCol(field.left);
        int lastLeft = resultGrid.locateLastCol(field.left);
        int top = resultGrid.locateNewRow(field.top);
        int endRow = rawLastRow + top - field.top;
        CellField newField = resultGrid.getGridData().expandCell(firstLeft, top);
        if (firstLeft != lastLeft && lastLeft > newField.right) {
            resultGrid.getGridData().copyFrom(resultGrid.getGridData(), firstLeft, top, firstLeft + field.right - field.left, top + field.bottom - field.top, lastLeft, top);
            this.emptyCell(firstLeft, top, endRow);
        } else if (field.left != field.right) {
            int lastRight = resultGrid.locateLastCol(field.right);
            int bottom = resultGrid.locateNewRow(field.bottom);
            if (lastRight > field.right) {
                resultGrid.getGridData().mergeCells(firstLeft, top, lastRight, bottom);
            }
        }
    }

    private void emptyCell(int col, int row, int lastRow) {
        GridData grid = this.worksheet.getResultGrid().getGridData();
        GridCell cell = new GridCell();
        cell.init(grid, col, row, GridConsts.DEF_CELL_PROP);
        GridCell rawCell = grid.getCellEx(col, row);
        cell.setBEdge(rawCell.getBEdgeColor(), rawCell.getBEdgeStyle());
        cell.setREdge(rawCell.getREdgeColor(), rawCell.getREdgeStyle());
        grid.setCell(cell);
        grid.setCellData(col, row, null);
    }

    private final class RowRegionIterator
    implements Iterator<int[]> {
        private int current = 1;

        public RowRegionIterator() {
            this.skipExpandingRows();
        }

        private void skipExpandingRows() {
            GridData grid = WorkSheetAdjustor.this.worksheet.getGridData();
            while (this.current < grid.getRowCount() && WorkSheetAdjustor.this.rowExpanding.get(this.current)) {
                ++this.current;
            }
        }

        @Override
        public boolean hasNext() {
            return this.current < WorkSheetAdjustor.this.worksheet.getGridData().getRowCount();
        }

        @Override
        public int[] next() {
            int end = this.findRegionEnd();
            int[] ret = new int[]{this.current, end};
            this.current = end + 1;
            this.skipExpandingRows();
            return ret;
        }

        private int findRegionEnd() {
            GridData grid = WorkSheetAdjustor.this.worksheet.getGridData();
            for (int row = this.current + 1; row < grid.getRowCount(); ++row) {
                if (!WorkSheetAdjustor.this.rowExpanding.get(row)) continue;
                return row - 1;
            }
            return grid.getRowCount() - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

