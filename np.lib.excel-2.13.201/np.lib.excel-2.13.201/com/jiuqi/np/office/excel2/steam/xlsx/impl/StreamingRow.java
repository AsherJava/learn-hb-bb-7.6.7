/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.impl;

import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.NotSupportedException;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingCell;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class StreamingRow
implements Row {
    private int rowIndex;
    private boolean isHidden;
    private int outlineLevel;
    private TreeMap<Integer, Cell> cellMap = new TreeMap();

    public StreamingRow(int rowIndex, boolean isHidden) {
        this.rowIndex = rowIndex;
        this.isHidden = isHidden;
    }

    public StreamingRow(int rowIndex, boolean isHidden, int outlineLevel) {
        this.rowIndex = rowIndex;
        this.isHidden = isHidden;
        this.outlineLevel = outlineLevel;
    }

    public Map<Integer, Cell> getCellMap() {
        return this.cellMap;
    }

    public void setCellMap(TreeMap<Integer, Cell> cellMap) {
        this.cellMap = cellMap;
    }

    @Override
    public int getRowNum() {
        return this.rowIndex;
    }

    @Override
    public Iterator<Cell> cellIterator() {
        return this.cellMap.values().iterator();
    }

    @Override
    public Iterator<Cell> iterator() {
        return this.cellMap.values().iterator();
    }

    @Override
    public Cell getCell(int cellnum) {
        return this.cellMap.get(cellnum);
    }

    @Override
    public short getLastCellNum() {
        return (short)(this.cellMap.size() == 0 ? -1 : this.cellMap.lastEntry().getValue().getColumnIndex() + 1);
    }

    @Override
    public boolean getZeroHeight() {
        return this.isHidden;
    }

    @Override
    public int getPhysicalNumberOfCells() {
        return this.cellMap.size();
    }

    @Override
    public short getFirstCellNum() {
        if (this.cellMap.size() == 0) {
            return -1;
        }
        return this.cellMap.firstKey().shortValue();
    }

    @Override
    public Cell getCell(int cellnum, Row.MissingCellPolicy policy) {
        StreamingCell cell = (StreamingCell)this.cellMap.get(cellnum);
        if (policy == Row.MissingCellPolicy.CREATE_NULL_AS_BLANK) {
            if (cell == null) {
                return new StreamingCell(cellnum, this.rowIndex, false);
            }
        } else if (policy == Row.MissingCellPolicy.RETURN_BLANK_AS_NULL && (cell == null || cell.getCellType() == CellType.BLANK)) {
            return null;
        }
        return cell;
    }

    @Override
    public Cell createCell(int column) {
        throw new NotSupportedException();
    }

    @Override
    public void removeCell(Cell cell) {
        throw new NotSupportedException();
    }

    @Override
    public void setRowNum(int rowNum) {
        throw new NotSupportedException();
    }

    @Override
    public void setHeight(short height) {
        throw new NotSupportedException();
    }

    @Override
    public void setZeroHeight(boolean zHeight) {
        throw new NotSupportedException();
    }

    @Override
    public void setHeightInPoints(float height) {
        throw new NotSupportedException();
    }

    @Override
    public short getHeight() {
        throw new NotSupportedException();
    }

    @Override
    public float getHeightInPoints() {
        throw new NotSupportedException();
    }

    @Override
    public boolean isFormatted() {
        throw new NotSupportedException();
    }

    @Override
    public CellStyle getRowStyle() {
        throw new NotSupportedException();
    }

    @Override
    public void setRowStyle(CellStyle style) {
        throw new NotSupportedException();
    }

    @Override
    public Sheet getSheet() {
        throw new NotSupportedException();
    }

    public Cell createCell(int column, int type) {
        throw new NotSupportedException();
    }

    @Override
    public Cell createCell(int column, CellType type) {
        throw new NotSupportedException();
    }

    @Override
    public int getOutlineLevel() {
        return this.outlineLevel;
    }

    @Override
    public void shiftCellsRight(int firstShiftColumnIndex, int lastShiftColumnIndex, int step) {
        throw new NotSupportedException();
    }

    @Override
    public void shiftCellsLeft(int firstShiftColumnIndex, int lastShiftColumnIndex, int step) {
        throw new NotSupportedException();
    }
}

