/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFClientAnchor
 *  org.apache.poi.hssf.usermodel.HSSFSheet
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.ClientAnchor
 *  org.apache.poi.ss.usermodel.ClientAnchor$AnchorType
 *  org.apache.poi.ss.usermodel.Drawing
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFClientAnchor
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.BooleanCellProperty;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CustomCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.office.excel.ExcelWritingException;
import com.jiuqi.bi.office.excel.HSSFHelper;
import com.jiuqi.bi.office.excel.ICellOperaterEx;
import com.jiuqi.bi.office.excel.IWorksheetWriter;
import com.jiuqi.bi.office.excel.WorkbookContext;
import com.jiuqi.bi.office.excel.WorksheetWriterSetting;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.office.excel.print.PrintSettingHelper;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

public final class WorksheetWriterEx
implements IWorksheetWriter {
    private final WorkbookContext context;
    private final Sheet xsheet;
    private final GridData gridData;
    private final List<ICellOperaterEx> cellOperaters;
    private final WorksheetWriterSetting wSetting;
    private String title;
    private boolean autoAdjust;
    private boolean addTitle;

    public WorksheetWriterEx(WorkbookContext context, Sheet sheet, GridData gridData) {
        this(context, sheet, gridData, new WorksheetWriterSetting());
    }

    public WorksheetWriterEx(WorkbookContext context, Sheet sheet, GridData gridData, PrintSetting printSetting) {
        this(context, sheet, gridData, new WorksheetWriterSetting());
        this.wSetting.setPrintSetting(printSetting);
    }

    public WorksheetWriterEx(WorkbookContext context, Sheet sheet, GridData gridData, Properties wSetting) {
        this(context, sheet, gridData, new WorksheetWriterSetting());
        this.wSetting.fromProperties(wSetting);
    }

    public WorksheetWriterEx(WorkbookContext context, Sheet sheet, GridData gridData, WorksheetWriterSetting wSetting) {
        this.context = context;
        this.xsheet = sheet;
        this.gridData = gridData;
        this.wSetting = wSetting;
        this.cellOperaters = new ArrayList<ICellOperaterEx>();
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setAutoAdjust(boolean autoAdjust) {
        this.autoAdjust = autoAdjust;
    }

    @Override
    public void setAddTitle(boolean addTitle) {
        this.addTitle = addTitle;
    }

    @Override
    public void addCellOperater(ICellOperaterEx cellOperater) {
        this.cellOperaters.add(cellOperater);
    }

    @Override
    public void writeWorkSheet() throws ExcelWritingException {
        try {
            this.addTitle();
            this.writeContent();
            this.adjustHW();
            this.mergeCells();
            this.hideRowCol();
            this.freezePane();
            this.printSetting();
        }
        catch (Exception e) {
            throw new ExcelWritingException(e.getMessage(), e);
        }
    }

    private void addTitle() {
        if (!this.addTitle) {
            return;
        }
        Cell cell = this.xsheet.createRow(0).createCell(0);
        cell.setCellValue(this.context.createRichText(this.title));
        cell.setCellStyle(this.context.getTitleStyle());
        this.xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, this.gridData.getColCount() - 2));
    }

    private void writeContent() {
        if (this.gridData == null) {
            throw new NullPointerException("\u8868\u683c\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a");
        }
        int rowCount = this.gridData.getRowCount();
        int colCount = this.gridData.getColCount();
        for (int row = 1; row < rowCount; ++row) {
            for (int col = 1; col < colCount; ++col) {
                GridCell gridCell = this.gridData.getCell(col, row);
                this.writeCell(gridCell);
            }
        }
    }

    private void writeCell(GridCell gridCell) {
        Row row;
        int rowNum = gridCell.getRowNum() - 1;
        int colNum = gridCell.getColNum() - 1;
        if (this.addTitle) {
            ++rowNum;
        }
        if ((row = this.xsheet.getRow(rowNum)) == null) {
            row = this.xsheet.createRow(rowNum);
        }
        Cell cell = row.createCell(colNum);
        try {
            CellStyle cellStyle = this.context.getCellStyle(this.gridData, gridCell);
            cell.setCellStyle(cellStyle);
            String cellData = gridCell.getContent();
            switch (gridCell.getType()) {
                case 0: {
                    if (StringUtils.isNotEmpty(cellData)) {
                        if (cellData.matches("^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$")) {
                            cell.setCellValue(Double.parseDouble(cellData));
                            break;
                        }
                        cell.setCellValue(this.context.createRichText(cellData));
                        break;
                    }
                    cell.setCellValue((String)null);
                    break;
                }
                case 1: {
                    this.setCellValue(cell, cellData);
                    break;
                }
                case 4: {
                    BooleanCellProperty bcp = gridCell.toBooleanCell();
                    this.setCellValue(cell, bcp.getShowText(cellData));
                    break;
                }
                case 2: {
                    if (StringUtils.isEmpty(cellData)) break;
                    if (WorksheetWriterEx.isDoubleString(cellData)) {
                        double value = gridCell.getFloat();
                        cell.setCellValue(value);
                        break;
                    }
                    cell.setCellValue(this.context.createRichText(cellData));
                    break;
                }
                case 5: {
                    Date date = gridCell.getDateTime();
                    if (date == null) break;
                    cell.setCellValue(date);
                    break;
                }
                case 3: {
                    if (!StringUtils.isNotEmpty(cellData)) break;
                    if ("|".equals(cellData)) {
                        String value = gridCell.getShowText();
                        cell.setCellValue(this.context.createRichText(value));
                        break;
                    }
                    double value = Double.parseDouble(cellData);
                    cell.setCellValue(value);
                    break;
                }
                case 6: {
                    CellField mergeRect;
                    if (!StringUtils.isNotEmpty(cellData) || (mergeRect = this.gridData.merges().getMergeRect(gridCell.getColNum(), gridCell.getRowNum())) != null && (mergeRect.left != gridCell.getColNum() || mergeRect.top != gridCell.getRowNum())) break;
                    byte[] bytes = gridCell.getImageData();
                    int pictureType = 4;
                    if (gridCell.getImageType().equalsIgnoreCase("jpeg")) {
                        pictureType = 5;
                    } else if (gridCell.getImageType().equalsIgnoreCase("png")) {
                        pictureType = 6;
                    } else if (gridCell.getImageType().equalsIgnoreCase("gif")) {
                        pictureType = 8;
                    } else if (gridCell.getImageType().equalsIgnoreCase("bmp")) {
                        pictureType = 11;
                    }
                    int pictureIndex = this.xsheet.getWorkbook().addPicture(bytes, pictureType);
                    int col2 = colNum + 1;
                    int row2 = rowNum + 1;
                    if (mergeRect != null) {
                        col2 = mergeRect.right;
                        row2 = mergeRect.bottom;
                    }
                    Object clientAnchor = this.xsheet instanceof HSSFSheet ? new HSSFClientAnchor(0, 0, 0, 0, (short)colNum, rowNum, (short)col2, row2) : new XSSFClientAnchor(0, 0, 0, 0, colNum, rowNum, col2, row2);
                    clientAnchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                    Drawing drawingPatriarch = this.xsheet.createDrawingPatriarch();
                    drawingPatriarch.createPicture((ClientAnchor)clientAnchor, pictureIndex);
                    break;
                }
                case 9: {
                    if (!StringUtils.isNotEmpty(cellData)) break;
                    cell.setCellValue(this.context.createRichText(cellData));
                    break;
                }
                case 100: {
                    if (StringUtils.isEmpty(gridCell.getCellData())) break;
                    String customShowFormat = gridCell.getShowFormat();
                    if (CustomCellProperty.isDateFormat(customShowFormat)) {
                        cell.setCellValue(gridCell.getDate());
                        break;
                    }
                    if (CustomCellProperty.isNumberFormat(customShowFormat)) {
                        cell.setCellValue(gridCell.getFloat());
                        break;
                    }
                    cell.setCellValue(cellData);
                    break;
                }
            }
            for (ICellOperaterEx operator : this.cellOperaters) {
                operator.handle(gridCell, cell);
            }
        }
        catch (Exception e) {
            throw new ExcelWritingException(e);
        }
    }

    private void setCellValue(Cell cell, String strValue) {
        if (strValue != null) {
            cell.setCellValue(this.context.createRichText(strValue));
        } else {
            cell.setCellValue((String)null);
        }
    }

    private void mergeCells() {
        GridFieldList fieldList = this.gridData.merges();
        for (int i = 0; i < fieldList.count(); ++i) {
            CellField field = fieldList.get(i);
            if (field.left == field.right && field.top == field.bottom) continue;
            int rowFrom = field.top - 1;
            int rowTo = field.bottom - 1;
            int colFrom = field.left - 1;
            int colTo = field.right - 1;
            if (this.addTitle) {
                ++rowFrom;
                ++rowTo;
            }
            CellRangeAddress range = new CellRangeAddress(rowFrom, rowTo, colFrom, colTo);
            this.xsheet.addMergedRegionUnsafe(range);
        }
    }

    private void adjustHW() {
        int colCount = this.gridData.getColCount();
        int rowCount = this.gridData.getRowCount();
        if (this.autoAdjust || (this.context.getOptions() & 1) != 0) {
            for (int col = 0; col < colCount; ++col) {
                this.xsheet.autoSizeColumn((int)((short)col));
            }
        } else {
            int col;
            if (this.hasAutoSizeColumn()) {
                if (this.xsheet instanceof SXSSFSheet) {
                    ((SXSSFSheet)this.xsheet).trackAllColumnsForAutoSizing();
                }
                for (col = 0; col < colCount; ++col) {
                    this.xsheet.autoSizeColumn((int)((short)col));
                }
            }
            for (col = 1; col < colCount; ++col) {
                if (this.gridData.getColAutoSize(col)) continue;
                int colWidth = this.gridData.getColWidths(col);
                this.xsheet.setColumnWidth(col - 1, HSSFHelper.pixelToWidth(colWidth));
            }
            for (int row = 1; row < rowCount; ++row) {
                Row hssfRow;
                if (!this.gridData.getRowVisible(row) || this.gridData.getRowAutoSize(row)) continue;
                int rowHeight = this.gridData.getRowHeights(row);
                Row row2 = hssfRow = this.addTitle ? this.xsheet.getRow(row) : this.xsheet.getRow(row - 1);
                if (hssfRow == null) continue;
                hssfRow.setHeight(HSSFHelper.pixelToHeight(rowHeight));
            }
        }
    }

    private boolean hasAutoSizeColumn() {
        for (int col = 1; col < this.gridData.getColCount(); ++col) {
            if (!this.gridData.getColAutoSize(col)) continue;
            return true;
        }
        return false;
    }

    private void hideRowCol() {
        for (int col = 1; col <= this.gridData.getColCount() - 1; ++col) {
            if (this.gridData.getColVisible(col)) continue;
            this.xsheet.setColumnHidden(col - 1, true);
        }
        for (int row = 1; row <= this.gridData.getRowCount() - 1; ++row) {
            Row hssfRow;
            if (this.gridData.getRowVisible(row)) continue;
            Row row2 = hssfRow = this.addTitle ? this.xsheet.getRow(row) : this.xsheet.getRow(row - 1);
            if (hssfRow == null) continue;
            hssfRow.setZeroHeight(true);
        }
    }

    private void freezePane() {
        int topCol = this.gridData.getScrollTopCol() > 0 ? this.gridData.getScrollTopCol() - 1 : 0;
        int topRow = this.gridData.getScrollTopRow() > 0 ? this.gridData.getScrollTopRow() - 1 : 0;
        this.xsheet.createFreezePane(topCol, topRow, topCol, topRow);
    }

    private void printSetting() {
        if (this.wSetting == null || this.wSetting.getPrintSetting() == null) {
            return;
        }
        PrintSettingHelper.loadPrintSetting(this.xsheet, this.wSetting.getPrintSetting());
    }

    private static boolean isDoubleString(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        String s = value.replace(",", "").replaceAll("[^eE0-9-.]", " ").trim();
        try {
            Double.parseDouble(s);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

