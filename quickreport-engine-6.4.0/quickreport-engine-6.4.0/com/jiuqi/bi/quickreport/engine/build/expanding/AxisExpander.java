/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;
import com.jiuqi.bi.quickreport.engine.context.EvalCellInfo;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.quickreport.engine.style.StyleProcessor;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class AxisExpander {
    protected ReportContext context;
    protected EngineWorksheet worksheet;
    protected ExpandingArea area;
    protected ResultGridData buildingGrid;
    private Deque<AxisDataNode> route = new ArrayDeque<AxisDataNode>();
    protected ICellContentHandler cellHandler;
    protected final IHandlerContext handlerContext = new IHandlerContext(){

        @Override
        public ResultGridData getResultGrid() {
            return AxisExpander.this.buildingGrid;
        }

        @Override
        public GridData getRawGrid() {
            return AxisExpander.this.worksheet.getGridData();
        }

        @Override
        public ReportContext getContext() {
            return AxisExpander.this.context;
        }
    };

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

    public void setArea(ExpandingArea area) {
        this.area = area;
    }

    public ResultGridData getBuildingGrid() {
        return this.buildingGrid;
    }

    public void setBuildingGrid(ResultGridData buildingGrid) {
        this.buildingGrid = buildingGrid;
    }

    public ICellContentHandler getCellHandler() {
        return this.cellHandler;
    }

    public void setCellHandler(ICellContentHandler cellHandler) {
        this.cellHandler = cellHandler;
    }

    public abstract void expand() throws ReportBuildException;

    protected void pushNode(AxisDataNode node) {
        if (!node.getRegion().isStatic()) {
            this.route.push(node);
        }
    }

    protected void popNode(AxisDataNode node) {
        if (!node.getRegion().isStatic()) {
            this.route.pop();
        }
    }

    protected void setCellValue(int col, int row, AxisDataNode data, CellValue cellValue) throws ReportBuildException {
        this.cellHandler.setCell(this.handlerContext, col, row, cellValue);
        this.setCellIndent(col, row, data, cellValue._bindingInfo);
        this.applyStyles(data, col, row, cellValue._restrictions);
    }

    protected void setCellIndent(int col, int row, AxisDataNode data, CellBindingInfo bindingInfo) {
        int rawIndent;
        if (data.getLevel() <= 1) {
            return;
        }
        if (bindingInfo == null) {
            rawIndent = 0;
        } else {
            Position position = bindingInfo.getCellMap().getPosition();
            GridCell rawCell = this.worksheet.getGridData().getCell(position.col(), position.row());
            rawIndent = rawCell.getIndent();
        }
        GridData grid = this.buildingGrid.getGridData();
        GridCell cell = grid.getCellEx(col, row);
        cell.setIndent(rawIndent + data.getLevel() - 1);
        grid.setCell(cell);
    }

    private void applyStyles(AxisDataNode data, int col, int row, List<AxisDataNode> restrictions) throws ReportBuildException {
        CellBindingInfo bindingInfo = data.getRegion().getMasterCell();
        if (bindingInfo.getStyleProcessor() == null) {
            return;
        }
        Position position = Position.valueOf((int)col, (int)row);
        this.initMasterContext(position, data, restrictions);
        try {
            StyleProcessor.applyStyles(this.context, this.worksheet, position, bindingInfo);
        }
        catch (ReportStyleException e) {
            throw new ReportBuildException("\u5904\u7406\u5355\u5143\u683c\u6761\u4ef6\u6837\u5f0f\u51fa\u9519\uff1a" + bindingInfo.getPosition(), e);
        }
        this.clearMasterContext();
    }

    private void initMasterContext(Position position, AxisDataNode data, List<AxisDataNode> restrictions) {
        this.context.setCurrentCell(new EvalCellInfo(position, data.getRegion().getMasterCell()));
        restrictions.forEach(r -> r.fillFilters(this.context.getCurrentFilters()));
    }

    private void clearMasterContext() {
        this.context.setCurrentCell(null);
        this.context.getCurrentFilters().clear();
    }

    protected boolean isSubRegionCell(ExpandingRegion region, int col, int row) {
        if (region.getSubRegions().isEmpty()) {
            return false;
        }
        int rawCol = this.buildingGrid.getRawCol(col);
        int rawRow = this.buildingGrid.getRawRow(row);
        Position pos = Position.valueOf((int)rawCol, (int)rawRow);
        for (ExpandingRegion subRegion : region.getSubRegions()) {
            if (!subRegion.contains(pos)) continue;
            return true;
        }
        return false;
    }

    protected void emptyCalcCell(int col, int row) {
        this.buildingGrid.getGridData().setObj(col, row, null);
    }

    protected void calcMasterCell(AxisDataNode data, int col, int row) throws ReportBuildException {
        GridData grid = this.buildingGrid.getGridData();
        CellValue cellValue = data.toCellValue();
        cellValue._restrictions = new ArrayList<AxisDataNode>();
        Object obj = grid.getObj(col, row);
        if (obj instanceof ExpandingCalcCell) {
            ExpandingCalcCell calcCell = (ExpandingCalcCell)obj;
            cellValue._restrictions.addAll(calcCell.getRestrictions());
        } else {
            cellValue._restrictions.addAll(this.route);
        }
        if (!data.getRegion().isStatic()) {
            cellValue._restrictions.add(data);
        }
        this.setCellValue(col, row, data, cellValue);
    }

    protected void bindCalcCell(CellBindingInfo bindingInfo, AxisDataNode restriction, int col, int row) {
        ExpandingCalcCell calcCell;
        Object obj = this.buildingGrid.getGridData().getObj(col, row);
        if (obj instanceof ExpandingCalcCell) {
            calcCell = (ExpandingCalcCell)obj;
        } else {
            calcCell = new ExpandingCalcCell(bindingInfo);
            calcCell.addRestrictions(this.route);
            this.buildingGrid.getGridData().setObj(col, row, (Object)calcCell);
        }
        if (!restriction.getRegion().isStatic()) {
            calcCell.addRestriction(restriction);
        }
        this.fillOtherRestrictions(calcCell);
        if (restriction.getRegion().getRefIndentCell() == bindingInfo && restriction.getLevel() > 1) {
            this.setCellIndent(col, row, restriction, bindingInfo);
        }
    }

    protected void bindCalcCell(ExpandingCalcCell calcCell, AxisDataNode restriction, int col, int row) {
        ExpandingCalcCell newCell = (ExpandingCalcCell)calcCell.clone();
        newCell.addRestrictions(this.route);
        if (!restriction.getRegion().isStatic()) {
            newCell.addRestriction(restriction);
        }
        this.fillOtherRestrictions(newCell);
        this.buildingGrid.getGridData().setObj(col, row, (Object)newCell);
    }

    protected abstract void fillOtherRestrictions(ExpandingCalcCell var1);
}

