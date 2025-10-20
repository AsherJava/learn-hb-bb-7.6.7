/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.print.PrintSetting
 */
package com.jiuqi.bi.quickreport.engine.build.print;

import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PrintSettingAdjustor {
    private ResultGridData resultGrid;
    private PrintSetting printSetting;

    public PrintSettingAdjustor(ResultGridData resultGrid) {
        this.resultGrid = resultGrid;
    }

    public PrintSetting adjust(WorksheetModel sheetModel) throws ReportBuildException {
        if (sheetModel.getPrintSetting() == null) {
            return null;
        }
        this.printSetting = (PrintSetting)sheetModel.getPrintSetting().clone();
        if (sheetModel.getGriddata().getColCount() != this.resultGrid.getGridData().getColCount() || sheetModel.getGriddata().getRowCount() != this.resultGrid.getGridData().getRowCount()) {
            this.adjustHideCols();
            this.adjustHideRows();
            this.adjustBreakCols();
            this.adjustBreakRows();
            this.adjustRepeatCols();
            this.adjustRepeatRows();
        }
        return this.printSetting;
    }

    private void adjustHideCols() {
        if (this.printSetting.getHideCols().isEmpty()) {
            return;
        }
        ArrayList<Integer> newCols = new ArrayList<Integer>();
        for (Integer col : this.printSetting.getHideCols()) {
            List<Integer> cols = this.resultGrid.getExpandedCols(col);
            newCols.addAll(cols);
        }
        this.printSetting.getHideCols().clear();
        this.printSetting.getHideCols().addAll(newCols);
    }

    private void adjustHideRows() {
        if (this.printSetting.getHideRows().isEmpty()) {
            return;
        }
        ArrayList<Integer> newRows = new ArrayList<Integer>();
        for (Integer row : this.printSetting.getHideRows()) {
            List<Integer> rows = this.resultGrid.getExpandedRows(row);
            newRows.addAll(rows);
        }
        this.printSetting.getHideRows().clear();
        this.printSetting.getHideRows().addAll(newRows);
    }

    private void adjustBreakCols() {
        if (this.printSetting.getBreakCols().isEmpty()) {
            return;
        }
        TreeSet<Integer> newCols = new TreeSet<Integer>();
        int rawPrev = -1;
        for (Integer col : this.printSetting.getBreakCols()) {
            for (int i = col.intValue(); i < this.resultGrid.getGridData().getColCount(); ++i) {
                int rawCol = this.resultGrid.getRawCol(i);
                if (rawCol == col && rawCol != rawPrev) {
                    newCols.add(i);
                }
                rawPrev = rawCol;
            }
        }
        this.printSetting.getBreakCols().clear();
        this.printSetting.getBreakCols().addAll(newCols);
    }

    private void adjustBreakRows() {
        if (this.printSetting.getBreakRows().isEmpty()) {
            return;
        }
        TreeSet<Integer> newRows = new TreeSet<Integer>();
        int rawPrev = -1;
        for (Integer row : this.printSetting.getBreakRows()) {
            for (int i = row.intValue(); i < this.resultGrid.getGridData().getRowCount(); ++i) {
                int rawRow = this.resultGrid.getRawRow(i);
                if (rawRow == row && rawRow != rawPrev) {
                    newRows.add(i);
                }
                rawPrev = rawRow;
            }
        }
        this.printSetting.getBreakRows().clear();
        this.printSetting.getBreakRows().addAll(newRows);
    }

    private void adjustRepeatCols() throws ReportBuildException {
        if (this.printSetting.getRepeatingColStart() <= 0 || this.printSetting.getRepeatingColEnd() <= 0) {
            return;
        }
        int startCol = this.resultGrid.locateNewCol(this.printSetting.getRepeatingColStart());
        int endCol = this.resultGrid.locateNewCol(this.printSetting.getRepeatingColEnd());
        if (startCol > 0 && endCol >= startCol) {
            this.printSetting.setRepeatingColStart(startCol);
            this.printSetting.setRepeatingColEnd(endCol);
        } else {
            this.printSetting.setRepeatingColStart(0);
            this.printSetting.setRepeatingColEnd(0);
        }
    }

    private void adjustRepeatRows() throws ReportBuildException {
        if (this.printSetting.getRepeatingRowStart() <= 0 || this.printSetting.getRepeatingRowEnd() <= 0) {
            return;
        }
        int startRow = this.resultGrid.locateNewRow(this.printSetting.getRepeatingRowStart());
        int endRow = this.resultGrid.locateNewRow(this.printSetting.getRepeatingRowEnd());
        if (startRow > 0 && endRow >= startRow) {
            this.printSetting.setRepeatingRowStart(startRow);
            this.printSetting.setRepeatingRowEnd(endRow);
        } else {
            this.printSetting.setRepeatingRowStart(0);
            this.printSetting.setRepeatingRowEnd(0);
        }
    }
}

