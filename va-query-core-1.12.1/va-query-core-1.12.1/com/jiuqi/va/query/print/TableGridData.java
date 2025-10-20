/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print;

import com.jiuqi.va.query.print.TableCellData;
import com.jiuqi.va.query.print.TableRowTypeEnum;
import java.util.ArrayList;
import java.util.List;

public class TableGridData {
    private List<List<TableCellData>> cells = new ArrayList<List<TableCellData>>(100);

    public TableCellData getCell(int row, int col) {
        try {
            return this.cells.get(row).get(col);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<TableCellData> getRowData(int row) {
        try {
            return this.cells.get(row);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void addRow(int row, List<TableCellData> rowData) {
        try {
            this.cells.remove(row);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.cells.add(row, rowData);
    }

    public void addRow(List<TableCellData> rowData) {
        this.cells.add(rowData);
    }

    public void addCell(int row, int col, TableCellData cellData) {
        int colSize;
        List<TableCellData> currentRowTableCellList = null;
        int rowSize = this.getRowSize() - 1;
        if (rowSize < row) {
            for (int i = 0; i < row - rowSize; ++i) {
                currentRowTableCellList = new ArrayList<TableCellData>();
                this.cells.add(currentRowTableCellList);
            }
            if (null == currentRowTableCellList) {
                currentRowTableCellList = this.getRowData(row);
            }
        } else {
            currentRowTableCellList = this.getRowData(row);
        }
        if ((colSize = currentRowTableCellList.size() - 1) < col) {
            for (int i = colSize; i < col; ++i) {
                TableCellData tableCellData = new TableCellData();
                currentRowTableCellList.add(tableCellData);
            }
            currentRowTableCellList.set(col, cellData);
        } else {
            currentRowTableCellList.set(col, cellData);
        }
    }

    public TableRowTypeEnum getRowType(int row) {
        List<TableCellData> tableCellData = this.cells.get(row);
        for (int i = 0; i < tableCellData.size(); ++i) {
            TableCellData cellData = tableCellData.get(i);
            if (cellData == null) continue;
            return cellData.getCellProp().getRowType();
        }
        return null;
    }

    public int getRowSize() {
        return this.cells.size();
    }

    public int getSpanColumn() {
        return 0;
    }

    public int getSpanRow() {
        return 0;
    }

    public int getRowIndex() {
        return 0;
    }

    public int getColumnIndex() {
        return 0;
    }

    public List<List<TableCellData>> getCells() {
        return this.cells;
    }

    public void setCells(List<List<TableCellData>> cells) {
        this.cells = cells;
    }
}

