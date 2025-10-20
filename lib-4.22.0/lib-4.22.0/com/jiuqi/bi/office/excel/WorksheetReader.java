/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ooxml.POIXMLDocumentPart
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.PaneInformation
 *  org.apache.poi.xssf.usermodel.XSSFClientAnchor
 *  org.apache.poi.xssf.usermodel.XSSFDrawing
 *  org.apache.poi.xssf.usermodel.XSSFPicture
 *  org.apache.poi.xssf.usermodel.XSSFShape
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.office.excel.FormulaConvertor;
import com.jiuqi.bi.office.excel.HSSFHelper;
import com.jiuqi.bi.office.excel.ICellOperaterEx;
import com.jiuqi.bi.office.excel.SheetCellReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PaneInformation;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class WorksheetReader {
    private Sheet sheet;
    private GridData gridData;
    private int rowCount;
    private int colCount;
    private SheetCellReader sheetCellReader;
    private Workbook workBook;
    private FormulaConvertor fc;
    private List<ICellOperaterEx> cellOperaters;
    private boolean isParseFormula = false;

    public WorksheetReader(Workbook workBook, Sheet sheet) {
        this(workBook, sheet, false);
    }

    public WorksheetReader(Workbook workbook, Sheet sheet, boolean isParseFormula) {
        this.sheet = sheet;
        this.workBook = workbook;
        this.isParseFormula = isParseFormula;
        this.cellOperaters = new ArrayList<ICellOperaterEx>();
    }

    public GridData getGridData() {
        this.loadGridData();
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        this.gridData = gridData;
    }

    public void addCellOperater(ICellOperaterEx cellOperater) {
        this.cellOperaters.add(cellOperater);
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
            Iterator it = row.cellIterator();
            while (it.hasNext()) {
                Cell cell = (Cell)it.next();
                GridCell gridCell = this.sheetCellReader.readCell(cell, this.isParseFormula);
                for (ICellOperaterEx operator : this.cellOperaters) {
                    operator.handle(gridCell, cell);
                }
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
        this.readPicture();
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

    private void readPicture() {
        if (this.sheet instanceof XSSFSheet) {
            XSSFSheet xssfSheet = (XSSFSheet)this.sheet;
            List relations = xssfSheet.getRelations();
            for (POIXMLDocumentPart relation : relations) {
                if (!(relation instanceof XSSFDrawing)) continue;
                XSSFDrawing drawing = (XSSFDrawing)relation;
                List shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    if (!(shape instanceof XSSFPicture)) continue;
                    XSSFPicture picture = (XSSFPicture)shape;
                    XSSFClientAnchor preferredSize = picture.getClientAnchor();
                    int row = preferredSize.getRow1();
                    short col = preferredSize.getCol1();
                    GridCell gridCell = this.gridData.getCellEx(col + 1, row + 1);
                    String mimeType = picture.getPictureData().getMimeType();
                    if (mimeType.toUpperCase().startsWith("IMAGE/")) {
                        mimeType = mimeType.substring(6);
                    }
                    gridCell.setImageData(picture.getPictureData().getData(), mimeType);
                    this.gridData.setCell(gridCell);
                }
            }
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

