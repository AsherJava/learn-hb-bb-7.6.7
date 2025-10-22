/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.CellField
 *  com.jiuqi.np.grid.GridCell
 *  com.jiuqi.np.grid.GridData
 *  com.jiuqi.np.grid.GridFieldList
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid.GridFieldList;
import com.jiuqi.np.office.excel.FormulaConvertor;
import com.jiuqi.np.office.excel.HSSFHelper;
import com.jiuqi.np.office.excel.SheetCellReader;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PaneInformation;

public class WorksheetReader {
    private Sheet sheet;
    private GridData gridData;
    private int rowCount;
    private int colCount;
    private SheetCellReader sheetCellReader;
    private Workbook workBook;
    private FormulaConvertor fc;
    private boolean isParseFormula = false;

    public WorksheetReader(Workbook workBook, Sheet sheet) {
        this(workBook, sheet, false);
    }

    public WorksheetReader(Workbook workbook, Sheet sheet, boolean isParseFormula) {
        this.sheet = sheet;
        this.workBook = workbook;
        this.isParseFormula = isParseFormula;
    }

    public GridData getGridData() {
        this.loadGridData();
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        this.gridData = gridData;
    }

    private void loadGridData() {
        if (this.gridData == null) {
            this.gridData = new GridData(0, 0);
        }
        this.sheetCellReader = new SheetCellReader(this.workBook, this.gridData);
        this.sheetCellReader.setFormulaConvertor(this.fc);
        this.rowCount = this.sheet.getLastRowNum() + 1;
        if (this.rowCount == 1 && this.sheet.getRow(0) == null) {
            this.gridData.setRowCount(2);
            this.gridData.setColCount(2);
            return;
        }
        this.gridData.setRowCount(this.rowCount + 1);
        for (int i = 0; i < this.rowCount; ++i) {
            Row row = this.sheet.getRow(i);
            if (row == null) continue;
            if (row.getLastCellNum() > this.colCount) {
                this.colCount = row.getLastCellNum();
                this.gridData.setColCount(this.colCount + 1);
            }
            Iterator<Cell> it = row.cellIterator();
            while (it.hasNext()) {
                Cell cell = it.next();
                GridCell gridCell = this.sheetCellReader.readCell(cell, this.isParseFormula);
                this.gridData.setCell(gridCell);
            }
        }
        PaneInformation pim = this.sheet.getPaneInformation();
        if (pim != null) {
            this.gridData.setScrollTopCol(pim.getVerticalSplitPosition() + 1);
            this.gridData.setScrollTopRow(pim.getHorizontalSplitPosition() + 1);
        }
        this.mergeCells();
        this.setRowColProperty();
    }

    private void setRowColProperty() {
        int i;
        for (i = 0; i < this.rowCount; ++i) {
            Row row = this.sheet.getRow(i);
            if (row != null) {
                this.gridData.setRowVisible(i + 1, !row.getZeroHeight());
            }
            if (row == null) continue;
            this.gridData.setRowHeights(i + 1, HSSFHelper.heightToPixel(row.getHeight()));
        }
        for (i = 0; i < this.colCount; ++i) {
            this.gridData.setColWidths(i + 1, HSSFHelper.widthToPixel((short)this.sheet.getColumnWidth(i)));
            this.gridData.setColVisible(i + 1, !this.sheet.isColumnHidden(i));
        }
    }

    private void mergeCells() {
        GridFieldList fieldList = this.gridData.merges();
        for (int i = 0; i < this.sheet.getNumMergedRegions(); ++i) {
            CellRangeAddress region = this.sheet.getMergedRegion(i);
            int rowFrom = region.getFirstRow() + 1;
            int rowTo = region.getLastRow() + 1;
            int colFrom = region.getFirstColumn() + 1;
            int colTo = region.getLastColumn() + 1;
            CellField field = new CellField(colFrom, rowFrom, colTo, rowTo);
            fieldList.addMergeRect(field);
        }
    }

    public void setFormulaConvertor(FormulaConvertor fc) {
        this.fc = fc;
    }
}

