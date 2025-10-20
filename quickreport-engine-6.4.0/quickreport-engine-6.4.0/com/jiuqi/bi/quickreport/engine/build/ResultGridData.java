/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.build;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.ArrayList;
import java.util.List;

public final class ResultGridData {
    private GridData gridData;
    private List<Integer> rowMaps;
    private List<Boolean> rawRows;
    private List<Integer> colMaps;
    private List<Boolean> rawCols;

    public ResultGridData(GridData gridData) {
        int i;
        this.gridData = gridData;
        this.rowMaps = new ArrayList<Integer>(gridData.getRowCount());
        this.rawRows = new ArrayList<Boolean>(gridData.getRowCount());
        for (i = 0; i < gridData.getRowCount(); ++i) {
            this.rowMaps.add(i);
            this.rawRows.add(true);
        }
        this.colMaps = new ArrayList<Integer>(gridData.getColCount());
        this.rawCols = new ArrayList<Boolean>(gridData.getColCount());
        for (i = 0; i < gridData.getColCount(); ++i) {
            this.colMaps.add(i);
            this.rawCols.add(true);
        }
        gridData.setOptions((gridData.getOptions() | 0x10) & 0xFFFFFFFB);
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public Position locateNewPostion(Position rawPos) throws ReportBuildException {
        int col = this.locateNewCol(rawPos.col());
        int row = this.locateNewRow(rawPos.row());
        return Position.valueOf((int)col, (int)row);
    }

    public int locateNewCol(int rawCol) throws ReportBuildException {
        for (int i = rawCol; i < this.colMaps.size(); ++i) {
            if (rawCol != this.colMaps.get(i)) continue;
            return i;
        }
        throw new ReportBuildException("\u5728\u7ed3\u679c\u8868\u683c\u4e2d\u5b9a\u4f4d\u539f\u59cb\u5217\uff08" + Position.nameOfCol((int)rawCol) + "\uff09\u5931\u8d25\u3002");
    }

    public int locateLastCol(int rawCol) throws ReportBuildException {
        for (int i = this.colMaps.size() - 1; i >= rawCol; --i) {
            if (rawCol != this.colMaps.get(i)) continue;
            return i;
        }
        throw new ReportBuildException("\u5728\u7ed3\u679c\u8868\u683c\u4e2d\u5b9a\u4f4d\u539f\u59cb\u5217\uff08" + Position.nameOfCol((int)rawCol) + "\uff09\u5931\u8d25\u3002");
    }

    public int locateNewRow(int rawRow) throws ReportBuildException {
        for (int i = rawRow; i < this.rowMaps.size(); ++i) {
            if (rawRow != this.rowMaps.get(i)) continue;
            return i;
        }
        throw new ReportBuildException("\u5728\u7ed3\u679c\u8868\u683c\u4e2d\u5b9a\u4f4d\u539f\u59cb\u884c\uff08" + rawRow + "\uff09\u5931\u8d25\u3002");
    }

    public int locateLastRow(int rawRow) throws ReportBuildException {
        for (int i = this.rowMaps.size() - 1; i >= rawRow; --i) {
            if (rawRow != this.rowMaps.get(i)) continue;
            return i;
        }
        throw new ReportBuildException("\u5728\u7ed3\u679c\u8868\u683c\u4e2d\u5b9a\u4f4d\u539f\u59cb\u884c\uff08" + rawRow + "\uff09\u5931\u8d25\u3002");
    }

    public Region locateNewRegion(Region rawRegion) throws ReportBuildException {
        int left = this.locateNewCol(rawRegion.left());
        int right = this.locateLastCol(rawRegion.right());
        int top = this.locateNewRow(rawRegion.top());
        int bottom = this.locateLastRow(rawRegion.bottom());
        return new Region(Position.valueOf((int)left, (int)top), Position.valueOf((int)right, (int)bottom));
    }

    public int getRawCol(int col) {
        return this.colMaps.get(col);
    }

    public boolean isRawCol(int col) {
        return this.rawCols.get(col);
    }

    public int getRawRow(int row) {
        return this.rowMaps.get(row);
    }

    public boolean isRawRow(int row) {
        return this.rawRows.get(row);
    }

    public void insertRow(int start, int count, GridData rawGrid, int rawStart) {
        int i;
        this.gridData.insertRow(start, count);
        for (i = 0; i < count; ++i) {
            this.rowMaps.add(start + i, rawStart + i);
            this.rawRows.add(start + i, false);
        }
        for (i = 0; i < count - 1; ++i) {
            this.gridData.setRowVisible(start + i, rawGrid.getRowVisible(rawStart + i));
            this.gridData.setRowAutoSize(start + i, rawGrid.getRowAutoSize(rawStart + i));
            this.gridData.setRowHeights(start + i, rawGrid.getRowHeights(rawStart + i));
        }
    }

    public void insertCol(int start, int count, GridData rawGrid, int rawStart) {
        int i;
        this.gridData.insertCol(start, count);
        for (i = 0; i < count; ++i) {
            this.colMaps.add(start + i, rawStart + i);
            this.rawCols.add(start + i, false);
        }
        for (i = 0; i < count - 1; ++i) {
            this.gridData.setColVisible(start + i, rawGrid.getColVisible(rawStart + i));
            this.gridData.setColAutoSize(start + i, rawGrid.getColAutoSize(rawStart + i));
            this.gridData.setColWidths(start + i, rawGrid.getColWidths(rawStart + i));
        }
    }

    public List<Integer> getExpandedCols(int rawCol) {
        return ResultGridData.expandPosition(rawCol, this.colMaps);
    }

    public List<Integer> getExpandedRows(int rawRow) {
        return ResultGridData.expandPosition(rawRow, this.rowMaps);
    }

    private static List<Integer> expandPosition(int rawPos, List<Integer> maps) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (int i = rawPos; i < maps.size(); ++i) {
            if (maps.get(i) != rawPos) continue;
            positions.add(i);
        }
        return positions;
    }

    public List<Integer> getExpandedCols(int rawLeft, int rawRight) {
        return ResultGridData.expandPosition(rawLeft, rawRight, this.colMaps);
    }

    public List<Integer> getExpandedRows(int rawTop, int rawBottom) {
        return ResultGridData.expandPosition(rawTop, rawBottom, this.rowMaps);
    }

    private static List<Integer> expandPosition(int rawLow, int rawHigh, List<Integer> maps) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        if (rawLow == -1 && rawHigh == -1) {
            for (int i = 1; i < maps.size(); ++i) {
                positions.add(i);
            }
        } else {
            for (int i = rawLow; i < maps.size(); ++i) {
                int raw = maps.get(i);
                if (raw < rawLow || raw > rawHigh) continue;
                positions.add(i);
            }
        }
        return positions;
    }

    public Region mergeExpandingRegion(ResultGridData fromGrid, Region fromRegion, ExpandingArea area) throws ReportBuildException {
        Region rawRegion = area.getRegion();
        Region miniRegion = area.getMiniRegion();
        int toLeft = this.locateNewCol(rawRegion.left());
        int toTop = this.locateNewRow(rawRegion.top());
        this.prepareRows(fromGrid, fromRegion.top(), fromRegion.rowSize() - (rawRegion.rowSize() - miniRegion.rowSize()), toTop, miniRegion.rowSize());
        this.prepareCols(fromGrid, fromRegion.left(), fromRegion.colSize() - (rawRegion.colSize() - miniRegion.colSize()), toLeft, miniRegion.colSize());
        this.copyGridRegion(fromGrid, fromRegion, toLeft, toTop);
        return fromRegion.offset(toLeft - fromRegion.left(), toTop - fromRegion.top());
    }

    private void prepareRows(ResultGridData fromGrid, int fromTop, int count, int toTop, int rawCount) {
        int row;
        int i;
        if (this.isRowsMatched(fromGrid, fromTop, toTop, count)) {
            return;
        }
        int appenedCount = 0;
        for (i = 0; i < count - rawCount && (row = toTop + rawCount + i) < this.rawRows.size() && !Boolean.TRUE.equals(this.rawRows.get(row)); ++i) {
            ++appenedCount;
        }
        for (i = 0; i < rawCount + appenedCount; ++i) {
            this.rowMaps.set(toTop + i, fromGrid.getRawRow(fromTop + i));
            this.rawRows.set(toTop + i, fromGrid.isRawRow(fromTop + i));
        }
        if (rawCount + appenedCount < count) {
            this.gridData.insertRow(toTop + rawCount + appenedCount, count - appenedCount - rawCount);
            for (i = rawCount + appenedCount; i < count; ++i) {
                this.rowMaps.add(toTop + i, fromGrid.getRawRow(fromTop + i));
                this.rawRows.add(toTop + i, false);
                boolean visible = fromGrid.getGridData().getRowVisible(fromTop + i);
                this.gridData.setRowVisible(toTop + i, visible);
            }
        }
    }

    private boolean isRowsMatched(ResultGridData fromGrid, int fromTop, int toTop, int count) {
        for (int i = 0; i < count; ++i) {
            int fromRow = fromTop + i;
            if (fromRow >= fromGrid.getGridData().getRowCount()) {
                return false;
            }
            int toRow = toTop + i;
            if (toRow >= this.gridData.getRowCount()) {
                return false;
            }
            if (fromGrid.isRawRow(fromRow) == this.isRawRow(toRow) && fromGrid.getRawRow(fromRow) == this.getRawRow(toRow)) continue;
            return false;
        }
        return true;
    }

    private void prepareCols(ResultGridData fromGrid, int fromLeft, int count, int toLeft, int rawCount) {
        int col;
        int i;
        if (this.isColsMatched(fromGrid, fromLeft, toLeft, count)) {
            return;
        }
        int appendedCount = 0;
        for (i = 0; i < count - rawCount && (col = toLeft + rawCount + i) < this.rawCols.size() && !Boolean.TRUE.equals(this.rawCols.get(col)); ++i) {
            ++appendedCount;
        }
        for (i = 0; i < rawCount + appendedCount; ++i) {
            this.colMaps.set(toLeft + i, fromGrid.getRawCol(fromLeft + i));
            this.rawCols.set(toLeft + i, fromGrid.isRawCol(fromLeft + i));
        }
        if (rawCount + appendedCount < count) {
            this.gridData.insertCol(toLeft + rawCount + appendedCount, count - rawCount - appendedCount);
            for (i = rawCount + appendedCount; i < count; ++i) {
                this.colMaps.add(toLeft + i, fromGrid.getRawCol(fromLeft + i));
                this.rawCols.add(toLeft + i, false);
                boolean visible = fromGrid.getGridData().getColVisible(fromLeft + i);
                this.gridData.setColVisible(toLeft + i, visible);
            }
        }
    }

    private boolean isColsMatched(ResultGridData fromGrid, int fromLeft, int toLeft, int count) {
        for (int i = 0; i < count; ++i) {
            int fromCol = fromLeft + i;
            if (fromCol >= fromGrid.getGridData().getColCount()) {
                return false;
            }
            int toCol = toLeft + i;
            if (toCol >= this.gridData.getColCount()) {
                return false;
            }
            if (fromGrid.isRawCol(fromCol) == this.isRawCol(toCol) && fromGrid.getRawCol(fromCol) == this.getRawCol(toCol)) continue;
            return false;
        }
        return true;
    }

    private void copyGridRegion(ResultGridData fromGrid, Region fromRegion, int toLeft, int toTop) {
        this.gridData.copyFrom(fromGrid.gridData, fromRegion.left(), fromRegion.top(), fromRegion.right(), fromRegion.bottom(), toLeft, toTop);
        int deltaCol = toLeft - fromRegion.left();
        int deltaRow = toTop - fromRegion.top();
        for (int row = fromRegion.top(); row <= fromRegion.bottom(); ++row) {
            for (int col = fromRegion.left(); col <= fromRegion.right(); ++col) {
                Object obj = fromGrid.gridData.getObj(col, row);
                this.gridData.setObj(col + deltaCol, row + deltaRow, obj);
            }
        }
    }

    public int[] getColMaps() {
        return ResultGridData.toMaps(this.colMaps);
    }

    public int[] getRowMaps() {
        return ResultGridData.toMaps(this.rowMaps);
    }

    private static int[] toMaps(List<Integer> maps) {
        int[] arr = new int[maps.size()];
        for (int i = 0; i < maps.size(); ++i) {
            arr[i] = maps.get(i) == null ? -1 : maps.get(i);
        }
        return arr;
    }

    public void setRowsVisible(int top, int bottom, boolean visible) {
        for (int row = top; row <= bottom; ++row) {
            this.gridData.setRowVisible(row, visible);
        }
    }

    public void setColsVisible(int left, int right, boolean visible) {
        for (int col = left; col <= right; ++col) {
            this.gridData.setColVisible(col, visible);
        }
    }
}

