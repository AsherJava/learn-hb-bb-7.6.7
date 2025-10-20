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
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataGroup;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisExpander;
import com.jiuqi.bi.quickreport.engine.build.expanding.GroupAxisDataIterator;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ColExpander
extends AxisExpander {
    private AxisDataNode colRoot;
    private Region rowRegion;
    private List<IFilterDescriptor> rowFilters;
    private int buildingRowEnd;
    private GridData tempGrid;
    private List<Integer> tempRowMaps;
    private TieredRegion tieredRegion = new TieredRegion();
    private static final String TIERED_TITLE = "\u5c0f\u8ba1";

    public AxisDataNode getColRoot() {
        return this.colRoot;
    }

    public void setColRoot(AxisDataNode colRoot) {
        this.colRoot = colRoot;
    }

    public int getBuildingRowEnd() {
        return this.buildingRowEnd;
    }

    public void setBuildingRowEnd(int buildingRowEnd) {
        this.buildingRowEnd = buildingRowEnd;
    }

    @Override
    public void expand() throws ReportBuildException {
        if (this.area.getColAxis().isEmpty()) {
            return;
        }
        this.prepare();
        Region rawRegion = this.area.getColAxis().getRegion();
        int startCol = this.buildingGrid.locateNewCol(rawRegion.left());
        this.expandChildrenCols(this.colRoot, startCol);
    }

    private void prepare() throws ReportBuildException {
        if (!this.area.getRowAxis().isEmpty()) {
            this.rowRegion = this.area.getRowAxis().getRegion();
            try {
                this.rowFilters = this.area.getRowAxis().getFilters(this.context);
            }
            catch (ReportAreaExpcetion e) {
                throw new ReportBuildException(e);
            }
        }
        this.createTempGrid();
    }

    private void createTempGrid() throws ReportBuildException {
        int bottom;
        Region rawColRegion = this.area.getColAxis().getRegion();
        int left = this.buildingGrid.locateNewCol(rawColRegion.left());
        int top = this.buildingGrid.locateNewRow(rawColRegion.top());
        int right = this.buildingGrid.locateNewCol(rawColRegion.right());
        if (this.buildingRowEnd <= 0) {
            bottom = this.buildingGrid.locateNewRow(rawColRegion.bottom());
        } else {
            Region rawRowRegion = this.area.getRowAxis().getRegion();
            bottom = this.buildingRowEnd + rawColRegion.bottom() - rawRowRegion.bottom();
        }
        this.tempGrid = this.worksheet.getGridData().protoCreate();
        this.tempGrid.setColCount(right - left + 2);
        this.tempGrid.setRowCount(bottom - top + 2);
        this.tempGrid.copyFrom(this.buildingGrid.getGridData(), left, top, right, bottom, 1, 1);
        this.tempRowMaps = new ArrayList<Integer>(this.tempGrid.getRowCount());
        this.tempRowMaps.add(0);
        if (this.area.getRowAxis().isEmpty()) {
            this.rawToTempGrid();
        } else {
            this.resultToTempGrid(new Region(left, top, right, bottom));
        }
    }

    private void rawToTempGrid() {
        GridData rawGrid = this.worksheet.getGridData();
        Region rawRegion = this.area.getColAxis().getRegion();
        for (int row = rawRegion.top(); row <= rawRegion.bottom(); ++row) {
            int tmpRow = row - rawRegion.top() + 1;
            for (int col = rawRegion.left(); col <= rawRegion.right(); ++col) {
                CellBindingInfo bindingInfo = (CellBindingInfo)rawGrid.getObj(col, row);
                if (bindingInfo == null || bindingInfo.getOwnerArea() != this.area) continue;
                int tmpCol = col - rawRegion.left() + 1;
                if (bindingInfo.isMaster()) {
                    this.tempGrid.setObj(tmpCol, tmpRow, (Object)bindingInfo);
                    continue;
                }
                ExpandingCalcCell calcCell = new ExpandingCalcCell(bindingInfo);
                this.tempGrid.setObj(tmpCol, tmpRow, (Object)calcCell);
            }
            this.tempRowMaps.add(tmpRow, row);
        }
    }

    private void resultToTempGrid(Region region) {
        int tmpCol;
        CellBindingInfo bindingInfo;
        int col;
        int tmpRow;
        int row;
        GridData rawGrid = this.worksheet.getGridData();
        Region rawColRegion = this.area.getColAxis().getRegion();
        Region rawRowRegion = this.area.getRowAxis().getRegion();
        int bottomDelta = rawColRegion.bottom() - rawRowRegion.bottom();
        for (row = rawColRegion.top(); row < rawRowRegion.top(); ++row) {
            tmpRow = row - rawColRegion.top() + 1;
            for (col = rawColRegion.left(); col <= rawColRegion.right(); ++col) {
                bindingInfo = (CellBindingInfo)rawGrid.getObj(col, row);
                if (bindingInfo == null) continue;
                tmpCol = col - rawColRegion.left() + 1;
                this.tempGrid.setObj(tmpCol, tmpRow, (Object)bindingInfo);
            }
            this.tempRowMaps.add(tmpRow, row);
        }
        for (row = rawRowRegion.top(); row <= region.bottom() - bottomDelta; ++row) {
            tmpRow = row - region.top() + 1;
            for (col = region.left(); col <= region.right(); ++col) {
                ExpandingCalcCell bindingObj = (ExpandingCalcCell)this.buildingGrid.getGridData().getObj(col, row);
                if (bindingObj == null) continue;
                tmpCol = col - region.left() + 1;
                this.tempGrid.setObj(tmpCol, tmpRow, bindingObj.clone());
            }
            this.tempRowMaps.add(tmpRow, this.buildingGrid.getRawRow(row));
        }
        for (row = rawColRegion.bottom() - bottomDelta + 1; row <= rawColRegion.bottom(); ++row) {
            int tempRow = region.bottom() - (rawColRegion.top() - 1) - (rawColRegion.bottom() - row);
            for (col = rawColRegion.left(); col <= rawColRegion.right(); ++col) {
                bindingInfo = (CellBindingInfo)rawGrid.getObj(col, row);
                if (bindingInfo == null || bindingInfo.getOwnerArea() != this.area) continue;
                int tempCol = col - rawColRegion.left() + 1;
                this.tempGrid.setObj(tempCol, tempRow, (Object)bindingInfo);
            }
            this.tempRowMaps.add(tempRow, row);
        }
    }

    private int expandChildrenCols(AxisDataNode node, int startCol) throws ReportBuildException {
        List<ExpandingRegion> subRegions;
        Region curRegion;
        if (node.getRegion() == null) {
            curRegion = this.area.getColAxis().getRegion();
            subRegions = this.area.getColAxis().getExpandingRegions();
        } else {
            curRegion = node.getRegion().getRegion();
            subRegions = node.getRegion().getSubRegions();
        }
        int delta = 0;
        GroupAxisDataIterator i = new GroupAxisDataIterator(node.getChildren(), subRegions);
        while (i.hasNext()) {
            AxisDataGroup subGroup = (AxisDataGroup)i.next();
            Region subRegion = subGroup.region.getRegion();
            int firstCol = startCol + delta + subRegion.left() - curRegion.left();
            int nextCol = this.expandGroup(node, subGroup, firstCol);
            delta += nextCol - firstCol - subRegion.colSize();
        }
        return startCol + curRegion.colSize() + delta;
    }

    private int expandGroup(AxisDataNode node, AxisDataGroup subGroup, int startCol) throws ReportBuildException {
        int levels = this.expandTieredLevel(subGroup, startCol);
        int nextCol = subGroup.isEmpty() ? this.doEmptyCol(subGroup.region, startCol, levels) : this.doExpandCol(subGroup, startCol, levels);
        this.mergeColCells(node, startCol, nextCol - 1);
        return nextCol;
    }

    private int expandTieredLevel(AxisDataGroup subGroup, int startCol) throws ReportBuildException {
        if (subGroup.region.getHierarchyMode() != HierarchyMode.TIERED) {
            return 1;
        }
        int initLevel = this.tieredRegion.init(subGroup.region);
        int maxLevel = subGroup.isEmpty() ? initLevel : Math.max(initLevel, subGroup.getMaxLevel());
        for (int i = initLevel + 1; i <= maxLevel; ++i) {
            this.appendRegionLevel(subGroup.region, i, startCol);
        }
        this.tieredRegion.setLevel(maxLevel);
        return maxLevel;
    }

    private void appendRegionLevel(ExpandingRegion subRegion, int level, int startCol) throws ReportBuildException {
        Region region = this.getCellRegion(subRegion.getMasterCell().getCellMap());
        int firstRow = this.buildingGrid.locateNewRow(region.top());
        int nextRow = firstRow + (level - 1) * region.rowSize();
        this.buildingGrid.insertRow(nextRow, region.rowSize(), this.worksheet.getGridData(), region.top());
        GridData grid = this.buildingGrid.getGridData();
        for (int col = 1; col < startCol; ++col) {
            CellField field = grid.expandCell(col, nextRow - 1);
            if (field.left != col || field.bottom != nextRow - 1) continue;
            grid.mergeCells(field.left, field.top, field.right, field.bottom + region.rowSize());
        }
        grid.copyFrom(grid, startCol, firstRow, grid.getColCount() - 1, firstRow + region.rowSize() - 1, startCol, nextRow);
    }

    private Region getCellRegion(CellMap cellMap) {
        CellField field = this.worksheet.getGridData().expandCell(cellMap.getPosition().col(), cellMap.getPosition().row());
        return new Region(field.left, field.top, field.right, field.bottom);
    }

    private int doEmptyCol(ExpandingRegion region, int startCol, int levels) throws ReportBuildException {
        int startRow = this.buildingGrid.locateNewRow(region.getRegion().top());
        if (levels <= 1) {
            this.emptyRegionCells(region, startCol, startRow);
        } else {
            Region rawRegion = region.getRegion();
            Region masterRegion = this.getCellRegion(region.getMasterCell().getCellMap());
            int deltaCol = startCol - rawRegion.left();
            int deltaRow = startRow - rawRegion.top();
            for (int i = 1; i < levels; ++i) {
                int col = masterRegion.left() + deltaCol;
                int row = masterRegion.top() + deltaRow + (i - 1) * masterRegion.rowSize();
                this.emptyCalcCell(col, row);
            }
            this.emptyRegionCells(region, startCol, startRow + (levels - 1) * masterRegion.rowSize());
            this.buildingGrid.getGridData().mergeCells(masterRegion.left() + deltaCol, masterRegion.top() + deltaRow, masterRegion.right() + deltaCol, masterRegion.top() + deltaRow + levels * masterRegion.rowSize());
        }
        return startCol + region.getRegion().colSize();
    }

    private void emptyRegionCells(ExpandingRegion region, int startCol, int startRow) throws ReportBuildException {
        if (this.cleanRegionCells(region, startCol, startRow)) {
            this.mergeAfterTiered(region, startCol, startRow);
        }
    }

    private boolean cleanRegionCells(ExpandingRegion region, int startCol, int startRow) throws ReportBuildException {
        boolean tieredMerge = false;
        Region tmpRegion = this.getTempColRegion(region);
        int deltaCol = startCol - tmpRegion.left();
        int deltaRow = startRow - tmpRegion.top();
        for (int tmpRow = tmpRegion.top(); tmpRow <= tmpRegion.bottom(); ++tmpRow) {
            for (int tmpCol = tmpRegion.left(); tmpCol <= tmpRegion.right(); ++tmpCol) {
                CellBindingInfo bindingInfo;
                Object bindingObj;
                CellField cf = this.tempGrid.expandCell(tmpCol, tmpRow);
                if (cf.left != tmpCol || cf.top != tmpRow || (bindingObj = this.tempGrid.getObj(tmpCol, tmpRow)) == null) continue;
                int col = tmpCol + deltaCol;
                int row = tmpRow + deltaRow;
                int newRow = this.verifyRow(region, row, tmpRow);
                if (row != newRow) {
                    row = newRow;
                    tieredMerge = true;
                }
                this.emptyCalcCell(col, row);
                if (!(bindingObj instanceof CellBindingInfo) || !(bindingInfo = (CellBindingInfo)bindingObj).isHiddenWhenEmpty()) continue;
                this.resetColVisible(startCol, region, bindingInfo, false);
            }
        }
        return tieredMerge;
    }

    private void resetColVisible(int startCol, ExpandingRegion region, CellBindingInfo masterCell, boolean visible) {
        int offset = startCol - region.getRegion().left();
        for (int col = masterCell.getCellMap().getExpandRegion().left(); col <= masterCell.getCellMap().getExpandRegion().right(); ++col) {
            this.buildingGrid.getGridData().setColVisible(col + offset, visible);
        }
    }

    private int doExpandCol(AxisDataGroup subGroup, int startCol, int levels) throws ReportBuildException {
        int nextCol = startCol;
        int nextRow = this.buildingGrid.locateNewRow(subGroup.region.getRegion().top());
        if (levels <= 1) {
            for (int index = 0; index < subGroup.datas.size(); ++index) {
                nextCol = this.expandNext(subGroup, index, nextCol, nextRow);
            }
        } else {
            Region masterRegion = this.getCellRegion(subGroup.region.getMasterCell().getCellMap());
            for (int index = 0; index < subGroup.datas.size(); ++index) {
                nextCol = this.expandNext(subGroup, index, nextCol, nextRow, levels, masterRegion);
            }
        }
        return nextCol;
    }

    private int expandNext(AxisDataGroup group, int index, int startCol, int startRow) throws ReportBuildException {
        AxisDataNode node = group.datas.get(index);
        if (index > 0) {
            this.appendColRegion(node, startCol, startRow);
        }
        this.bindRegionCells(node, startCol, startRow);
        this.pushNode(node);
        int nextCol = this.expandChildrenCols(node, startCol);
        this.mergeHeadColCells(node, startCol, nextCol - 1);
        this.popNode(node);
        return nextCol;
    }

    private int expandNext(AxisDataGroup group, int index, int startCol, int startRow, int levels, Region masterRegion) throws ReportBuildException {
        AxisDataNode node = group.datas.get(index);
        group.setStart(index, startCol);
        if (index > 0) {
            this.appendColRegion(node, startCol, startRow, levels, masterRegion);
        }
        this.bindColRegionCells(group, index, startCol, startRow, levels, masterRegion);
        this.pushNode(node);
        int nextCol = this.expandChildrenCols(node, startCol);
        this.mergeHeadColCells(node, startCol, nextCol - 1);
        this.popNode(node);
        return nextCol;
    }

    private void appendColRegion(AxisDataNode node, int startCol, int startRow) throws ReportBuildException {
        Region rawRegion = this.getTempColRegion(node.getRegion());
        GridData grid = this.buildingGrid.getGridData();
        this.buildingGrid.insertCol(startCol, rawRegion.colSize(), this.worksheet.getGridData(), node.getRegion().getRegion().left());
        grid.copyFrom(this.tempGrid, rawRegion.left(), rawRegion.top(), rawRegion.right(), rawRegion.bottom(), startCol, startRow);
    }

    private void appendColRegion(AxisDataNode node, int startCol, int startRow, int levels, Region masterRegion) throws ReportBuildException {
        Region rawRegion = this.getTempColRegion(node.getRegion());
        GridData grid = this.buildingGrid.getGridData();
        this.buildingGrid.insertCol(startCol, rawRegion.colSize(), this.worksheet.getGridData(), node.getRegion().getRegion().left());
        for (int i = 1; i <= levels; ++i) {
            grid.copyFrom(this.tempGrid, rawRegion.left(), rawRegion.top(), rawRegion.right(), rawRegion.top() + masterRegion.rowSize() - 1, startCol, startRow + (i - 1) * masterRegion.rowSize());
        }
        grid.copyFrom(this.tempGrid, rawRegion.left(), rawRegion.top() + masterRegion.rowSize(), rawRegion.right(), rawRegion.bottom(), startCol, startRow + levels * masterRegion.rowSize());
    }

    private void bindRegionCells(AxisDataNode data, int startCol, int startRow) throws ReportBuildException {
        if (this.applyRegionCells(data, startCol, startRow)) {
            this.mergeAfterTiered(data.getRegion(), startCol, startRow);
        }
    }

    private boolean applyRegionCells(AxisDataNode data, int startCol, int startRow) throws ReportBuildException {
        boolean tieredMerge = false;
        ExpandingRegion region = data.getRegion();
        Region tmpRegion = this.getTempColRegion(region);
        int deltaCol = startCol - tmpRegion.left();
        int deltaRow = startRow - tmpRegion.top();
        for (int tmpRow = tmpRegion.top(); tmpRow <= tmpRegion.bottom(); ++tmpRow) {
            for (int tmpCol = tmpRegion.left(); tmpCol <= tmpRegion.right(); ++tmpCol) {
                Object bindingObj;
                CellField cf = this.tempGrid.expandCell(tmpCol, tmpRow);
                if (cf.left != tmpCol || cf.top != tmpRow || (bindingObj = this.tempGrid.getObj(tmpCol, tmpRow)) == null) continue;
                int col = tmpCol + deltaCol;
                int row = tmpRow + deltaRow;
                int newRow = this.verifyRow(region, row, tmpRow);
                if (newRow != row) {
                    row = newRow;
                    tieredMerge = true;
                }
                this.applyRegionCell(data, startCol, bindingObj, col, row);
            }
        }
        return tieredMerge;
    }

    private void applyRegionCell(AxisDataNode data, int startCol, Object bindingObj, int col, int row) throws ReportBuildException {
        ExpandingRegion region = data.getRegion();
        if (bindingObj instanceof CellBindingInfo) {
            CellBindingInfo bindingInfo = (CellBindingInfo)bindingObj;
            if (region.getMasterCell() == bindingInfo) {
                this.calcMasterCell(data, col, row);
            } else {
                this.bindCalcCell(bindingInfo, data, col, row);
            }
            if (bindingInfo.isHiddenWhenEmpty()) {
                this.resetColVisible(startCol, region, bindingInfo, true);
            }
        } else if (bindingObj instanceof ExpandingCalcCell) {
            this.bindCalcCell((ExpandingCalcCell)bindingObj, data, col, row);
        }
    }

    private int verifyRow(ExpandingRegion region, int row, int tmpRow) throws ReportBuildException {
        if (region.getMasterCell().isHiddenWhenEmpty() || this.tieredRegion.isEmpty()) {
            return row;
        }
        int rawRow = this.tempRowMaps.get(tmpRow);
        if (this.buildingGrid.getRawRow(row) == rawRow) {
            return row;
        }
        return this.buildingGrid.locateNewRow(rawRow);
    }

    private void mergeAfterTiered(ExpandingRegion region, int startCol, int startRow) {
        Region curRegion = this.getCellRegion(region.getMasterCell().getCellMap());
        if (curRegion.bottom() != this.tieredRegion.getMasterRegion().bottom() || this.tieredRegion.getLevel() <= 1) {
            return;
        }
        this.buildingGrid.getGridData().mergeCells(startCol, startRow, startCol + curRegion.colSize() - 1, startRow + curRegion.rowSize() - 1 + (this.tieredRegion.getLevel() - 1) * this.tieredRegion.getMasterRegion().rowSize());
    }

    private void bindColRegionCells(AxisDataGroup group, int index, int startCol, int startRow, int levels, Region masterRegion) throws ReportBuildException {
        AxisDataNode data = group.datas.get(index);
        Region rawRegion = this.getTempColRegion(data.getRegion());
        this.bindTieredHeaderCells(group, index, startCol, startRow, levels, masterRegion, rawRegion);
        this.bindTieredBodyCells(data, startCol, startRow, levels, masterRegion, rawRegion);
    }

    private void bindTieredHeaderCells(AxisDataGroup group, int index, int startCol, int startRow, int levels, Region masterRegion, Region rawRegion) throws ReportBuildException {
        AxisDataNode data = group.datas.get(index);
        this.calcMasterCell(data, startCol, startRow + (data.getLevel() - 1) * masterRegion.rowSize());
        if (index >= group.datas.size() - 1 || group.datas.get(index + 1).getLevel() <= data.getLevel()) {
            if (levels > data.getLevel()) {
                this.buildingGrid.getGridData().mergeCells(startCol, startRow + (data.getLevel() - 1) * masterRegion.rowSize(), startCol + masterRegion.colSize() - 1, startRow + levels * masterRegion.rowSize() - 1);
            }
        } else {
            int row = startRow + data.getLevel() * masterRegion.rowSize();
            this.calcMasterCell(data, startCol, row);
            this.buildingGrid.getGridData().setCellData(startCol, row, TIERED_TITLE);
            if (levels > data.getLevel() + 1) {
                this.buildingGrid.getGridData().mergeCells(startCol, row, startCol + masterRegion.colSize() - 1, startRow + levels * masterRegion.rowSize() - 1);
            }
        }
        if (index < group.datas.size() - 1 && group.datas.get(index + 1).getLevel() >= data.getLevel()) {
            return;
        }
        int toLevel = index < group.datas.size() - 1 ? group.datas.get(index + 1).getLevel() : 1;
        for (int level = data.getLevel() - 1; level >= toLevel; --level) {
            int fromIndex = group.lastIndexOf(level, index);
            if (fromIndex < 0) continue;
            int left = group.getStart(fromIndex);
            int top = startRow + (level - 1) * masterRegion.rowSize();
            int right = startCol + masterRegion.colSize() - 1;
            int bottom = top + masterRegion.rowSize() - 1;
            this.buildingGrid.getGridData().mergeCells(left, top, right, bottom);
        }
    }

    private void bindTieredBodyCells(AxisDataNode data, int startCol, int startRow, int levels, Region masterRegion, Region rawRegion) throws ReportBuildException {
        int deltaCol = startCol - rawRegion.left();
        int deltaRow = startRow - rawRegion.top() + (levels - 1) * masterRegion.rowSize();
        for (int rawRow = rawRegion.top() + masterRegion.rowSize(); rawRow <= rawRegion.bottom(); ++rawRow) {
            for (int rawCol = rawRegion.left(); rawCol <= rawRegion.right(); ++rawCol) {
                CellField cf = this.tempGrid.expandCell(rawCol, rawRow);
                if (cf.left != rawCol || cf.top != rawRow) continue;
                Object bindingObj = this.tempGrid.getObj(rawCol, rawRow);
                int col = rawCol + deltaCol;
                int row = rawRow + deltaRow;
                if (bindingObj instanceof CellBindingInfo) {
                    CellBindingInfo bindingInfo = (CellBindingInfo)bindingObj;
                    if (data.getRegion().getMasterCell() == bindingInfo) {
                        this.calcMasterCell(data, col, row);
                        continue;
                    }
                    this.bindCalcCell(bindingInfo, data, col, row);
                    continue;
                }
                if (!(bindingObj instanceof ExpandingCalcCell)) continue;
                this.bindCalcCell((ExpandingCalcCell)bindingObj, data, col, row);
            }
        }
    }

    private void mergeHeadColCells(AxisDataNode node, int left, int right) throws ReportBuildException {
        int rawTop = node.getRegion().getRegion().top();
        int top = this.buildingGrid.locateNewRow(rawTop);
        Position rawPos = node.getRegion().getMasterCellPosition();
        CellField cf = this.worksheet.getGridData().expandCell(rawPos.col(), rawPos.row());
        int bottom = this.buildingGrid.locateLastRow(cf.bottom);
        this.colMerge(node.getRegion(), left, top, right, bottom);
    }

    private Region getTempColRegion(ExpandingRegion region) {
        Region areaRegion = this.area.getColAxis().getRegion();
        Region rawRegion = region.getRegion();
        int left = rawRegion.left() - areaRegion.left() + 1;
        int top = rawRegion.top() - areaRegion.top() + 1;
        int right = rawRegion.right() - areaRegion.left() + 1;
        int bottom = this.tempGrid.getRowCount() - 1;
        return new Region(left, top, right, bottom);
    }

    private void mergeColCells(AxisDataNode node, int left, int right) throws ReportBuildException {
        if (node.getChildren().isEmpty() || node.getRegion() == null) {
            return;
        }
        Region subRegion = node.getRegion().expandSubRegion();
        Position masterPos = node.getRegion().getMasterCellPosition();
        CellField cf = this.worksheet.getGridData().expandCell(masterPos.col(), masterPos.row());
        if (cf.bottom + 1 < subRegion.top()) {
            int top = this.buildingGrid.locateNewRow(cf.bottom + 1);
            int bottom = this.buildingGrid.locateNewRow(subRegion.top() - 1);
            this.colMerge(node.getRegion(), left, top, right, bottom);
        }
        Region masterRegion = node.getRegion().getRegion();
        if (subRegion.bottom() < masterRegion.bottom()) {
            int top = this.buildingGrid.locateNewRow(subRegion.bottom() + 1);
            int bottom = this.buildingGrid.locateNewRow(masterRegion.bottom());
            this.colMerge(node.getRegion(), left, top, right, bottom);
        }
    }

    private void colMerge(ExpandingRegion region, int left, int top, int right, int bottom) {
        GridData grid = this.buildingGrid.getGridData();
        for (int row = top; row <= bottom; ++row) {
            int col = left;
            while (col < right) {
                CellField cf = grid.expandCell(col, row);
                if (cf.top != row || this.isSubRegionCell(region, col, row)) {
                    ++col;
                    continue;
                }
                int endCol = this.findMergeCol(cf, right);
                if (endCol > cf.right) {
                    grid.mergeCells(cf.left, cf.top, endCol, cf.bottom);
                }
                col = endCol + 1;
            }
        }
    }

    private int findMergeCol(CellField field, int right) {
        int nextRawCol;
        int originLeft = this.buildingGrid.getRawCol(field.left);
        int originRight = this.buildingGrid.getRawCol(field.right);
        int nextCol = field.right + 1;
        while (nextCol <= right && (nextRawCol = this.buildingGrid.getRawCol(nextCol)) >= originLeft && nextRawCol <= originRight) {
            CellField nextField = this.buildingGrid.getGridData().expandCell(nextCol, field.top);
            if (nextField.left != nextCol || nextField.top != field.top) break;
            nextCol = nextField.right + 1;
        }
        return nextCol - 1;
    }

    @Override
    protected void fillOtherRestrictions(ExpandingCalcCell calcCell) {
        if (this.rowRegion != null && this.rowFilters != null && !this.rowRegion.contains(calcCell.getCell().getPosition().getPosition())) {
            calcCell.getExtraFilters().addAll(this.rowFilters);
        }
    }

    @Override
    protected void setCellIndent(int col, int row, AxisDataNode data, CellBindingInfo bindingInfo) {
    }

    private final class TieredRegion {
        private ExpandingRegion region;
        private Region masterRegion;
        private int level = 1;

        public int init(ExpandingRegion region) throws ReportBuildException {
            if (this.region == null) {
                this.region = region;
                this.masterRegion = ColExpander.this.getCellRegion(region.getMasterCell().getCellMap());
            } else if (this.region != region) {
                throw new ReportBuildException("\u68c0\u6d4b\u5230\u591a\u4e2a\u680f\u65b9\u5411\u7684\u5c42\u7ea7\u663e\u793a\u533a\u57df");
            }
            return this.level;
        }

        public boolean isEmpty() {
            return this.region == null;
        }

        public Region getMasterRegion() {
            return this.masterRegion;
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(int level) {
            if (this.level < level) {
                this.level = level;
            }
        }

        public String toString() {
            StringJoiner joiner = new StringJoiner(", ", "[", "]");
            if (this.region != null) {
                joiner.add(this.region.toString());
                joiner.add("level = " + this.level);
            }
            return joiner.toString();
        }
    }
}

