/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.bi.quickreport.engine.util;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridData;

public class GridDataHandler {
    private final GridData gridData;

    public GridDataHandler(GridData gridData) {
        this.gridData = gridData;
    }

    public void deleteHiddenColRow() {
        this.deleteHiddenCol();
        this.deleteHiddenRow();
    }

    public void deleteHiddenCol() {
        for (int col = this.gridData.getColCount() - 1; col > 0; --col) {
            if (this.gridData.getColVisible(col)) continue;
            this.copyCellData2NextCol(col);
            this.gridData.deleteCol(col, 1);
        }
    }

    public void deleteHiddenRow() {
        for (int row = this.gridData.getRowCount() - 1; row > 0; --row) {
            if (this.gridData.getRowVisible(row)) continue;
            this.copyCellData2NextRow(row);
            this.gridData.deleteRow(row, 1);
        }
    }

    private void copyCellData2NextCol(int col) {
        if (col + 1 >= this.gridData.getColCount()) {
            return;
        }
        for (int row = 1; row < this.gridData.getRowCount(); ++row) {
            CellField cf = this.gridData.merges().getMergeRect(col + 1, row);
            if (cf == null || cf.top != row || cf.left < col || cf.left >= col + 1) continue;
            String cellData = this.gridData.getCellData(cf.left, cf.top);
            this.gridData.setCellData(col + 1, row, cellData);
        }
    }

    private void copyCellData2NextRow(int row) {
        if (row + 1 >= this.gridData.getRowCount()) {
            return;
        }
        for (int col = 1; col < this.gridData.getColCount(); ++col) {
            CellField cf = this.gridData.merges().getMergeRect(col, row + 1);
            if (cf == null || cf.left != col || cf.top < row || cf.top >= row + 1) continue;
            String cellData = this.gridData.getCellData(cf.left, cf.top);
            this.gridData.setCellData(col, row + 1, cellData);
        }
    }
}

