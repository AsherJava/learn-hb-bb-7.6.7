/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.NumberCellProperty
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.nr.summary.executor.query.grid;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.nr.summary.executor.query.grid.PatternParser;

public class GridHelper {
    private final GridData gridData;
    private static final String CELL_FONT_NAME = "\u5fae\u8f6f\u96c5\u9ed1";

    public GridHelper(GridData gridData) {
        this.gridData = gridData;
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public GridCell getCellEx(Position pos) {
        return this.getCellEx(pos.col(), pos.row());
    }

    public GridCell getCellEx(int col, int row) {
        return this.gridData.getCellEx(col, row);
    }

    public int getColCount() {
        int colCount = this.gridData.getColCount();
        return colCount;
    }

    public void setCell(GridCell gridCell) {
        this.gridData.setCell(gridCell);
    }

    public Position newPosition(int col, int row) {
        int rowCount;
        int colCount = this.gridData.getColCount();
        if (col > colCount) {
            while (col > colCount) {
                colCount += 50;
            }
            this.gridData.setColCount(colCount);
        }
        if (row > (rowCount = this.gridData.getRowCount()) - 1) {
            while (row > rowCount - 1) {
                rowCount += 50;
            }
            this.gridData.setRowCount(rowCount);
        }
        return new Position(col, row);
    }

    public void setValueStyle(GridCell valueCell, int valType, PatternParser parser) {
        if (valType == 6) {
            this.setValueStyle(valueCell, valType);
        } else if (DataTypes.isNumber((int)valType)) {
            if (parser.isThousandsPercent()) {
                valueCell.setDataType(1);
                valueCell.setFontName(CELL_FONT_NAME);
                this.setCellStyle(valueCell, 2);
            } else {
                this.setNumberCellProperty(valueCell, parser);
                this.setValueStyle(valueCell, valType);
            }
        } else {
            this.setValueStyle(valueCell, valType);
        }
    }

    public void setValueStyle(GridCell valueCell, int valType) {
        valueCell.setDataType(0);
        valueCell.setFontName(CELL_FONT_NAME);
        if (valType == 6) {
            valueCell.setDataType(1);
            this.setCellStyle(valueCell, 1);
        } else if (DataTypes.isNumber((int)valType)) {
            valueCell.setDataType(2);
            this.setCellStyle(valueCell, 2);
        } else {
            this.setCellStyle(valueCell, 2);
        }
    }

    public void setCellStyle(GridCell cell, int horizonAlign) {
        cell.setHorzAlign(horizonAlign);
        cell.setVertAlign(3);
    }

    public void setNumberCellProperty(GridCell gridCell, PatternParser parser) {
        if (parser == null) {
            return;
        }
        gridCell.setDataType(2);
        NumberCellProperty numberCellProperty = new NumberCellProperty(gridCell);
        numberCellProperty.setDecimal(parser.getDecimal());
        numberCellProperty.setThoundsMarks(parser.isThousandsMark());
        numberCellProperty.setIsPercent(parser.isPercent());
    }
}

