/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataGroup;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisExpander;
import com.jiuqi.bi.quickreport.engine.build.expanding.GroupAxisDataIterator;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.build.handler.IHeaderHandler;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.List;

public final class RowExpander
extends AxisExpander {
    private AxisDataNode rowRoot;
    private IHeaderHandler rowHeaderProcessor;
    private Region colRegion;
    private List<IFilterDescriptor> colFilters;
    private int buildingRowEnd = -1;

    public AxisDataNode getRowRoot() {
        return this.rowRoot;
    }

    public void setRowRoot(AxisDataNode rowRoot) {
        this.rowRoot = rowRoot;
    }

    @Override
    public void expand() throws ReportBuildException {
        if (this.area.getRowAxis().isEmpty()) {
            return;
        }
        this.prepare();
        Region rawRegion = this.area.getRowAxis().getRegion();
        int startRow = this.buildingGrid.locateNewRow(rawRegion.top());
        int nextRow = this.expandChildrenRows(this.rowRoot, startRow);
        if (nextRow - startRow > rawRegion.bottom() - rawRegion.top()) {
            this.buildingRowEnd = nextRow - 1;
        }
    }

    public int getEndRow() {
        return this.buildingRowEnd;
    }

    private void prepare() throws ReportBuildException {
        if (!this.area.getColAxis().isEmpty()) {
            this.colRegion = this.area.getColAxis().getRegion();
            try {
                this.colFilters = this.area.getColAxis().getFilters(this.context);
            }
            catch (ReportAreaExpcetion e) {
                throw new ReportBuildException(e);
            }
        }
        this.rowHeaderProcessor = this.createRowHeaderProcessor();
    }

    private IHeaderHandler createRowHeaderProcessor() throws ReportBuildException {
        switch (this.context.getReport().getRowHeaderMode()) {
            case MERGE: {
                return (context, region, data, field) -> context.getResultGrid().getGridData().mergeCells(field.left, field.top, field.right, field.bottom);
            }
            case LIST: {
                return this.createListHeaderProcessor();
            }
            case FIRST: {
                return (context, region, data, field) -> {};
            }
        }
        throw new ReportBuildException("\u672a\u652f\u6301\u7684\u884c\u8868\u5934\u6837\u5f0f\uff1a" + (Object)((Object)this.context.getReport().getRowHeaderMode()));
    }

    private IHeaderHandler createListHeaderProcessor() {
        return (context, region, data, field) -> {
            block3: {
                Object obj;
                GridData grid;
                block2: {
                    grid = context.getResultGrid().getGridData();
                    obj = grid.getObj(field.left, field.top);
                    if (!(obj instanceof CellValue)) break block2;
                    for (int row = field.top + 1; row <= field.bottom; ++row) {
                        if (!grid.isValidCell(field.left, row)) continue;
                        this.setCellValue(field.left, row, data, (CellValue)obj);
                    }
                    break block3;
                }
                if (!(obj instanceof ExpandingCalcCell)) break block3;
                for (int row = field.top + 1; row <= field.bottom; ++row) {
                    if (!grid.isValidCell(field.left, row)) continue;
                    grid.setObj(field.left, row, obj);
                }
            }
        };
    }

    private int expandChildrenRows(AxisDataNode node, int startRow) throws ReportBuildException {
        List<ExpandingRegion> subRegions;
        Region curRegion;
        if (node.getRegion() == null) {
            curRegion = this.area.getRowAxis().getRegion();
            subRegions = this.area.getRowAxis().getExpandingRegions();
        } else {
            curRegion = node.getRegion().getRegion();
            subRegions = node.getRegion().getSubRegions();
        }
        int delta = 0;
        GroupAxisDataIterator i = new GroupAxisDataIterator(node.getChildren(), subRegions);
        while (i.hasNext()) {
            AxisDataGroup subGroup = (AxisDataGroup)i.next();
            Region subRegion = subGroup.region.getRegion();
            int firstRow = startRow + delta + subRegion.top() - curRegion.top();
            int nextRow = this.expandGroupRows(node, subGroup, firstRow);
            delta += nextRow - firstRow - subRegion.rowSize();
        }
        return startRow + curRegion.rowSize() + delta;
    }

    private int expandGroupRows(AxisDataNode node, AxisDataGroup subGroup, int startRow) throws ReportBuildException {
        Region subRegion = subGroup.region.getRegion();
        int nextRow = startRow;
        int nextCol = this.buildingGrid.locateNewCol(subRegion.left());
        if (subGroup.datas.isEmpty()) {
            nextRow = this.doEmptyRow(subGroup.region, nextRow, nextCol);
        } else {
            for (int index = 0; index < subGroup.datas.size(); ++index) {
                nextRow = this.doExpandRow(subGroup, index, nextRow, nextCol);
            }
        }
        this.mergeRowCells(node, startRow, nextRow - 1);
        return nextRow;
    }

    private int doEmptyRow(ExpandingRegion region, int startRow, int startCol) throws ReportBuildException {
        this.bindRegionCells(region, null, startCol, startRow);
        return startRow + region.getRegion().rowSize();
    }

    private int doExpandRow(AxisDataGroup group, int index, int startRow, int startCol) throws ReportBuildException {
        AxisDataNode node = group.datas.get(index);
        if (index == 0) {
            this.applyRegionCells(group.region, node, startCol, startRow);
        } else {
            this.appendRowRegion(group.region, node, startRow, startCol);
        }
        this.pushNode(node);
        int nextRow = this.expandChildrenRows(node, startRow);
        this.mergeHeadRowCells(node, startRow, nextRow - 1);
        this.popNode(node);
        return nextRow;
    }

    private void appendRowRegion(ExpandingRegion region, AxisDataNode node, int startRow, int startCol) throws ReportBuildException {
        Region rawRegion = region.getRegion();
        this.buildingGrid.insertRow(startRow, rawRegion.rowSize(), this.worksheet.getGridData(), rawRegion.top());
        this.buildingGrid.getGridData().copyFrom(this.worksheet.getGridData(), rawRegion.left(), rawRegion.top(), rawRegion.right(), rawRegion.bottom(), startCol, startRow);
        this.bindRegionCells(region, node, startCol, startRow);
    }

    protected void bindRegionCells(ExpandingRegion region, AxisDataNode data, int startCol, int startRow) throws ReportBuildException {
        if (data == null) {
            this.emptyRegionCells(region, startCol, startRow);
        } else {
            this.applyRegionCells(region, data, startCol, startRow);
        }
    }

    private void emptyRegionCells(ExpandingRegion region, int startCol, int startRow) {
        GridData rawGrid = this.worksheet.getGridData();
        Region rawRegion = region.getRegion();
        int deltaCol = startCol - rawRegion.left();
        int deltaRow = startRow - rawRegion.top();
        for (int rawRow = rawRegion.top(); rawRow <= rawRegion.bottom(); ++rawRow) {
            for (int rawCol = rawRegion.left(); rawCol <= rawRegion.right(); ++rawCol) {
                CellBindingInfo bindingInfo;
                CellField cf = rawGrid.expandCell(rawCol, rawRow);
                if (cf.left != rawCol || cf.top != rawRow || (bindingInfo = (CellBindingInfo)rawGrid.getObj(rawCol, rawRow)) == null) continue;
                this.emptyCalcCell(rawCol + deltaCol, rawRow + deltaRow);
                if (!bindingInfo.isHiddenWhenEmpty()) continue;
                Region hiddenRegion = bindingInfo.getCellMap().getExpandRegion();
                this.buildingGrid.setRowsVisible(hiddenRegion.top() + deltaRow, hiddenRegion.bottom() + deltaRow, false);
            }
        }
    }

    private void applyRegionCells(ExpandingRegion region, AxisDataNode data, int startCol, int startRow) throws ReportBuildException {
        GridData rawGrid = this.worksheet.getGridData();
        Region rawRegion = region.getRegion();
        int deltaCol = startCol - rawRegion.left();
        int deltaRow = startRow - rawRegion.top();
        for (int rawRow = rawRegion.top(); rawRow <= rawRegion.bottom(); ++rawRow) {
            for (int rawCol = rawRegion.left(); rawCol <= rawRegion.right(); ++rawCol) {
                CellBindingInfo bindingInfo;
                CellField cf = rawGrid.expandCell(rawCol, rawRow);
                if (cf.left != rawCol || cf.top != rawRow || (bindingInfo = (CellBindingInfo)rawGrid.getObj(rawCol, rawRow)) == null) continue;
                if (region.getMasterCell() == bindingInfo) {
                    this.calcMasterCell(data, rawCol + deltaCol, rawRow + deltaRow);
                } else {
                    this.bindCalcCell(bindingInfo, data, rawCol + deltaCol, rawRow + deltaRow);
                }
                if (!bindingInfo.isHiddenWhenEmpty()) continue;
                Region hiddenRegion = bindingInfo.getCellMap().getExpandRegion();
                this.buildingGrid.setRowsVisible(hiddenRegion.top() + deltaRow, hiddenRegion.bottom() + deltaRow, true);
            }
        }
    }

    private void mergeHeadRowCells(AxisDataNode node, int top, int bottom) throws ReportBuildException {
        int rawLeft = node.getRegion().getRegion().left();
        int left = this.buildingGrid.locateNewCol(rawLeft);
        Position masterPos = node.getRegion().getMasterCellPosition();
        CellField cf = this.worksheet.getGridData().expandCell(masterPos.col(), masterPos.row());
        int right = this.buildingGrid.locateNewCol(cf.right);
        this.rowMerge(node.getRegion(), node, top, left, bottom, right);
    }

    private void mergeRowCells(AxisDataNode node, int top, int bottom) throws ReportBuildException {
        if (node.getChildren().isEmpty() || node.getRegion() == null) {
            return;
        }
        Region subRegion = node.getRegion().expandSubRegion();
        Position masterPos = node.getRegion().getMasterCellPosition();
        CellField cf = this.worksheet.getGridData().expandCell(masterPos.col(), masterPos.row());
        if (cf.right + 1 < subRegion.left()) {
            int left = this.buildingGrid.locateNewCol(cf.right + 1);
            int right = this.buildingGrid.locateNewCol(subRegion.left() - 1);
            this.rowMerge(node.getRegion(), node, top, left, bottom, right);
        }
        Region masterRegion = node.getRegion().getRegion();
        if (subRegion.right() < masterRegion.right()) {
            int left = this.buildingGrid.locateNewCol(subRegion.right() + 1);
            int right = this.buildingGrid.locateLastCol(masterRegion.right());
            this.rowMerge(node.getRegion(), node, top, left, bottom, right);
        }
    }

    private void rowMerge(ExpandingRegion region, AxisDataNode data, int top, int left, int bottom, int right) throws ReportBuildException {
        GridData grid = this.buildingGrid.getGridData();
        for (int col = left; col <= right; ++col) {
            int row = top;
            while (row < bottom) {
                CellField cf = grid.expandCell(col, row);
                if (cf.left != col || this.isSubRegionCell(region, col, row)) {
                    ++row;
                    continue;
                }
                int endRow = this.findMergeRow(cf, bottom);
                if (endRow > cf.bottom) {
                    this.rowHeaderProcessor.processRegion(this.handlerContext, region, data, new CellField(cf.left, cf.top, cf.right, endRow));
                }
                row = endRow + 1;
            }
        }
    }

    private int findMergeRow(CellField field, int bottom) {
        int nextRawRow;
        int originTop = this.buildingGrid.getRawRow(field.top);
        int originBottom = this.buildingGrid.getRawRow(field.bottom);
        int nextRow = field.bottom + 1;
        while (nextRow <= bottom && (nextRawRow = this.buildingGrid.getRawRow(nextRow)) >= originTop && nextRawRow <= originBottom) {
            CellField nextField = this.buildingGrid.getGridData().expandCell(field.left, nextRow);
            if (nextField.left != field.left || nextField.top != nextRow) break;
            nextRow = nextField.bottom + 1;
        }
        return nextRow - 1;
    }

    @Override
    protected void fillOtherRestrictions(ExpandingCalcCell calcCell) {
        if (this.colRegion != null && this.colFilters != null && !this.colRegion.contains(calcCell.getCell().getPosition().getPosition())) {
            calcCell.getExtraFilters().addAll(this.colFilters);
        }
    }
}

