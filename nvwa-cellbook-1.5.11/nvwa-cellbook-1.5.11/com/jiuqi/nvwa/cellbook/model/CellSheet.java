/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheetGroup;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import com.jiuqi.nvwa.cellbook.model.Col;
import com.jiuqi.nvwa.cellbook.model.Options;
import com.jiuqi.nvwa.cellbook.model.Point;
import com.jiuqi.nvwa.cellbook.model.Row;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CellSheet
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String name;
    private String groupCode;
    private Options options = new Options();
    private List<Row> rows = new ArrayList<Row>();
    private List<Col> columns = new ArrayList<Col>();
    private List<List<Cell>> data = new ArrayList<List<Cell>>();
    private List<CellMerge> merges = new ArrayList<CellMerge>();
    private CellBook cellBook;

    protected CellSheet(String title, String name, int rowNum, int colNum, String groupCode, CellBook cellBook) {
        int rowIndex;
        this.title = title;
        this.name = name;
        this.groupCode = groupCode;
        this.cellBook = cellBook;
        for (rowIndex = 0; rowIndex < rowNum; ++rowIndex) {
            this.rows.add(new Row());
        }
        for (int colIndex = 0; colIndex < colNum; ++colIndex) {
            this.columns.add(new Col());
        }
        for (rowIndex = 0; rowIndex < rowNum; ++rowIndex) {
            ArrayList<Cell> rowCell = new ArrayList<Cell>();
            for (int colIndex = 0; colIndex < colNum; ++colIndex) {
                rowCell.add(new Cell(rowIndex, colIndex, this));
            }
            this.data.add(rowCell);
        }
    }

    public Cell getCell(int rowIndex, int colIndex) {
        return this.data.get(rowIndex).get(colIndex);
    }

    protected void setCell(int rowIndex, int colIndex, Cell cell) {
        cell.setRowIndex(rowIndex);
        cell.setColIndex(colIndex);
        List<Cell> list = this.data.get(rowIndex);
        list.remove(colIndex);
        list.add(colIndex, cell);
    }

    public boolean insertRows(int rowIndex, int count) {
        return this.insertRows(rowIndex, count, -1);
    }

    public boolean insertRows(int rowIndex, int count, int copyIndex) {
        int rowCount = this.rows.size();
        int colCount = this.columns.size();
        if (rowIndex < 0 || count <= 0 || rowIndex >= rowCount || copyIndex >= rowCount) {
            return false;
        }
        if (null != this.options.getHeader() && this.options.getHeader().getRowHeader() > -1 && rowIndex <= this.options.getHeader().getRowHeader()) {
            this.options.getHeader().setRowHeader(this.options.getHeader().getRowHeader() + count);
        }
        for (CellMerge cellMerge : this.merges) {
            if (cellMerge.getRowSpan() > 1 && cellMerge.getRowIndex() < rowIndex && rowIndex < cellMerge.getRowIndex() + cellMerge.getRowSpan()) {
                cellMerge.setRowSpan(cellMerge.getRowSpan() + count);
                continue;
            }
            if (cellMerge.getRowIndex() <= rowIndex) continue;
            cellMerge.setRowIndex(cellMerge.getRowIndex() + count);
        }
        for (int i = rowIndex; i < rowCount; ++i) {
            List<Cell> list = this.data.get(i);
            for (int c = 0; c < colCount; ++c) {
                Cell cell = list.get(c);
                cell.setRowIndex(cell.getRowIndex() + count);
                if (!cell.isMerged() || cell.getMergeInfo().getX() <= rowIndex) continue;
                Point mergeInfo = cell.getMergeInfo();
                mergeInfo.setX(mergeInfo.getX() + count);
                cell.setMergeInfo(mergeInfo);
            }
        }
        for (int x = 0; x < count; ++x) {
            Row copyRow = null;
            ArrayList<Cell> copyRowCell = new ArrayList<Cell>();
            if (copyIndex >= 0) {
                copyRow = (Row)this.rows.get(copyIndex).clone();
                List<Cell> rowCell = this.data.get(copyIndex);
                for (Cell cell : rowCell) {
                    Cell newCell = new Cell(rowIndex + x, cell.getColIndex(), this);
                    CellStyle cellStyle = cell.getCellStyle();
                    CellStyle newCellStyle = newCell.getCellStyle();
                    if (!newCellStyle.equals(cellStyle)) {
                        newCell.setCellStyle((CellStyle)cellStyle.clone());
                    }
                    this.cellAddMergeRow(rowIndex, cell.getColIndex(), newCell);
                    copyRowCell.add(newCell);
                }
            } else {
                copyRow = new Row();
                for (int colIndex = 0; colIndex < colCount; ++colIndex) {
                    Cell newCell = new Cell(rowIndex + x, colIndex, this);
                    this.cellAddMergeRow(rowIndex, colIndex, newCell);
                    copyRowCell.add(newCell);
                }
            }
            this.rows.add(rowIndex, copyRow);
            this.data.add(rowIndex + x, copyRowCell);
        }
        return true;
    }

    public boolean insertCows(int colIndex, int count) {
        return this.insertCows(colIndex, count, -1);
    }

    public boolean insertCows(int colIndex, int count, int copyIndex) {
        List<Cell> list;
        int rowCount = this.rows.size();
        int colCount = this.columns.size();
        if (colIndex < 0 || count <= 0 || colIndex >= colCount || copyIndex >= colCount) {
            return false;
        }
        if (null != this.options.getHeader() && this.options.getHeader().getColumnHeader() > -1 && colIndex <= this.options.getHeader().getColumnHeader()) {
            this.options.getHeader().setColumnHeader(this.options.getHeader().getColumnHeader() + count);
        }
        for (CellMerge cellMerge : this.merges) {
            if (cellMerge.getColumnSpan() > 1 && cellMerge.getColumnIndex() < colIndex && colIndex < cellMerge.getColumnIndex() + cellMerge.getColumnSpan()) {
                cellMerge.setColumnSpan(cellMerge.getColumnSpan() + count);
                continue;
            }
            if (cellMerge.getColumnIndex() <= colIndex) continue;
            cellMerge.setColumnIndex(cellMerge.getColumnIndex() + count);
        }
        for (int i = 0; i < rowCount; ++i) {
            list = this.data.get(i);
            for (int c = colIndex; c < colCount; ++c) {
                Cell cell = list.get(c);
                cell.setColIndex(cell.getColIndex() + count);
                if (!cell.isMerged() || cell.getMergeInfo().getY() <= colIndex) continue;
                Point mergeInfo = cell.getMergeInfo();
                mergeInfo.setY(mergeInfo.getY() + count);
                cell.setMergeInfo(mergeInfo);
            }
        }
        for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
            list = this.data.get(rowIndex);
            for (int x = 0; x < count; ++x) {
                Cell newCell = new Cell(rowIndex, colIndex + x, this);
                if (copyIndex >= 0) {
                    CellStyle cellStyle;
                    Cell cell = list.get(copyIndex);
                    CellStyle newCellStyle = newCell.getCellStyle();
                    if (!newCellStyle.equals(cellStyle = cell.getCellStyle())) {
                        newCell.setCellStyle((CellStyle)cellStyle.clone());
                    }
                }
                this.cellAddMergeCol(rowIndex, colIndex, newCell);
                list.add(colIndex + x, newCell);
            }
        }
        for (int x = 0; x < count; ++x) {
            Col copyCol = null;
            copyCol = copyIndex >= 0 ? (Col)this.columns.get(copyIndex).clone() : new Col();
            this.columns.add(colIndex, copyCol);
        }
        return true;
    }

    private void cellAddMergeCol(int rowIndex, int colIndex, Cell newCell) {
        if (colIndex > 0) {
            Cell leftCell = this.data.get(rowIndex).get(colIndex - 1);
            Cell rightCell = this.data.get(rowIndex).get(colIndex);
            if (leftCell.isMerged() && rightCell.isMerged() && leftCell.getMergeInfo().equals(rightCell.getMergeInfo())) {
                newCell.setMerged(true);
                newCell.setMergeInfo((Point)leftCell.getMergeInfo().clone());
            }
        }
    }

    private void cellAddMergeRow(int rowIndex, int colIndex, Cell newCell) {
        Cell nextCell = this.data.get(rowIndex).get(colIndex);
        if (rowIndex > 0) {
            Cell lastCell = this.data.get(rowIndex - 1).get(colIndex);
            if (nextCell.isMerged() && lastCell.isMerged() && nextCell.getMergeInfo().equals(lastCell.getMergeInfo())) {
                newCell.setMerged(true);
                newCell.setMergeInfo((Point)nextCell.getMergeInfo().clone());
            }
        }
    }

    public boolean mergeCells(int rowIndex, int colIndex, int rowSpan, int columnSpan) {
        int y;
        int x;
        if (rowIndex < 0 || colIndex < 0 || rowSpan <= 1 && columnSpan <= 1) {
            return false;
        }
        if (rowIndex + rowSpan > this.rows.size() || colIndex + columnSpan > this.columns.size()) {
            return false;
        }
        int rowNum = rowIndex + rowSpan;
        int colNum = colIndex + columnSpan;
        for (x = rowIndex; x < rowNum; ++x) {
            for (y = colIndex; y < colNum; ++y) {
                Cell cell = this.data.get(x).get(y);
                if (!cell.isMerged()) continue;
                this.unMergeCell(cell);
            }
        }
        for (x = rowIndex; x < rowNum; ++x) {
            for (y = colIndex; y < colNum; ++y) {
                Point mergeInfo = new Point(rowIndex, colIndex);
                Cell cell = this.data.get(x).get(y);
                cell.setMerged(true);
                cell.setMergeInfo(mergeInfo);
            }
        }
        CellMerge cellMerge = new CellMerge(rowIndex, colIndex, rowSpan, columnSpan);
        this.merges.add(cellMerge);
        return true;
    }

    private boolean unMergeCell(Cell cell) {
        if (!cell.isMerged()) {
            return false;
        }
        Point mergeInfo = cell.getMergeInfo();
        Optional<CellMerge> findFirst = this.merges.stream().filter(merge -> merge.getRowIndex() == mergeInfo.getX() && merge.getColumnIndex() == mergeInfo.getY()).findFirst();
        CellMerge cellMerge = findFirst.get();
        int rowNum = cellMerge.getRowIndex() + cellMerge.getRowSpan();
        int colNum = cellMerge.getColumnIndex() + cellMerge.getColumnSpan();
        for (int rowIndex = cellMerge.getRowIndex(); rowIndex < rowNum; ++rowIndex) {
            for (int colIndex = cellMerge.getColumnIndex(); colIndex < colNum; ++colIndex) {
                Cell mergeCell = this.data.get(rowIndex).get(colIndex);
                mergeCell.setMerged(false);
                mergeCell.setMergeInfo(null);
            }
        }
        int indexOf = this.merges.indexOf(cellMerge);
        this.merges.remove(indexOf);
        return true;
    }

    public boolean copyRegion(int fromLeft, int fromTop, int fromRight, int fromBottom, int toLeft, int toTop) {
        return this.copyRegion(this, fromLeft, fromTop, fromRight, fromBottom, toLeft, toTop);
    }

    public boolean copyRegion(CellSheet srcData, int fromLeft, int fromTop, int fromRight, int fromBottom, int toLeft, int toTop) {
        if (!(this.checkColumn(toLeft) && this.checkColumn(toLeft + fromRight - fromLeft) && this.checkRow(toTop) && this.checkRow(toTop + fromBottom - fromTop))) {
            return false;
        }
        if (!(srcData.checkColumn(fromLeft) && srcData.checkColumn(fromRight) && srcData.checkRow(fromTop) && srcData.checkRow(fromBottom))) {
            return false;
        }
        for (int r = 0; r <= fromBottom - fromTop; ++r) {
            int copyRow = fromTop + r;
            int newCellRow = toTop + r;
            Row fromRow = srcData.getRow(copyRow);
            Row toRow = (Row)fromRow.clone();
            this.setRow(newCellRow, toRow);
            for (int c = 0; c <= fromRight - fromLeft; ++c) {
                CellStyle cellStyle;
                CellStyle newCellStyle;
                int copyCol = fromLeft + c;
                Cell copyCell = srcData.getCell(copyRow, copyCol);
                int newCellCol = toLeft + c;
                Cell cell = this.getCell(newCellRow, newCellCol);
                if (cell.isMerged()) {
                    this.unMergeCell(cell);
                }
                Cell newCell = (Cell)copyCell.clone();
                if (copyCell.isMerged()) {
                    Point point = new Point(copyCell.getMergeInfo().getX() + toTop - fromTop, copyCell.getMergeInfo().getY() + toLeft - fromLeft);
                    newCell.setMergeInfo(point);
                    if (newCell.getMergeInfo().getX() == newCellRow && newCell.getMergeInfo().getY() == newCellCol) {
                        CellMerge copyCellMerge = srcData.findMergeByPoint(copyCell.getMergeInfo());
                        CellMerge cellMerge = new CellMerge(newCellRow, newCellCol, copyCellMerge.getRowSpan(), copyCellMerge.getColumnSpan());
                        this.merges.add(cellMerge);
                    }
                }
                if (!(newCellStyle = newCell.getCellStyle()).equals(cellStyle = copyCell.getCellStyle())) {
                    newCell.setCellStyle((CellStyle)cellStyle.clone());
                }
                this.setCell(newCellRow, newCellCol, newCell);
            }
        }
        return true;
    }

    protected boolean checkColumn(int col) {
        return col >= 0 && col < this.columns.size();
    }

    protected boolean checkRow(int row) {
        return row >= 0 && row < this.rows.size();
    }

    public int getHeaderRowCount() {
        return this.options.getHeader().getRowHeader();
    }

    public int getHeaderColCount() {
        return this.options.getHeader().getColumnHeader();
    }

    public void setHeaderRowCount(int rowCount) {
        this.options.getHeader().setRowHeader(rowCount);
    }

    public void setHeaderColCount(int colCount) {
        this.options.getHeader().setColumnHeader(colCount);
    }

    public int getFooterRowCount() {
        return this.options.getFooter().getRowFooter();
    }

    public int getFooterColCount() {
        return this.options.getFooter().getColumnFooter();
    }

    public void setFooterRowCount(int rowCount) {
        this.options.getFooter().setRowFooter(rowCount);
    }

    public void setFooterColCount(int colCount) {
        this.options.getFooter().setColumnFooter(colCount);
    }

    public void setRowHeight(int rowIndex, int height) {
        this.getRows().get(rowIndex).setSize(height);
    }

    public int getRowHeight(int rowIndex) {
        return this.getRows().get(rowIndex).getSize();
    }

    public boolean getRowAutoHeight(int rowIndex) {
        return this.getRows().get(rowIndex).isAuto();
    }

    public void setRowAutoHeight(int rowIndex, boolean auto) {
        this.getRows().get(rowIndex).setAuto(auto);
    }

    public boolean getRowHidden(int rowIndex) {
        return this.getRows().get(rowIndex).isHidden();
    }

    public void setRowHidden(int rowIndex, boolean hidden) {
        this.getRows().get(rowIndex).setHidden(hidden);
    }

    public void setColWide(int colIndex, int wide) {
        this.columns.get(colIndex).setSize(wide);
    }

    public int getColWide(int colIndex) {
        return this.columns.get(colIndex).getSize();
    }

    public boolean getColAutoWide(int colIndex) {
        return this.columns.get(colIndex).isAuto();
    }

    public void setColAutoWide(int colIndex, boolean auto) {
        this.columns.get(colIndex).setAuto(auto);
    }

    public boolean getColHidden(int colIndex) {
        return this.columns.get(colIndex).isHidden();
    }

    public void setColHidden(int colIndex, boolean hidden) {
        this.columns.get(colIndex).setHidden(hidden);
    }

    public void setCol(int colIndex, Col col) {
        this.columns.remove(colIndex);
        this.columns.set(colIndex, col);
    }

    public void setRow(int rowIndex, Row row) {
        this.rows.remove(rowIndex);
        this.rows.set(rowIndex, row);
    }

    public int getRowCount() {
        return this.rows.size();
    }

    public int getColumnCount() {
        return this.columns.size();
    }

    public Row getRow(int row) {
        return this.rows.get(row);
    }

    public Col getCol(int col) {
        return this.columns.get(col);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Options getOptions() {
        return this.options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public List<Row> getRows() {
        return this.rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Col> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Col> columns) {
        this.columns = columns;
    }

    public List<List<Cell>> getData() {
        return this.data;
    }

    public void setData(List<List<Cell>> data) {
        this.data = data;
    }

    public List<CellMerge> getMerges() {
        return this.merges;
    }

    public CellMerge findMergeByPoint(Point mergeInfo) {
        Optional<CellMerge> findFirst = this.merges.stream().filter(merge -> merge.getRowIndex() == mergeInfo.getX() && merge.getColumnIndex() == mergeInfo.getY()).findFirst();
        if (findFirst.isPresent()) {
            CellMerge cellMerge = findFirst.get();
            return cellMerge;
        }
        return null;
    }

    public void setMerges(List<CellMerge> merges) {
        this.merges = merges;
    }

    public CellSheetGroup getCellSheetGroup() {
        List<CellSheetGroup> groups;
        Optional<CellSheetGroup> findGroup;
        if (!StringUtils.isEmpty(this.groupCode) && (findGroup = (groups = this.cellBook.getGroups()).stream().filter(group -> group.getName().equals(this.groupCode)).findFirst()).isPresent()) {
            return findGroup.get();
        }
        return null;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}

