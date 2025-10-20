/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.graphics.ImageDescriptor;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.np.grid2.BIFF2;
import com.jiuqi.np.grid2.CellStringList;
import com.jiuqi.np.grid2.CellStringListItem;
import com.jiuqi.np.grid2.ColumnInfo;
import com.jiuqi.np.grid2.Grid2CellField;
import com.jiuqi.np.grid2.Grid2FieldList;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.LogUtil;
import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.RowInfo;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Grid2Data
implements Serializable {
    private static final long serialVersionUID = -3100428550650844910L;
    private int rowCount;
    private int colCount;
    private List<RowInfo> rows;
    private List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
    private List<List<GridCellData>> cells;
    private boolean rowResizeEnabled = false;
    private boolean rowFreeResizeEnabled = false;
    private boolean columnFreeResizeEnabled = false;
    private boolean rowSelectEnabled = false;
    private boolean columnResizeEnabled = false;
    private boolean columnSelectEnabled = false;
    private boolean dirty = false;
    private int headColumnCount;
    private int headRowCount;
    private int footColumnCount;
    private int footRowCount;
    private boolean showSelectingBorder = false;
    private Grid2FieldList rects;
    private CellStringList overFlowShowTextList;
    private CellStringList overFlowEditTextList;
    private CellStringList overFlowDataExList;
    private CellStringList overFitFontSizeList;
    private String script;

    public Grid2Data() {
        this.rows = new ArrayList<RowInfo>();
        this.cells = new ArrayList<List<GridCellData>>();
        this.rects = new Grid2FieldList();
        this.overFlowShowTextList = new CellStringList();
        this.overFlowEditTextList = new CellStringList();
        this.overFlowDataExList = new CellStringList();
        this.overFitFontSizeList = new CellStringList();
    }

    public GridCellData getGridCellData(int col, int row) {
        try {
            return this.cells.get(row).get(col);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void exchangeColumns(int colIndex1, int colIndex2) {
        for (List<GridCellData> row : this.cells) {
            this.exchangeListItem(row, colIndex1, colIndex2);
            GridCellData c1 = row.get(colIndex1);
            GridCellData c2 = row.get(colIndex2);
            int ci = c1.getColIndex();
            int ri = c1.getRowIndex();
            c1.setColIndex(c2.getColIndex());
            c1.setRowIndex(c2.getRowIndex());
            c2.setColIndex(ci);
            c2.setRowIndex(ri);
        }
        this.exchangeListItem(this.columns, colIndex1, colIndex2);
    }

    private void exchangeListItem(List list, int pos1, int pos2) {
        if (list == null || pos1 >= list.size() || pos2 >= list.size() || pos1 == pos2 || pos1 < 0 || pos2 < 0) {
            return;
        }
        int big = Math.max(pos1, pos2);
        int small = big == pos1 ? pos2 : pos1;
        Object o1 = list.remove(small);
        Object o2 = list.remove(big - 1);
        list.add(small, o2);
        list.add(big, o1);
    }

    public void setGridCellData(GridCellData gridCellData, int col, int row) {
        List<GridCellData> gridRow = this.cells.get(col);
        gridRow.remove(row);
        gridRow.add(row, gridCellData);
    }

    public int getRowBackgroundColor(int rowIndex) {
        try {
            return this.rows.get(rowIndex).getRowBackgroundColor();
        }
        catch (Exception e) {
            return 0xFFFFFF;
        }
    }

    public void setRowBackgroundColor(int rowIndex, int rowBackgroundColor) {
        this.rows.get(rowIndex).setRowBackgroundColor(rowBackgroundColor);
    }

    public void setRowBackImage(int rowIndex, ImageDescriptor image) {
        this.rows.get(rowIndex).setRowBackImage(image);
    }

    public ImageDescriptor getRowBackImage(int rowIndex) {
        try {
            return this.rows.get(rowIndex).getRowBackImage();
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean isShowSelectingBorder() {
        return this.showSelectingBorder;
    }

    public void setShowSelectingBorder(boolean showSelectingBorder) {
        this.showSelectingBorder = showSelectingBorder;
    }

    public void setColumnCount(int count) {
        count = Math.max(count, 0);
        if (this.cells.size() != 0) {
            if (count > this.columns.size()) {
                this.insertColumns(this.columns.size(), count - this.columns.size());
            } else if (count < this.columns.size()) {
                this.deleteColumns(count, this.columns.size() - count);
            }
        } else {
            this.colCount = count;
            if (this.columns.size() == 0) {
                this.initColumnList(this.columns, count);
            }
            if (this.rows.size() != 0) {
                this.initCells(this.cells, this.colCount, this.rowCount);
            }
        }
    }

    public void setRowCount(int count) {
        count = Math.max(count, 0);
        if (this.cells.size() != 0) {
            if (count > this.rows.size()) {
                this.insertRows(this.rows.size(), count - this.rows.size(), false, 0);
            } else if (count < this.rows.size()) {
                this.deleteRows(count, this.rows.size() - count);
            }
        } else {
            this.rowCount = count;
            if (this.rows.size() == 0) {
                this.initRowList(this.rows, count);
            }
            if (this.columns.size() != 0) {
                this.initCells(this.cells, this.colCount, this.rowCount);
            }
        }
    }

    public void setRowResizeEnabled(boolean value) {
        this.rowResizeEnabled = value;
    }

    public boolean isRowResizeEnabled() {
        return this.rowResizeEnabled;
    }

    public void setRowFreeResizeEnabled(boolean value) {
        this.rowFreeResizeEnabled = value;
    }

    public boolean isRowFreeResizeEnabled() {
        return this.rowFreeResizeEnabled;
    }

    public void setRowSelectEnabled(boolean value) {
        this.rowSelectEnabled = value;
    }

    public boolean isRowSelectEnabled() {
        return this.rowSelectEnabled;
    }

    public void setColumnResizeEnabled(boolean flag) {
        this.columnResizeEnabled = flag;
    }

    public boolean isColResizeEnabled() {
        return this.columnResizeEnabled;
    }

    public void setColomnFreeResizeEnabled(boolean value) {
        this.columnFreeResizeEnabled = value;
    }

    public boolean isColFreeResizeEnabled() {
        return this.columnFreeResizeEnabled;
    }

    public void setColSelectEnabled(boolean value) {
        this.columnSelectEnabled = value;
    }

    public boolean isColSelectEnabled() {
        return this.columnSelectEnabled;
    }

    public void clearDirty() {
        this.dirty = false;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setHeaderColumnCount(int value) {
        this.headColumnCount = value = Math.max(value, 0);
    }

    public void setHeaderRowCount(int value) {
        this.headRowCount = value = Math.max(value, 0);
    }

    public void setFooterRowCount(int value) {
        this.footRowCount = value = Math.max(value, 0);
    }

    public void setFooterColumnCount(int value) {
        this.footColumnCount = value = Math.max(value, 0);
    }

    public void setColumnWidth(int column, int width) {
        this.columns.get(column).setColWidth(width);
    }

    public void setColumnGrab(int column, boolean value) {
        this.columns.get(column).setColumnGrab(value);
    }

    public boolean getColumnGrab(int column) {
        if (column >= 0 && column < this.columns.size()) {
            return this.columns.get(column).getColumnGrab();
        }
        return false;
    }

    public void setRowHeight(int row, int height) {
        this.rows.get(row).setRowHeight(height);
    }

    public void insertColumns(int index, int count) {
        if (index <= this.getHeaderColumnCount()) {
            this.setHeaderColumnCount(this.getHeaderColumnCount() + count);
        }
        for (int i = 0; i < this.rects.count(); ++i) {
            Grid2CellField rect = this.rects.get(i);
            if (rect.right >= index && rect.left < index) {
                GridCellData merge = this.cells.get(rect.top).get(rect.left);
                merge.setColSpan(merge.getColSpan() + count);
                rect.right += count;
            }
            if (rect.left < index) continue;
            rect.left += count;
            rect.right += count;
        }
        for (int j = 0; j < count; ++j) {
            this.columns.add(index, new ColumnInfo());
        }
        for (int i = 0; i < this.getRowCount(); ++i) {
            int j;
            List<GridCellData> row = this.cells.get(i);
            for (j = index; j < this.getColumnCount(); ++j) {
                GridCellData otherCell = row.get(j);
                if (otherCell.getMergeInfo() != null && otherCell.getMergeInfo().x >= index) {
                    otherCell.setMergeInfo(new Point(otherCell.getMergeInfo().x + count, otherCell.getMergeInfo().y));
                }
                otherCell.setColIndex(j + count);
            }
            for (j = 0; j < count; ++j) {
                GridCellData cellData = new GridCellData(index + count - j - 1, i);
                row.add(index, cellData);
                this.setMerge(cellData, true);
            }
        }
        this.colCount += count;
    }

    private void setMerge(GridCellData cellData, boolean isCol) {
        int ri = cellData.getRowIndex();
        int ci = cellData.getColIndex();
        boolean condition1 = false;
        boolean condition2 = false;
        for (int i = 0; i < this.rects.count(); ++i) {
            Grid2CellField rect = this.rects.get(i);
            if (isCol) {
                condition1 = rect.left < ci;
                condition2 = rect.top <= ri;
            } else {
                condition1 = rect.left <= ci;
                boolean bl = condition2 = rect.top < ri;
            }
            if (!condition1 || rect.right < ci || !condition2 || rect.bottom < ri) continue;
            cellData.setMerged(true);
            cellData.setMergeInfo(new Point(rect.left, rect.top));
        }
    }

    public void insertColumns(int index, int count, int copyIndex) {
        if (index <= this.getHeaderColumnCount()) {
            this.setHeaderColumnCount(this.getHeaderColumnCount() + count);
        }
        for (int i = 0; i < this.rects.count(); ++i) {
            Grid2CellField rect = this.rects.get(i);
            if (rect.right >= index && rect.left < index) {
                GridCellData merge = this.cells.get(rect.top).get(rect.left);
                merge.setColSpan(merge.getColSpan() + count);
                rect.right += count;
            }
            if (rect.left < index) continue;
            rect.left += count;
            rect.right += count;
        }
        ColumnInfo copyFrom = this.columns.get(copyIndex);
        for (int j = 0; j < count; ++j) {
            this.columns.add(index, new ColumnInfo(copyFrom));
        }
        for (int i = 0; i < this.getRowCount(); ++i) {
            int j;
            List<GridCellData> row = this.cells.get(i);
            for (j = index; j < this.getColumnCount(); ++j) {
                GridCellData otherCell = row.get(j);
                if (otherCell.getMergeInfo() != null && otherCell.getMergeInfo().x >= index) {
                    otherCell.setMergeInfo(new Point(otherCell.getMergeInfo().x + count, otherCell.getMergeInfo().y));
                }
                otherCell.setColIndex(j + count);
            }
            GridCellData copyCell = row.get(copyIndex);
            for (j = 0; j < count; ++j) {
                GridCellData newCell = new GridCellData(copyCell, index + count - j - 1, i);
                newCell.setMerged(false);
                newCell.setMergeInfo(null);
                row.add(index, newCell);
                this.setMerge(newCell, true);
            }
        }
        this.colCount += count;
    }

    public void deleteColumns(int index, int count) {
        if (index < this.getHeaderColumnCount()) {
            this.setHeaderColumnCount(this.getHeaderColumnCount() - count);
        }
        for (int i = this.rects.count() - 1; i >= 0; --i) {
            Grid2CellField rect = this.rects.get(i);
            if (rect.left >= index) {
                if (rect.left > index + count - 1) {
                    rect.left -= count;
                    rect.right -= count;
                    continue;
                }
                if (rect.right > index + count - 1) {
                    rect.left = index;
                    rect.right -= count;
                    continue;
                }
                this.rects.remove(i);
                continue;
            }
            if (rect.right > index + count - 1) {
                rect.right -= count;
                continue;
            }
            if (rect.right < index) continue;
            rect.right = index - 1;
        }
        for (int j = 0; j < count; ++j) {
            this.columns.remove(index);
        }
        for (int i = 0; i < this.getRowCount(); ++i) {
            int j;
            List<GridCellData> row = this.cells.get(i);
            for (j = index; j < this.getColumnCount(); ++j) {
                GridCellData otherCell = row.get(j);
                if (otherCell.getMergeInfo() != null && otherCell.getMergeInfo().x >= index) {
                    if (otherCell.getMergeInfo().x >= index + count) {
                        otherCell.setMergeInfo(new Point(otherCell.getMergeInfo().x - count, otherCell.getMergeInfo().y));
                    } else {
                        otherCell.setMergeInfo(new Point(index, otherCell.getMergeInfo().y));
                    }
                }
                otherCell.setColIndex(j - count);
            }
            for (j = 0; j < count; ++j) {
                row.remove(index);
            }
        }
        this.colCount -= count;
    }

    public void insertRows(int index, int count, boolean relative, int expendCol) {
        Point mergeInfo;
        List<GridCellData> row;
        int i;
        if (index <= this.getHeaderRowCount()) {
            this.setHeaderRowCount(this.getHeaderRowCount() + count);
        }
        for (i = 0; i < this.rects.count(); ++i) {
            Grid2CellField rect = this.rects.get(i);
            if (rect.top >= index) {
                rect.top += count;
            }
            if (rect.bottom < index) continue;
            rect.bottom += count;
        }
        for (int j = index; j < this.getRowCount(); ++j) {
            row = this.cells.get(j);
            for (int i2 = 0; i2 < this.getColumnCount(); ++i2) {
                GridCellData cellData = row.get(i2);
                cellData.setRowIndex(j + count);
                mergeInfo = cellData.getMergeInfo();
                if (mergeInfo == null || mergeInfo.y < index) continue;
                cellData.setMergeInfo(new Point(mergeInfo.x, mergeInfo.y + count));
            }
        }
        for (i = 0; i < count; ++i) {
            row = new ArrayList<GridCellData>();
            for (int j = 0; j < this.getColumnCount(); ++j) {
                GridCellData cell = index > 1 ? new GridCellData(this.cells.get(index - 1).get(j), j, index + count - i - 1) : new GridCellData(j, index + count - i - 1);
                if (index < this.getRowCount()) {
                    if (this.mergedBySameCell(this.cells.get(index - 1).get(j), this.cells.get(index).get(j))) {
                        mergeInfo = this.cells.get(index - 1).get(j).getMergeInfo();
                        cell.setMerged(true);
                        cell.setMergeInfo(mergeInfo);
                        if (mergeInfo.x == j) {
                            this.cells.get(mergeInfo.y).get(j).setRowSpan(this.cells.get(mergeInfo.y).get(j).getRowSpan() + 1);
                        }
                    } else if (this.mergedByCell(this.cells.get(index - 1).get(j), this.cells.get(index).get(j))) {
                        cell.setMerged(true);
                        this.cells.get(index - 1).get(j).setRowSpan(this.cells.get(index - 1).get(j).getRowSpan() + 1);
                        cell.setMergeInfo(new Point(j, index - 1));
                    }
                }
                row.add(cell);
            }
            this.cells.add(index, row);
            int copyIndex = -1;
            if (index > 0) {
                copyIndex = index - 1;
            }
            if (copyIndex < 1) {
                this.rows.add(index, new RowInfo());
                continue;
            }
            this.rows.add(index, new RowInfo(this.rows.get(copyIndex)));
        }
        this.rowCount += count;
    }

    public void addRow(int count) {
        this.insertRows(this.getRowCount(), count);
    }

    public void insertRows(int index, int count) {
        this.insertRows(index, count, -1);
    }

    public void insertRows(int index, int count, int copyIndex) {
        Point mergeInfo;
        List<GridCellData> row;
        int i;
        if (index <= this.getHeaderRowCount()) {
            this.setHeaderRowCount(this.getHeaderRowCount() + count);
        }
        for (i = 0; i < this.rects.count(); ++i) {
            Grid2CellField rect = this.rects.get(i);
            if (rect.top >= index) {
                rect.top += count;
            }
            if (rect.bottom < index) continue;
            rect.bottom += count;
        }
        for (int j = index; j < this.getRowCount(); ++j) {
            row = this.cells.get(j);
            for (int i2 = 0; i2 < this.getColumnCount(); ++i2) {
                GridCellData cellData = row.get(i2);
                cellData.setRowIndex(j + count);
                mergeInfo = cellData.getMergeInfo();
                if (mergeInfo == null || mergeInfo.y < index) continue;
                cellData.setMergeInfo(new Point(mergeInfo.x, mergeInfo.y + count));
            }
        }
        for (i = 0; i < count; ++i) {
            row = new ArrayList<GridCellData>();
            for (int j = 0; j < this.getColumnCount(); ++j) {
                GridCellData cell = copyIndex < 0 ? new GridCellData(j, index + count - i - 1) : new GridCellData(this.cells.get(copyIndex).get(j), j, index + count - i - 1);
                if (index < this.getRowCount() && index > 0 && index < this.getRowCount()) {
                    if (this.mergedBySameCell(this.cells.get(index - 1).get(j), this.cells.get(index).get(j))) {
                        mergeInfo = this.cells.get(index - 1).get(j).getMergeInfo();
                        cell.setMerged(true);
                        cell.setMergeInfo(mergeInfo);
                        if (mergeInfo.x == j) {
                            this.cells.get(mergeInfo.y).get(j).setRowSpan(this.cells.get(mergeInfo.y).get(j).getRowSpan() + 1);
                        }
                    } else if (this.mergedByCell(this.cells.get(index - 1).get(j), this.cells.get(index).get(j))) {
                        cell.setMerged(true);
                        this.cells.get(index - 1).get(j).setRowSpan(this.cells.get(index - 1).get(j).getRowSpan() + 1);
                        cell.setMergeInfo(new Point(j, index - 1));
                    }
                }
                row.add(cell);
            }
            this.cells.add(index, row);
            if (copyIndex < 0) {
                this.rows.add(index, new RowInfo());
                continue;
            }
            this.rows.add(index, new RowInfo(this.rows.get(copyIndex)));
        }
        this.rowCount += count;
    }

    public void addRows(int index, int count, int copyIndex) {
        Point mergeInfo;
        List<GridCellData> row;
        if (index <= this.getHeaderRowCount()) {
            this.setHeaderRowCount(this.getHeaderRowCount() + count);
        }
        for (int j = index; j < this.getRowCount(); ++j) {
            row = this.cells.get(j);
            for (int i = 0; i < this.getColumnCount(); ++i) {
                GridCellData cellData = row.get(i);
                cellData.setRowIndex(j + count);
                mergeInfo = cellData.getMergeInfo();
                if (mergeInfo == null || mergeInfo.y < index) continue;
                cellData.setMergeInfo(new Point(mergeInfo.x, mergeInfo.y + count));
            }
        }
        for (int i = 0; i < count; ++i) {
            row = new ArrayList<GridCellData>();
            for (int j = 0; j < this.getColumnCount(); ++j) {
                GridCellData cell = copyIndex < 0 ? new GridCellData(j, index + count - i - 1) : new GridCellData(this.cells.get(copyIndex).get(j), j, index + count - i - 1);
                if (index < this.getRowCount() && index > 0 && index < this.getRowCount()) {
                    if (this.mergedBySameCell(this.cells.get(index - 1).get(j), this.cells.get(index).get(j))) {
                        mergeInfo = this.cells.get(index - 1).get(j).getMergeInfo();
                        cell.setMerged(true);
                        cell.setMergeInfo(mergeInfo);
                        if (mergeInfo.x == j) {
                            this.cells.get(mergeInfo.y).get(j).setRowSpan(this.cells.get(mergeInfo.y).get(j).getRowSpan() + 1);
                        }
                    } else if (this.mergedByCell(this.cells.get(index - 1).get(j), this.cells.get(index).get(j))) {
                        cell.setMerged(true);
                        this.cells.get(index - 1).get(j).setRowSpan(this.cells.get(index - 1).get(j).getRowSpan() + 1);
                        cell.setMergeInfo(new Point(j, index - 1));
                    }
                }
                row.add(cell);
            }
            this.cells.add(index, row);
            if (copyIndex < 0) {
                this.rows.add(index, new RowInfo());
                continue;
            }
            this.rows.add(index, new RowInfo(this.rows.get(copyIndex)));
        }
        this.rowCount += count;
        ArrayList<Grid2CellField> tempRects = new ArrayList<Grid2CellField>();
        for (int i = 0; i < this.rects.count(); ++i) {
            Grid2CellField rect = this.rects.get(i);
            for (int y = 0; y < count && rect.top >= copyIndex && rect.bottom >= copyIndex; ++y) {
                tempRects.add(new Grid2CellField(rect.left, rect.top + y + 1, rect.right, rect.bottom + y + 1));
            }
        }
        for (Grid2CellField grid2CellField : tempRects) {
            this.rects.addMergeRect(grid2CellField);
        }
    }

    public void deleteRows(int index, int count) {
        int i;
        if (index < this.getHeaderRowCount()) {
            this.setHeaderRowCount(this.getHeaderRowCount() - count);
        }
        for (i = this.rects.count() - 1; i >= 0; --i) {
            Grid2CellField rect = this.rects.get(i);
            if (rect.top > index) {
                if (rect.top > index + count - 1) {
                    rect.top -= count;
                    rect.bottom -= count;
                    continue;
                }
                if (rect.bottom > index + count - 1) {
                    if (rect.top > index) {
                        rect.top = index + count;
                        continue;
                    }
                    rect.bottom -= count;
                    continue;
                }
                this.rects.remove(i);
                continue;
            }
            if (rect.bottom > index + count - 1) {
                rect.bottom -= count;
                continue;
            }
            if (rect.bottom < index) continue;
            rect.bottom = index - 1;
        }
        for (int j = index; j < this.getRowCount(); ++j) {
            List<GridCellData> row = this.cells.get(j);
            for (int i2 = 0; i2 < this.getColumnCount(); ++i2) {
                row.get(i2).setRowIndex(j - count);
            }
        }
        for (i = 0; i < count; ++i) {
            this.cells.remove(index);
            this.rows.remove(index);
        }
        this.rowCount -= count;
    }

    private boolean mergedBySameCell(GridCellData gridCellData1, GridCellData gridCellData2) {
        Point p1 = gridCellData1.getMergeInfo();
        Point p2 = gridCellData2.getMergeInfo();
        return p1 != null && p2 != null && p1.x == p2.x && p1.y == p2.y;
    }

    private boolean mergedByCell(GridCellData gridCellData1, GridCellData gridCellData2) {
        Point p1 = gridCellData1.getMergeInfo();
        Point p2 = gridCellData2.getMergeInfo();
        return p2 != null && p2.x == gridCellData1.getColIndex() && p2.y == gridCellData1.getRowIndex();
    }

    public boolean mergeCells(int left, int top, int right, int bottom) {
        if (left < 0 || top < 0 || right >= this.colCount || bottom >= this.rowCount) {
            return false;
        }
        GridCellData merged = this.cells.get(top).get(left);
        if (merged.isMerged() && merged.getMergeInfo() != null) {
            return true;
        }
        if (merged.getRowSpan() > 1 || merged.getColSpan() > 1) {
            this.unmergeCell(left, top);
        }
        merged.setRowSpan(bottom - top + 1);
        merged.setColSpan(right - left + 1);
        for (int j = top; j <= bottom; ++j) {
            List<GridCellData> row = this.cells.get(j);
            for (int i = left; i <= right; ++i) {
                if (!(row.get(i).getColSpan() <= 1 && row.get(i).getRowSpan() <= 1 || i == left && j == top)) {
                    this.unmergeCell(i, j);
                }
                row.get(i).setMerged(true);
                row.get(i).setMergeInfo(new Point(left, top));
            }
        }
        this.cells.get(top).get(left).setMerged(true);
        this.cells.get(top).get(left).setMergeInfo(new Point(left, top));
        this.rects.addMergeRect(new Grid2CellField(left, top, right, bottom));
        return true;
    }

    public Grid2FieldList merges() {
        return this.rects;
    }

    public boolean unmergeCell(int col, int row) {
        int i;
        GridCellData merged = this.cells.get(row).get(col);
        if (merged.getMergeInfo() != null) {
            merged = this.cells.get(merged.getMergeInfo().y).get(merged.getMergeInfo().x);
        }
        int columnsCount = merged.getColSpan();
        int rowsCount = merged.getRowSpan();
        if (columnsCount + col > this.getColumnCount()) {
            columnsCount = this.getColumnCount() - col;
        }
        if (rowsCount + row > this.getRowCount()) {
            rowsCount = this.getRowCount() - row;
        }
        merged.setRowSpan(1);
        merged.setColSpan(1);
        int mergedRow = merged.getRowIndex();
        int mergedCol = merged.getColIndex();
        for (i = 0; i < columnsCount; ++i) {
            for (int j = 0; j < rowsCount; ++j) {
                this.cells.get(mergedRow + j).get(mergedCol + i).setMerged(false);
                this.cells.get(mergedRow + j).get(mergedCol + i).setMergeInfo(null);
            }
        }
        for (i = this.rects.count() - 1; i >= 0; --i) {
            Grid2CellField cell = this.rects.get(i);
            if (cell.left != mergedCol || cell.top != mergedRow) continue;
            this.rects.remove(i);
            return true;
        }
        return true;
    }

    public void setRowAutoHeight(int row, boolean value) {
        this.rows.get(row).setRowAutoHeight(value);
    }

    public void setColumnAutoWidth(int column, boolean value) {
        this.columns.get(column).setColAutoWidth(value);
    }

    public void setRowHidden(int row, boolean value) {
        this.rows.get(row).setRowHidden(value);
    }

    public void setColumnHidden(int col, boolean value) {
        this.columns.get(col).setColumnHidden(value);
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getColumnCount() {
        return this.colCount;
    }

    public int getHeaderRowCount() {
        return this.headRowCount;
    }

    public int getHeaderColumnCount() {
        return this.headColumnCount;
    }

    public int getFooterRowCount() {
        return this.footRowCount;
    }

    public int getFooterColumnCount() {
        return this.footColumnCount;
    }

    public int getRowHeight(int row) {
        return this.rows.get(row).getRowHeight();
    }

    public int getColumnWidth(int column) {
        try {
            return this.columns.get(column).getColWidth();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public boolean isRowHidden(int row) {
        try {
            return this.rows.get(row).isRowHidden();
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean isColumnHidden(int column) {
        if (column >= 0 && column < this.columns.size()) {
            return this.columns.get(column).isColumnHidden();
        }
        return false;
    }

    public boolean isRowAutoHeight(int row) {
        if (row >= 0 && row < this.rows.size()) {
            return this.rows.get(row).isRowAutoHeight();
        }
        return false;
    }

    public boolean isColumnAutoWidth(int column) {
        if (column < this.columns.size()) {
            return this.columns.get(column).isColAutoWidth();
        }
        return false;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public boolean copyFrom(Grid2Data srcData, int fromLeft, int fromTop, int fromRight, int fromBottom, int toLeft, int toTop) {
        if (!this.checkColumn(toLeft)) {
            return false;
        }
        if (!this.checkColumn(toLeft + fromRight - fromLeft)) {
            return false;
        }
        if (!this.checkRow(toTop)) {
            return false;
        }
        if (!this.checkRow(toTop + fromBottom - fromTop)) {
            return false;
        }
        if (!srcData.checkColumn(fromLeft)) {
            return false;
        }
        if (!srcData.checkColumn(fromRight)) {
            return false;
        }
        if (!srcData.checkRow(fromTop)) {
            return false;
        }
        if (!srcData.checkRow(fromBottom)) {
            return false;
        }
        for (int i = 0; i <= fromRight - fromLeft; ++i) {
            for (int j = 0; j <= fromBottom - fromTop; ++j) {
                GridCellData cellData2copy = srcData.getGridCellData(fromLeft + i, fromTop + j);
                this.getGridCellData(toLeft + i, toTop + j).copyCellData(cellData2copy);
                if (cellData2copy.getRowSpan() <= 1 && cellData2copy.getColSpan() <= 1) continue;
                this.mergeCells(toLeft + i, toTop + j, toLeft + i + cellData2copy.getColSpan() - 1, toTop + j + cellData2copy.getRowSpan() - 1);
            }
        }
        return true;
    }

    private void initRowList(List<RowInfo> list, int count) {
        for (int i = 0; i < count; ++i) {
            list.add(new RowInfo());
        }
    }

    private void initColumnList(List<ColumnInfo> list, int count) {
        for (int i = 0; i < count; ++i) {
            list.add(new ColumnInfo());
        }
    }

    private void initCells(List<List<GridCellData>> list, int column, int row) {
        GridCellData copyFrom = new GridCellData(-1, -1);
        for (int i = 0; i < row; ++i) {
            ArrayList<GridCellData> rows = new ArrayList<GridCellData>(column);
            for (int j = 0; j < column; ++j) {
                rows.add(new GridCellData(copyFrom, j, i));
            }
            list.add(rows);
        }
    }

    private boolean checkColumn(int col) {
        return col >= 0 && col < this.getColumnCount();
    }

    private boolean checkRow(int row) {
        return row >= 0 && row < this.getRowCount();
    }

    public static byte[] gridToBytes(Grid2Data data) {
        if (data == null) {
            return null;
        }
        MemStream2 store = new MemStream2();
        try {
            data.saveToStream(store);
            return store.getBytes();
        }
        catch (StreamException2 ex) {
            LogUtil.log(ex);
            return null;
        }
    }

    public void saveToStream(Stream2 stream) throws StreamException2 {
        if (this.rowCount > 0 && this.colCount > 0) {
            GridCellData cellData;
            int j;
            List<GridCellData> rowData;
            int i;
            stream.writeInt(this.rowCount);
            stream.writeInt(this.colCount);
            for (i = 0; i < this.rowCount; ++i) {
                stream.writeInt(this.rows.get(i).getRowBackgroundColor());
                stream.writeInt(this.rows.get(i).getRowHeight());
                stream.write((byte)(this.rows.get(i).isRowAutoHeight() ? 1 : 0));
                stream.write((byte)(this.rows.get(i).isRowHidden() ? 1 : 0));
            }
            for (i = 0; i < this.colCount; ++i) {
                stream.writeInt(this.columns.get(i).getColWidth());
                stream.write((byte)(this.columns.get(i).isColAutoWidth() ? 1 : 0));
                stream.write((byte)(this.columns.get(i).isColumnHidden() ? 1 : 0));
                stream.write((byte)(this.columns.get(i).getColumnGrab() ? 1 : 0));
            }
            for (i = 1; i < this.rowCount; ++i) {
                rowData = this.cells.get(i);
                for (j = 1; j < this.colCount; ++j) {
                    try {
                        cellData = rowData.get(j);
                        cellData.saveToStream(stream);
                        if (cellData.isShowTextOverFlow()) {
                            this.overFlowShowTextList.add(new CellStringListItem(cellData.getColIndex(), cellData.getRowIndex(), cellData.getShowText()));
                        }
                        if (cellData.isEditTextOverFlow()) {
                            this.overFlowEditTextList.add(new CellStringListItem(cellData.getColIndex(), cellData.getRowIndex(), cellData.getEditText()));
                        }
                        if (!cellData.isFitFontSize()) continue;
                        this.overFitFontSizeList.add(new CellStringListItem(cellData.getColIndex(), cellData.getRowIndex(), "T"));
                        continue;
                    }
                    catch (Exception e) {
                        LogUtil.log(e);
                    }
                }
            }
            stream.writeInt(this.headColumnCount);
            stream.writeInt(this.headRowCount);
            stream.writeInt(this.footColumnCount);
            stream.writeInt(this.footRowCount);
            stream.write((byte)(this.showSelectingBorder ? 1 : 0));
            for (i = 1; i < this.rowCount; ++i) {
                rowData = this.cells.get(i);
                for (j = 1; j < this.colCount; ++j) {
                    try {
                        cellData = rowData.get(j);
                        cellData.saveDataExToStream(stream);
                        if (!cellData.isDataExOverFlow()) continue;
                        this.overFlowDataExList.add(new CellStringListItem(cellData.getColIndex(), cellData.getRowIndex(), cellData.getDataExString()));
                        continue;
                    }
                    catch (Exception e) {
                        LogUtil.log(e);
                    }
                }
            }
            for (i = 0; i < this.rowCount; ++i) {
                rowData = this.cells.get(i);
                try {
                    cellData = rowData.get(0);
                    this.overFlowDataExList.add(new CellStringListItem(cellData.getColIndex(), cellData.getRowIndex(), cellData.getDataExString()));
                    continue;
                }
                catch (Exception e) {
                    LogUtil.log(e);
                }
            }
            for (i = 1; i < this.colCount; ++i) {
                rowData = this.cells.get(0);
                try {
                    cellData = rowData.get(i);
                    this.overFlowDataExList.add(new CellStringListItem(cellData.getColIndex(), cellData.getRowIndex(), cellData.getDataExString()));
                    continue;
                }
                catch (Exception e) {
                    LogUtil.log(e);
                }
            }
            BIFF2 b = new BIFF2();
            if (this.overFlowShowTextList.hasValue()) {
                b.ident = 0;
                this.overFlowShowTextList.saveToStream(b.data());
                BIFF2.writeBIFF(0, b, stream);
            }
            if (this.overFlowEditTextList.hasValue()) {
                b.reset();
                b.ident = 1;
                this.overFlowEditTextList.saveToStream(b.data());
                BIFF2.writeBIFF(0, b, stream);
            }
            if (this.overFlowDataExList.hasValue()) {
                b.reset();
                b.ident = (short)2;
                this.overFlowDataExList.saveToStream(b.data());
                BIFF2.writeBIFF(0, b, stream);
            }
            if (this.overFitFontSizeList.hasValue()) {
                b.reset();
                b.ident = (short)3;
                this.overFitFontSizeList.saveToStream(b.data());
                BIFF2.writeBIFF(0, b, stream);
            }
            if (null != this.script) {
                b.reset();
                b.ident = (short)4;
                byte[] encodeString = b.data().encodeString(this.script);
                b.data().writeInt(encodeString.length);
                b.data().write(encodeString, 0, encodeString.length);
                BIFF2.writeBIFF(0, b, stream);
            }
            this.clearList();
            b.reset();
            b.ident = (short)127;
            BIFF2.writeBIFF(0, b, stream);
        }
    }

    public static Grid2Data bytesToGrid(byte[] data) {
        if (data == null) {
            return null;
        }
        Grid2Data gd = new Grid2Data();
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            gd.loadFromStream(s);
        }
        catch (Exception ex) {
            LogUtil.log(ex);
            gd = null;
        }
        return gd;
    }

    public void loadFromStream(Stream2 stream) throws StreamException2 {
        try {
            int j;
            List<GridCellData> rowData;
            int i;
            int i2;
            this.setRowCount(stream.readInt());
            this.setColumnCount(stream.readInt());
            for (i2 = 0; i2 < this.getRowCount(); ++i2) {
                this.rows.get(i2).setRowBackgroundColor(stream.readInt());
                this.rows.get(i2).setRowHeight(stream.readInt());
                this.rows.get(i2).setRowAutoHeight(Grid2Data.readBool(stream));
                this.rows.get(i2).setRowHidden(Grid2Data.readBool(stream));
            }
            for (i2 = 0; i2 < this.getColumnCount(); ++i2) {
                this.columns.get(i2).setColWidth(stream.readInt());
                this.columns.get(i2).setColAutoWidth(Grid2Data.readBool(stream));
                this.columns.get(i2).setColumnHidden(Grid2Data.readBool(stream));
                this.columns.get(i2).setColumnGrab(Grid2Data.readBool(stream));
            }
            for (i = 1; i < this.rowCount; ++i) {
                rowData = this.cells.get(i);
                for (j = 1; j < this.colCount; ++j) {
                    GridCellData cellData = rowData.get(j);
                    cellData.loadFromStream(stream);
                    if (cellData.getRowSpan() <= 1 && cellData.getColSpan() <= 1) continue;
                    this.rects.addMergeRect(new Grid2CellField(cellData.getColIndex(), cellData.getRowIndex(), cellData.getColIndex() + cellData.getColSpan() - 1, cellData.getRowIndex() + cellData.getRowSpan() - 1));
                    for (int j2 = 0; j2 < cellData.getRowSpan(); ++j2) {
                        for (int k = 0; k < cellData.getColSpan(); ++k) {
                            if (j2 == 0 && k == 0) continue;
                            this.getGridCellData(cellData.getColIndex() + k, cellData.getRowIndex() + j2).setMergeInfo(new Point(cellData.getColIndex(), cellData.getRowIndex()));
                        }
                    }
                }
            }
            this.setHeaderColumnCount(stream.readInt());
            this.setHeaderRowCount(stream.readInt());
            this.setFooterColumnCount(stream.readInt());
            this.setFooterRowCount(stream.readInt());
            this.setShowSelectingBorder(Grid2Data.readBool(stream));
            for (i = 1; i < this.rowCount; ++i) {
                rowData = this.cells.get(i);
                for (j = 1; j < this.colCount; ++j) {
                    GridCellData cellData = rowData.get(j);
                    cellData.loadDataExFromStream(stream);
                }
            }
            if (stream.getPosition() < stream.getSize()) {
                BIFF2 b = new BIFF2();
                while (b.ident != 127) {
                    BIFF2.readBIFF(0, b, stream);
                    switch (b.ident) {
                        case 0: {
                            this.overFlowShowTextList.loadFromStream(b.data());
                            break;
                        }
                        case 1: {
                            this.overFlowEditTextList.loadFromStream(b.data());
                            break;
                        }
                        case 2: {
                            this.overFlowDataExList.loadFromStream(b.data());
                            break;
                        }
                        case 3: {
                            this.overFitFontSizeList.loadFromStream(b.data());
                            break;
                        }
                        case 4: {
                            String readStringBySize;
                            int length = b.data().readInt();
                            this.script = readStringBySize = b.data().readString(length);
                        }
                    }
                }
                if (this.overFlowShowTextList.hasValue()) {
                    for (CellStringListItem item : this.overFlowShowTextList.getStringListItemList()) {
                        this.getGridCellData(item.colIndex, item.rowIndex).setShowText(item.string);
                    }
                }
                if (this.overFlowEditTextList.hasValue()) {
                    for (CellStringListItem item : this.overFlowEditTextList.getStringListItemList()) {
                        this.getGridCellData(item.colIndex, item.rowIndex).setEditText(item.string);
                    }
                }
                if (this.overFitFontSizeList.hasValue()) {
                    for (CellStringListItem item : this.overFitFontSizeList.getStringListItemList()) {
                        this.getGridCellData(item.colIndex, item.rowIndex).setFitFontSize(true);
                    }
                }
                if (this.overFlowDataExList.hasValue()) {
                    for (CellStringListItem item : this.overFlowDataExList.getStringListItemList()) {
                        this.getGridCellData(item.colIndex, item.rowIndex).setDataExFromString(item.string);
                    }
                }
                this.clearList();
            }
        }
        catch (Throwable t) {
            LogUtil.log(t);
        }
    }

    private void clearList() {
        this.overFlowShowTextList.clear();
        this.overFlowEditTextList.clear();
        this.overFitFontSizeList.clear();
        this.overFlowDataExList.clear();
    }

    public static boolean readBool(Stream2 stream) throws StreamException2 {
        return stream.read() == 1;
    }

    public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; ++i) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte)(value >>> offset & 0xFF);
        }
        return b;
    }

    public static final int byteArrayToInt(ByteArrayInputStream bais) {
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; ++i) {
            b[i] = (byte)bais.read();
        }
        return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
    }
}

