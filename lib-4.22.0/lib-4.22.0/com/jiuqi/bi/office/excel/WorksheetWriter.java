/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFCell
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFFont
 *  org.apache.poi.hssf.usermodel.HSSFRichTextString
 *  org.apache.poi.hssf.usermodel.HSSFRow
 *  org.apache.poi.hssf.usermodel.HSSFSheet
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.RichTextString
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.office.excel.CellEdge;
import com.jiuqi.bi.office.excel.CellStyleKey;
import com.jiuqi.bi.office.excel.ExcelWritingException;
import com.jiuqi.bi.office.excel.ExportConsts;
import com.jiuqi.bi.office.excel.HSSFHelper;
import com.jiuqi.bi.office.excel.ICellOperater;
import com.jiuqi.bi.office.excel.ICellOperaterEx;
import com.jiuqi.bi.office.excel.IWorksheetWriter;
import com.jiuqi.bi.office.excel.WorksheetWriterSetting;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.office.excel.print.PrintSettingHelper;
import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.util.Html;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

@Deprecated
public final class WorksheetWriter
implements IWorksheetWriter {
    private HSSFWorkbook workBook;
    private HSSFSheet sheet;
    private GridData gridData;
    private String title;
    private boolean autoAdjust;
    private boolean addTitle;
    private HSSFFont titleFont;
    private HashMap cellStyleHashMap;
    private HashMap cellFontHashMap;
    private List cellOperaters = new ArrayList();
    private List cellExOperaters = new ArrayList();
    private WorksheetWriterSetting wSetting;

    public WorksheetWriter(HSSFWorkbook workBook, HSSFSheet sheet, GridData gridData, HashMap cellStyleHashMap) {
        this(workBook, sheet, gridData, cellStyleHashMap, new HashMap());
    }

    public WorksheetWriter(HSSFWorkbook workBook, HSSFSheet sheet, GridData gridData, HashMap cellStyleHashMap, HashMap cellFontHashMap) {
        this(workBook, sheet, gridData, cellStyleHashMap, cellFontHashMap, new WorksheetWriterSetting());
    }

    public WorksheetWriter(HSSFWorkbook workBook, HSSFSheet sheet, GridData gridData, HashMap cellStyleHashMap, PrintSetting printSetting) {
        this(workBook, sheet, gridData, cellStyleHashMap, new HashMap(), new WorksheetWriterSetting());
        this.wSetting.setPrintSetting(printSetting);
    }

    public WorksheetWriter(HSSFWorkbook workBook, HSSFSheet sheet, GridData gridData, HashMap cellStyleHashMap, Properties wSetting) {
        this(workBook, sheet, gridData, cellStyleHashMap, new HashMap(), new WorksheetWriterSetting());
        this.wSetting.fromProperties(wSetting);
    }

    public WorksheetWriter(HSSFWorkbook workBook, HSSFSheet sheet, GridData gridData, HashMap cellStyleHashMap, HashMap cellFontHashMap, WorksheetWriterSetting wSetting) {
        this.workBook = workBook;
        this.sheet = sheet;
        this.gridData = gridData;
        this.cellStyleHashMap = cellStyleHashMap;
        this.cellFontHashMap = cellFontHashMap;
        this.wSetting = wSetting;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    private void setDefaultTitleFont() {
        this.titleFont = this.workBook.createFont();
        this.titleFont.setFontName("\u5b8b\u4f53");
        this.titleFont.setBold(true);
        this.titleFont.setFontHeightInPoints((short)12);
    }

    @Override
    public void setAutoAdjust(boolean autoAdjust) {
        this.autoAdjust = autoAdjust;
    }

    @Override
    public void setAddTitle(boolean addTitle) {
        this.addTitle = addTitle;
    }

    public void addCellOperater(ICellOperater cellOperater) {
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
        if (this.addTitle) {
            this.setDefaultTitleFont();
            HSSFRichTextString richText = new HSSFRichTextString(this.title);
            HSSFCell cell = this.sheet.createRow(0).createCell(0);
            cell.setCellValue((RichTextString)richText);
            HSSFCellStyle titleCellStyle = this.workBook.createCellStyle();
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCellStyle.setFont(this.titleFont);
            cell.setCellStyle(titleCellStyle);
            this.sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, this.gridData.getColCount() - 2));
        }
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
        int rowNum = gridCell.getRowNum() - 1;
        int colNum = gridCell.getColNum() - 1;
        if (this.addTitle) {
            ++rowNum;
        }
        HSSFRow row = this.sheet.getRow(rowNum) == null ? this.sheet.createRow(rowNum) : this.sheet.getRow(rowNum);
        HSSFCell cell = row.createCell(colNum);
        try {
            HSSFCellStyle cellStyle = this.buildCellStyle(gridCell);
            cell.setCellStyle(cellStyle);
            switch (gridCell.getDataType()) {
                case 0: {
                    String strValue = gridCell.getCellData();
                    if (strValue != null) {
                        cell.setCellValue((RichTextString)new HSSFRichTextString(strValue));
                        break;
                    }
                    cell.setCellValue((String)null);
                    break;
                }
                case 1: 
                case 4: {
                    String strValue = gridCell.getCellData();
                    if (strValue != null) {
                        HSSFRichTextString richTextString = new HSSFRichTextString(strValue);
                        cell.setCellValue((RichTextString)richTextString);
                        break;
                    }
                    cell.setCellValue((String)null);
                    break;
                }
                case 2: {
                    String cellData = gridCell.getCellData();
                    if (cellData == null || cellData.equals("")) break;
                    double value = gridCell.getFloat();
                    cell.setCellValue(value);
                    NumberCellPropertyIntf ncp = gridCell.toNumberCell();
                    if (!ncp.getIsPercent() || !ncp.getThoundsMark()) break;
                    String decimalStr = "#,##0" + this.buildDecimal(ncp) + "\u2030";
                    DecimalFormat numf = new DecimalFormat(decimalStr);
                    String formatValue = numf.format(value);
                    cell.setCellValue((RichTextString)new HSSFRichTextString(formatValue));
                    break;
                }
                case 5: {
                    Date date = gridCell.getDateTime();
                    if (date == null) break;
                    cell.setCellValue(date);
                    break;
                }
                case 3: {
                    String cellData = gridCell.getCellData();
                    if (cellData == null || cellData.equals("")) break;
                    if ("|".equals(cellData)) {
                        String value = gridCell.getShowText();
                        cell.setCellValue((RichTextString)new HSSFRichTextString(value));
                        break;
                    }
                    double value = Double.parseDouble(cellData);
                    cell.setCellValue(value);
                    break;
                }
                case 9: {
                    String linkInfoStr = gridCell.getCellData();
                    if (linkInfoStr == null || "".equals(linkInfoStr)) break;
                    String[] info = GridCell.parserLinkInformation(linkInfoStr);
                    HSSFRichTextString hssfStr = new HSSFRichTextString(info[0]);
                    cell.setCellValue((RichTextString)hssfStr);
                    break;
                }
            }
            for (Object obj : this.cellOperaters) {
                ((ICellOperater)obj).handle(gridCell, cell);
            }
            for (Object obj : this.cellExOperaters) {
                ((ICellOperaterEx)obj).handle(gridCell, (Cell)cell);
            }
        }
        catch (Exception e) {
            throw new ExcelWritingException(e.getMessage(), e);
        }
    }

    private String makeCurFotmat(CurrencyCellProperty curCellProperty) {
        String currencyFormat = "#,##0";
        String colorStr = "";
        boolean isBracketNegative = false;
        currencyFormat = currencyFormat + this.buildDecimal(curCellProperty);
        int idx = curCellProperty.getUnitIndex();
        if (curCellProperty.getWarningNegative()) {
            colorStr = "[Red]";
        }
        isBracketNegative = curCellProperty.getBracketNegative();
        switch (curCellProperty.getUnitShowType()) {
            case 0: {
                String signStr = CurrencyCellProperty.CURRENCY_UNIT_SIGNS[idx];
                if (signStr.length() == 1) {
                    currencyFormat = isBracketNegative ? signStr + currencyFormat + ";" + colorStr + signStr + "(" + currencyFormat + ")" : signStr + currencyFormat + ";" + colorStr + signStr + "-" + currencyFormat;
                    break;
                }
                if (signStr.length() <= 1) break;
                currencyFormat = isBracketNegative ? "[$" + signStr + "]" + currencyFormat + ";" + colorStr + "[$" + signStr + "](" + currencyFormat + ")" : "[$" + signStr + "]" + currencyFormat + ";" + colorStr + "[$" + signStr + "]-" + currencyFormat;
                break;
            }
            case 1: {
                String nameStr = CurrencyCellProperty.CURRENCY_UNIT_NAMES[idx];
                currencyFormat = isBracketNegative ? "[$" + nameStr + "] " + currencyFormat + ";" + colorStr + "[$" + nameStr + "] (" + currencyFormat + ")" : "[$" + nameStr + "] " + currencyFormat + ";" + colorStr + "[$" + nameStr + "] -" + currencyFormat;
                break;
            }
            case 2: {
                String nameStr = CurrencyCellProperty.CURRENCY_UNIT_NAMES[idx];
                currencyFormat = isBracketNegative ? currencyFormat + " [$" + nameStr + "];" + colorStr + "(" + currencyFormat + ") [$" + nameStr + "]" : currencyFormat + " [$" + nameStr + "];" + colorStr + "-" + currencyFormat + " [$" + nameStr + "]";
                break;
            }
            case 3: {
                String titleStr = CurrencyCellProperty.CURRENCY_UNIT_TITLES[idx];
                currencyFormat = isBracketNegative ? "[$" + titleStr + "] " + currencyFormat + ";" + colorStr + "[$" + titleStr + "] (" + currencyFormat + ")" : "[$" + titleStr + "] " + currencyFormat + ";" + colorStr + "[$" + titleStr + "] -" + currencyFormat;
                break;
            }
        }
        return currencyFormat;
    }

    private String buildDecimal(NumberCellPropertyIntf numberCellProperty) {
        int decimalCount = numberCellProperty.getDecimal();
        if (decimalCount > 0) {
            String decimal = ".";
            for (int i = 0; i < decimalCount; ++i) {
                decimal = decimal + "0";
            }
            return decimal;
        }
        return "";
    }

    private HSSFFont getCellFont(GridCell gridCell) {
        HSSFColor fontColor;
        HSSFFont font = (HSSFFont)this.cellFontHashMap.get(gridCell);
        if (font != null) {
            return font;
        }
        font = this.workBook.createFont();
        if (gridCell.getFontBold()) {
            font.setBold(true);
        }
        if ((fontColor = HSSFHelper.getApproximateTextColor(gridCell.getFontColor())) != null) {
            font.setColor(fontColor.getIndex());
        }
        font.setFontHeight((short)gridCell.getFontHeight());
        font.setItalic(gridCell.getFontItalic());
        font.setFontName(gridCell.getFontName());
        font.setFontHeightInPoints((short)gridCell.getFontSize());
        font.setStrikeout(gridCell.getFontStrikeOut());
        if (gridCell.getFontUnderLine()) {
            font.setUnderline((byte)1);
        }
        this.cellFontHashMap.put(gridCell, font);
        return font;
    }

    private HSSFCellStyle buildCellStyle(GridCell gridCell) {
        short formatIndex;
        short formatIndex2;
        CellEdge.EdgeInfo[] edgeInfos = new CellEdge(this.gridData).getBorders(gridCell.getColNum(), gridCell.getRowNum());
        HSSFCellStyle cellStyle = (HSSFCellStyle)this.cellStyleHashMap.get(new CellStyleKey(gridCell, edgeInfos));
        if (cellStyle != null) {
            return cellStyle;
        }
        GridCell tempGridCell = this.gridData.getCellEx(gridCell.getColNum(), gridCell.getRowNum());
        cellStyle = this.workBook.createCellStyle();
        if (!Html.isTransparent(tempGridCell.getBackColor()) && tempGridCell.getBackColor() != 0xFFFFFF) {
            HSSFColor backColor;
            short backColorIdx = HSSFColor.HSSFColorPredefined.WHITE.getIndex();
            if (tempGridCell.getSilverHead()) {
                backColorIdx = ExportConsts.TABLEHEAD_COLOR;
            } else if (tempGridCell.getBackStyle() != 0 && (backColor = HSSFHelper.getApproximateColor(tempGridCell.getBackColor())) != null) {
                backColorIdx = backColor.getIndex();
            }
            cellStyle.setFillForegroundColor(backColorIdx);
            if (backColorIdx != HSSFColor.HSSFColorPredefined.DARK_GREEN.getIndex()) {
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
        }
        BorderStyle topBorder = this.transBorderStyle(tempGridCell.getTEdgeStyle());
        cellStyle.setBorderTop(topBorder);
        HSSFColor topBorderColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getColor();
        if (!this.isEdgeDefault(tempGridCell, 1)) {
            topBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getTEdgeColor());
        }
        if ((topBorderColor = this.buildBorderColor(topBorderColor, tempGridCell.getTEdgeColor())) != null) {
            cellStyle.setTopBorderColor(topBorderColor.getIndex());
        }
        BorderStyle rightBorder = this.transBorderStyle(tempGridCell.getREdgeStyle());
        cellStyle.setBorderRight(rightBorder);
        HSSFColor righttBorderColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getColor();
        if (!this.isEdgeDefault(tempGridCell, 2)) {
            righttBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getREdgeColor());
        }
        if ((righttBorderColor = this.buildBorderColor(righttBorderColor, tempGridCell.getREdgeColor())) != null) {
            cellStyle.setRightBorderColor(righttBorderColor.getIndex());
        }
        BorderStyle leftBorder = this.transBorderStyle(tempGridCell.getLEdgeStyle());
        cellStyle.setBorderLeft(leftBorder);
        HSSFColor leftBorderColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getColor();
        if (!this.isEdgeDefault(tempGridCell, 3)) {
            leftBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getLEdgeColor());
        }
        if ((leftBorderColor = this.buildBorderColor(leftBorderColor, tempGridCell.getLEdgeColor())) != null) {
            cellStyle.setLeftBorderColor(leftBorderColor.getIndex());
        }
        BorderStyle bottomBorder = this.transBorderStyle(tempGridCell.getBEdgeStyle());
        cellStyle.setBorderBottom(bottomBorder);
        HSSFColor bottomBorderColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getColor();
        if (!this.isEdgeDefault(tempGridCell, 0)) {
            bottomBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getBEdgeColor());
        }
        if ((bottomBorderColor = this.buildBorderColor(bottomBorderColor, tempGridCell.getBEdgeColor())) != null) {
            cellStyle.setBottomBorderColor(bottomBorderColor.getIndex());
        }
        HorizontalAlignment horzAlign = this.transHorizCellAlign(tempGridCell.getHorzAlign());
        VerticalAlignment vertAlign = this.transVerticalCellAlign(tempGridCell.getVertAlign());
        cellStyle.setAlignment(horzAlign);
        cellStyle.setVerticalAlignment(vertAlign);
        cellStyle.setWrapText(tempGridCell.getWrapLine());
        cellStyle.setIndention((short)tempGridCell.getIndent());
        HSSFFont font = this.getCellFont(tempGridCell);
        cellStyle.setFont(font);
        if (tempGridCell.getDataType() == 0) {
            cellStyle.setDataFormat((short)0);
        }
        if (tempGridCell.getDataType() == 1) {
            formatIndex2 = this.workBook.createDataFormat().getFormat("@");
            cellStyle.setDataFormat(formatIndex2);
        }
        if (tempGridCell.getDataType() == 2) {
            String formatStr;
            NumberCellProperty ncp = new NumberCellProperty(tempGridCell);
            if (ncp.getIsPercent()) {
                formatStr = "0" + this.buildDecimal(ncp) + "%";
                formatIndex = this.workBook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            } else if (tempGridCell.getShowText() != null && tempGridCell.getShowText().endsWith("\u2030")) {
                String numStr = "0";
                String colorStr = "";
                numStr = ncp.getThoundsMark() ? "#,##0" + this.buildDecimal(ncp) : numStr + this.buildDecimal(ncp);
                if (ncp.getWarningNegative()) {
                    colorStr = "[Red]";
                }
                String formatStr2 = "";
                formatStr2 = ncp.getBracketNegative() ? numStr + ";" + colorStr + "(" + numStr + ")" : numStr + ";" + colorStr + "-" + numStr;
                short formatIndex3 = this.workBook.createDataFormat().getFormat(formatStr2);
                cellStyle.setDataFormat(formatIndex3);
            } else {
                formatStr = "0";
                formatStr = ncp.getThoundsMark() ? "#,##0" + this.buildDecimal(ncp) : formatStr + this.buildDecimal(ncp);
                formatStr = formatStr + "_ ";
                formatIndex = this.workBook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            }
        }
        if (tempGridCell.getDataType() == 3) {
            CurrencyCellProperty curCellProperty = new CurrencyCellProperty(tempGridCell);
            String currencyFormat = this.makeCurFotmat(curCellProperty);
            formatIndex = this.workBook.createDataFormat().getFormat(currencyFormat);
            cellStyle.setDataFormat(formatIndex);
        }
        if (gridCell.getDataType() == 5) {
            formatIndex2 = this.workBook.createDataFormat().getFormat("yyyy-m-d");
            cellStyle.setDataFormat(formatIndex2);
        }
        if (gridCell.getDataType() == 9) {
            // empty if block
        }
        this.cellStyleHashMap.put(new CellStyleKey(tempGridCell, edgeInfos), cellStyle);
        return cellStyle;
    }

    private boolean isEdgeDefault(GridCell cell, int edgePosition) {
        switch (edgePosition) {
            case 1: {
                return cell.getTEdgeStyle() == 2 && cell.getTEdgeColor() == 0xD1D1D1;
            }
            case 2: {
                return cell.getREdgeStyle() == 2 && cell.getREdgeColor() == 0xD1D1D1;
            }
            case 3: {
                return cell.getBEdgeStyle() == 2 && cell.getBEdgeColor() == 0xD1D1D1;
            }
            case 0: {
                return cell.getLEdgeStyle() == 2 && cell.getLEdgeColor() == 0xD1D1D1;
            }
        }
        return false;
    }

    private HorizontalAlignment transHorizCellAlign(int align) {
        switch (align) {
            case 0: {
                return HorizontalAlignment.GENERAL;
            }
            case 1: {
                return HorizontalAlignment.LEFT;
            }
            case 2: {
                return HorizontalAlignment.RIGHT;
            }
            case 3: {
                return HorizontalAlignment.CENTER;
            }
            case 4: 
            case 5: {
                return HorizontalAlignment.JUSTIFY;
            }
            case 6: {
                return HorizontalAlignment.RIGHT;
            }
        }
        return HorizontalAlignment.GENERAL;
    }

    private VerticalAlignment transVerticalCellAlign(int align) {
        switch (align) {
            case 0: {
                return VerticalAlignment.CENTER;
            }
            case 1: {
                return VerticalAlignment.TOP;
            }
            case 2: {
                return VerticalAlignment.BOTTOM;
            }
            case 3: {
                return VerticalAlignment.CENTER;
            }
            case 4: 
            case 5: {
                return VerticalAlignment.JUSTIFY;
            }
            case 6: {
                return VerticalAlignment.BOTTOM;
            }
        }
        return VerticalAlignment.CENTER;
    }

    private BorderStyle transBorderStyle(int idx) {
        switch (idx) {
            case 0: {
                return BorderStyle.THIN;
            }
            case 1: {
                return BorderStyle.NONE;
            }
            case 2: {
                return BorderStyle.THIN;
            }
            case 3: {
                return BorderStyle.DASHED;
            }
            case 4: {
                return BorderStyle.DOTTED;
            }
            case 5: {
                return BorderStyle.DASH_DOT;
            }
            case 6: {
                return BorderStyle.DASH_DOT_DOT;
            }
            case 7: 
            case 8: 
            case 9: {
                return BorderStyle.THICK;
            }
            case 10: 
            case 11: 
            case 12: {
                return BorderStyle.DOUBLE;
            }
            case 13: 
            case 14: 
            case 15: {
                return BorderStyle.THICK;
            }
        }
        return BorderStyle.THIN;
    }

    private void mergeCells() {
        GridFieldList fieldList = this.gridData.merges();
        for (int i = 0; i < fieldList.count(); ++i) {
            CellRangeAddress range;
            CellField field = fieldList.get(i);
            int rowFrom = field.top - 1;
            int rowTo = field.bottom - 1;
            int colFrom = field.left - 1;
            int colTo = field.right - 1;
            if (this.addTitle) {
                ++rowFrom;
                ++rowTo;
            }
            if ((range = new CellRangeAddress(rowFrom, rowTo, colFrom, colTo)).getFirstRow() >= range.getLastRow() || range.getFirstColumn() >= range.getLastColumn()) continue;
            this.sheet.addMergedRegion(range);
        }
    }

    private void adjustHW() {
        int colCount = this.gridData.getColCount();
        int rowCount = this.gridData.getRowCount();
        if (this.autoAdjust) {
            for (int col = 0; col < colCount; ++col) {
                this.sheet.autoSizeColumn((int)((short)col));
            }
        } else {
            int col;
            if (this.hasAutoSizeColumn()) {
                for (col = 1; col < colCount; ++col) {
                    this.sheet.autoSizeColumn((int)((short)col));
                }
            }
            for (col = 1; col < colCount; ++col) {
                if (this.gridData.getColAutoSize(col)) continue;
                int colWidth = this.gridData.getColWidths(col);
                this.sheet.setColumnWidth(col - 1, HSSFHelper.pixelToWidth(colWidth));
            }
            for (int row = 1; row < rowCount; ++row) {
                HSSFRow hssfRow;
                if (!this.gridData.getRowVisible(row) || this.gridData.getRowAutoSize(row)) continue;
                int rowHeight = this.gridData.getRowHeights(row);
                HSSFRow hSSFRow = hssfRow = this.addTitle ? this.sheet.getRow(row) : this.sheet.getRow(row - 1);
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
            this.sheet.setColumnHidden(col - 1, true);
        }
        for (int row = 1; row <= this.gridData.getRowCount() - 1; ++row) {
            if (this.gridData.getRowVisible(row)) continue;
            HSSFRow hssfRow = this.addTitle ? this.sheet.getRow(row) : this.sheet.getRow(row - 1);
            hssfRow.setZeroHeight(true);
        }
    }

    private void freezePane() {
        int topCol = this.gridData.getScrollTopCol() > 0 ? this.gridData.getScrollTopCol() - 1 : 0;
        int topRow = this.gridData.getScrollTopRow() > 0 ? this.gridData.getScrollTopRow() - 1 : 0;
        this.sheet.createFreezePane(topCol, topRow, topCol, topRow);
    }

    @Override
    public void addCellOperater(ICellOperaterEx cellOperater) {
        this.cellExOperaters.add(cellOperater);
    }

    private HSSFColor buildBorderColor(HSSFColor hssfColor, int borderColor) {
        if (hssfColor != null && HSSFColor.HSSFColorPredefined.WHITE.getIndex() == hssfColor.getIndex() && !Html.getHtmlColor(borderColor).equalsIgnoreCase("#ffffff")) {
            return null;
        }
        return hssfColor;
    }

    private void printSetting() {
        if (this.wSetting == null || this.wSetting.getPrintSetting() == null) {
            return;
        }
        PrintSettingHelper.loadPrintSetting((Sheet)this.sheet, this.wSetting.getPrintSetting());
    }
}

