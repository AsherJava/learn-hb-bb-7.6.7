/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.expanding.ColExpander;
import com.jiuqi.bi.quickreport.engine.build.expanding.RowExpander;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;

public final class GridExpander {
    private ReportContext context;
    private EngineWorksheet worksheet;
    private ResultGridData buildingGrid;
    private Region expandedRegion;
    private ExpandingArea area;
    private AxisDataNode rowRoot;
    private AxisDataNode colRoot;
    private ICellContentHandler cellHandler;

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public EngineWorksheet getWorksheet() {
        return this.worksheet;
    }

    public void setWorksheet(EngineWorksheet worksheet) {
        this.worksheet = worksheet;
    }

    public ExpandingArea getArea() {
        return this.area;
    }

    public void setArea(ExpandingArea area) {
        this.area = area;
    }

    public AxisDataNode getRowRoot() {
        return this.rowRoot;
    }

    public void setRowRoot(AxisDataNode rowRoot) {
        this.rowRoot = rowRoot;
    }

    public AxisDataNode getColRoot() {
        return this.colRoot;
    }

    public void setColRoot(AxisDataNode colRoot) {
        this.colRoot = colRoot;
    }

    public ICellContentHandler getCellHandler() {
        return this.cellHandler;
    }

    public void setCellHandler(ICellContentHandler cellHandler) {
        this.cellHandler = cellHandler;
    }

    public void expand() throws ReportBuildException {
        this.prepare();
        int buildingRowEnd = this.expandRows();
        this.expandCols(buildingRowEnd);
        this.mergeResultGrid();
    }

    public Region getExpanedRegion() {
        return this.expandedRegion;
    }

    private void prepare() throws ReportBuildException {
        this.prepareBuildingGrid();
        this.initCalcCells();
    }

    private void prepareBuildingGrid() throws ReportBuildException {
        Region rawRegion = this.area.getRegion();
        if (this.isExpaned(rawRegion)) {
            this.createBuildingGrid(rawRegion);
        } else {
            this.buildingGrid = this.worksheet.getResultGrid();
        }
    }

    private void createBuildingGrid(Region rawRegion) {
        boolean visible;
        GridData rawGrid = this.worksheet.getGridData();
        GridData newGrid = rawGrid.protoCreate();
        newGrid.setOptions((rawGrid.getOptions() | 0x10) & 0xFFFFFFFB);
        newGrid.setColCount(rawGrid.getColCount());
        newGrid.setRowCount(rawGrid.getRowCount());
        newGrid.copyFrom(rawGrid, rawRegion.left(), rawRegion.top(), rawRegion.right(), rawRegion.bottom(), rawRegion.left(), rawRegion.top());
        for (int row = rawRegion.top(); row <= rawRegion.bottom(); ++row) {
            visible = rawGrid.getRowVisible(row);
            newGrid.setRowVisible(row, visible);
        }
        for (int col = rawRegion.left(); col <= rawRegion.right(); ++col) {
            visible = rawGrid.getColVisible(col);
            newGrid.setColVisible(col, visible);
        }
        this.buildingGrid = new ResultGridData(newGrid);
    }

    private void initCalcCells() throws ReportBuildException {
        GridData rawGrid = this.worksheet.getGridData();
        Region rawRegion = this.area.getRegion();
        Position newPos = this.buildingGrid.locateNewPostion(rawRegion.leftTop());
        int colDelta = newPos.col() - rawRegion.left();
        int rowDelta = newPos.row() - rawRegion.top();
        for (int col = rawRegion.left(); col <= rawRegion.right(); ++col) {
            for (int row = rawRegion.top(); row <= rawRegion.bottom(); ++row) {
                CellBindingInfo bindingInfo = (CellBindingInfo)rawGrid.getObj(col, row);
                if (bindingInfo == null || bindingInfo.getOwnerArea() != this.area || bindingInfo.isMaster()) continue;
                ExpandingCalcCell calcCell = new ExpandingCalcCell(bindingInfo);
                this.buildingGrid.getGridData().setObj(col + colDelta, row + rowDelta, (Object)calcCell);
            }
        }
    }

    private boolean isExpaned(Region rawRegion) throws ReportBuildException {
        int top;
        int left;
        ResultGridData resultGrid = this.worksheet.getResultGrid();
        for (int col = left = resultGrid.locateNewCol(rawRegion.left()); col < left + rawRegion.colSize(); ++col) {
            if (resultGrid.isRawCol(col)) continue;
            return true;
        }
        int nextCol = left + rawRegion.colSize();
        if (nextCol < resultGrid.getGridData().getColCount() && !resultGrid.isRawCol(nextCol)) {
            return true;
        }
        for (int row = top = resultGrid.locateNewRow(rawRegion.top()); row < top + rawRegion.rowSize(); ++row) {
            if (resultGrid.isRawRow(row)) continue;
            return true;
        }
        int nextRow = top + rawRegion.rowSize();
        return nextRow < resultGrid.getGridData().getRowCount() && !resultGrid.isRawRow(nextRow);
    }

    private int expandRows() throws ReportBuildException {
        RowExpander rowExpander = new RowExpander();
        rowExpander.setContext(this.context);
        rowExpander.setWorksheet(this.worksheet);
        rowExpander.setArea(this.area);
        rowExpander.setCellHandler(this.cellHandler);
        rowExpander.setBuildingGrid(this.buildingGrid);
        rowExpander.setRowRoot(this.rowRoot);
        rowExpander.expand();
        return rowExpander.getEndRow();
    }

    private void expandCols(int buildingRowEnd) throws ReportBuildException {
        ColExpander colExpander = new ColExpander();
        colExpander.setContext(this.context);
        colExpander.setWorksheet(this.worksheet);
        colExpander.setArea(this.area);
        colExpander.setCellHandler(this.cellHandler);
        colExpander.setBuildingGrid(this.buildingGrid);
        colExpander.setColRoot(this.colRoot);
        colExpander.setBuildingRowEnd(buildingRowEnd);
        colExpander.expand();
    }

    private void mergeResultGrid() throws ReportBuildException {
        ResultGridData resultGrid = this.worksheet.getResultGrid();
        Region buildedRegion = this.getBuildedRegion();
        this.expandedRegion = this.buildingGrid == resultGrid ? buildedRegion : resultGrid.mergeExpandingRegion(this.buildingGrid, buildedRegion, this.area);
    }

    private Region getBuildedRegion() throws ReportBuildException {
        Region rawRegion = this.area.getRegion();
        int left = this.buildingGrid.locateNewCol(rawRegion.left());
        int top = this.buildingGrid.locateNewRow(rawRegion.top());
        int right = this.buildingGrid.locateLastCol(rawRegion.right());
        int bottom = this.buildingGrid.locateLastRow(rawRegion.bottom());
        return new Region(left, top, right, bottom);
    }
}

