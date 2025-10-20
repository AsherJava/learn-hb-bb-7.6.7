/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.grid.GridHelper
 */
package com.jiuqi.bi.quickreport.engine.build.fragment;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.build.fragment.GridFragment;
import com.jiuqi.bi.quickreport.engine.context.EvalCellInfo;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.selection.CellSelection;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.quickreport.engine.style.StyleProcessor;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.grid.GridHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CellCalculator
extends GridFragment {
    private ExpandingArea area;
    private Region region;
    private List<CellEntry> entries = new ArrayList<CellEntry>();

    public ExpandingArea getArea() {
        return this.area;
    }

    public void setArea(ExpandingArea area) {
        this.area = area;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public void build() throws ReportBuildException {
        this.collectCells();
        this.updateCellTracingInfo();
        this.execCells();
    }

    private void collectCells() {
        GridData grid = this.worksheet.getResultGrid().getGridData();
        for (int row = this.region.top(); row <= this.region.bottom(); ++row) {
            for (int col = this.region.left(); col <= this.region.right(); ++col) {
                Object bindingObj = grid.getObj(col, row);
                if (!(bindingObj instanceof ExpandingCalcCell)) continue;
                Position pos = Position.valueOf((int)col, (int)row);
                this.entries.add(new CellEntry(pos, (ExpandingCalcCell)bindingObj));
            }
        }
        Collections.sort(this.entries, !this.area.getRowAxis().isEmpty() ? CellCalculator::compareByRow : CellCalculator::compareByCol);
    }

    private static int compareByRow(CellEntry entry1, CellEntry entry2) {
        int order2;
        int order1 = entry1.calcCell.getCell().getToplogicOrder();
        int c = order1 - (order2 = entry2.calcCell.getCell().getToplogicOrder());
        if (c != 0) {
            return c;
        }
        c = entry1.position.row() - entry2.position.row();
        if (c != 0) {
            return c;
        }
        return entry1.position.col() - entry2.position.col();
    }

    private static int compareByCol(CellEntry entry1, CellEntry entry2) {
        int order2;
        int order1 = entry1.calcCell.getCell().getToplogicOrder();
        int c = order1 - (order2 = entry2.calcCell.getCell().getToplogicOrder());
        if (c != 0) {
            return c;
        }
        c = entry1.position.col() - entry2.position.col();
        if (c != 0) {
            return c;
        }
        return entry1.position.row() - entry2.position.row();
    }

    private void updateCellTracingInfo() throws ReportBuildException {
        CellSelection cells;
        try {
            cells = this.context.getTracingCells();
        }
        catch (ReportExpressionException e) {
            throw new ReportBuildException(e.getMessage(), e);
        }
        if (cells.isEmpty()) {
            return;
        }
        for (CellEntry entry : this.entries) {
            CellBindingInfo cellBinding;
            if (!cells.contains(new SheetPosition(this.worksheet.name(), entry.position)) || (cellBinding = entry.calcCell.getCell()) == null) continue;
            cellBinding.setTraceable(true);
        }
    }

    private void execCells() throws ReportBuildException {
        this.context.resetPrimaryFields(this.area.getPrimaryFields());
        try {
            for (CellEntry entry : this.entries) {
                this.buildCell(entry.position, entry.calcCell);
            }
        }
        finally {
            this.context.resetPrimaryFields(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void buildCell(Position position, ExpandingCalcCell calcCell) throws ReportBuildException {
        this.initContext(position, calcCell);
        try {
            if (calcCell.getCell().getCellMap() == null) {
                CellValue cellValue = this.makeCell(position, calcCell);
                this.worksheet.getResultGrid().getGridData().setObj(position.col(), position.row(), (Object)cellValue);
            } else {
                CellValue cellValue = this.calcCell(calcCell);
                this.setGridCell(position, cellValue);
            }
            this.applyStyles(calcCell.getCell(), position);
        }
        finally {
            this.clearContext();
        }
    }

    private CellValue makeCell(Position position, ExpandingCalcCell calcCell) {
        CellValue cellValue = new CellValue(calcCell.getCell());
        cellValue.displayValue = cellValue.value = GridHelper.readCellValue((GridData)this.worksheet.getResultGrid().getGridData(), (int)position.col(), (int)position.row());
        cellValue._restrictions = calcCell.getRestrictions();
        return cellValue;
    }

    private CellValue calcCell(ExpandingCalcCell calcCell) throws ReportBuildException {
        CellValue cellValue = this.calcCell(calcCell.getCell());
        cellValue._restrictions = calcCell.getRestrictions();
        return cellValue;
    }

    private void setGridCell(Position position, CellValue cellValue) throws ReportBuildException {
        this.cellHandler.setCell(this.handlerContext, position.col(), position.row(), cellValue);
    }

    private void applyStyles(CellBindingInfo bindingInfo, Position position) throws ReportBuildException {
        try {
            StyleProcessor.applyStyles(this.context, this.worksheet, position, bindingInfo);
        }
        catch (ReportStyleException e) {
            throw new ReportBuildException("\u5904\u7406\u5355\u5143\u683c\u6761\u4ef6\u6837\u5f0f\u51fa\u9519\uff1a" + bindingInfo.getPosition(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initContext(Position position, ExpandingCalcCell calcCell) throws ReportBuildException {
        this.beginFilter();
        try {
            this.context.setCurrentCell(new EvalCellInfo(position, calcCell.getCell()));
            List<IFilterDescriptor> restrictFilters = this.getRestrictionFilters(calcCell);
            this.pushFilters(restrictFilters);
            if (calcCell.getCell().getFilter() != null) {
                List<IFilterDescriptor> filters;
                try {
                    filters = FilterAnalyzer.createFilterDescriptor(this.context, calcCell.getCell(), null);
                }
                catch (ReportContextException e) {
                    throw new ReportBuildException(e);
                }
                this.pushFilters(filters);
            }
            List<IFilterDescriptor> colRowFilters = this.getColRowFilters(calcCell.getCell().getPosition().getPosition());
            this.pushFilters(colRowFilters);
            this.pushFilters(calcCell.getExtraFilters());
        }
        finally {
            this.endFilter();
        }
    }

    protected List<IFilterDescriptor> getRestrictionFilters(ExpandingCalcCell calcCell) {
        ArrayList<IFilterDescriptor> restrictFilters = new ArrayList<IFilterDescriptor>();
        for (AxisDataNode data : calcCell.getRestrictions()) {
            if (data.getRegion().isStatic()) continue;
            restrictFilters.add(data.toFilter());
        }
        return restrictFilters;
    }

    private void clearContext() {
        this.context.setCurrentCell(null);
        this.context.getCurrentFilters().clear();
    }

    private static final class CellEntry {
        public final Position position;
        public final ExpandingCalcCell calcCell;

        public CellEntry(Position position, ExpandingCalcCell cell) {
            this.position = position;
            this.calcCell = cell;
        }

        public String toString() {
            return this.position + "=" + this.calcCell;
        }
    }
}

