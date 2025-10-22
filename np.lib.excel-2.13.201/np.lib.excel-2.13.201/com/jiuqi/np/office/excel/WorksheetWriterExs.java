/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.np.grid.CellField
 *  com.jiuqi.np.grid.CurrencyCellProperty
 *  com.jiuqi.np.grid.GridCell
 *  com.jiuqi.np.grid.GridData
 *  com.jiuqi.np.grid.GridFieldList
 *  com.jiuqi.np.grid.NumberCellProperty
 *  com.jiuqi.np.grid.NumberCellPropertyIntf
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.bi.util.Html;
import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.CurrencyCellProperty;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid.GridFieldList;
import com.jiuqi.np.grid.NumberCellProperty;
import com.jiuqi.np.grid.NumberCellPropertyIntf;
import com.jiuqi.np.office.excel.CellStyleKey;
import com.jiuqi.np.office.excel.ExcelWritingException;
import com.jiuqi.np.office.excel.ExportConsts;
import com.jiuqi.np.office.excel.HSSFHelper;
import com.jiuqi.np.office.excel.ICellOperaterEx;
import com.jiuqi.np.office.excel.IWorksheetWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public final class WorksheetWriterExs
implements IWorksheetWriter {
    private Workbook xworkBook;
    private Sheet xsheet;
    private GridData gridData;
    private String title;
    private boolean autoAdjust;
    private boolean addTitle;
    private Font xtitleFont;
    private HashMap<CellStyleKey, CellStyle> cellStyleHashMap;
    private HashMap<GridCell, Font> cellFontHashMap;
    private List<ICellOperaterEx> cellOperaters = new ArrayList<ICellOperaterEx>();

    public WorksheetWriterExs(Workbook workBook, Sheet sheet, GridData gridData, HashMap<CellStyleKey, CellStyle> cellStyleHashMap) {
        this(workBook, sheet, gridData, cellStyleHashMap, new HashMap<GridCell, Font>());
    }

    public WorksheetWriterExs(Workbook workBook, Sheet sheet, GridData gridData, HashMap<CellStyleKey, CellStyle> cellStyleHashMap, HashMap<GridCell, Font> cellFontHashMap) {
        this.xworkBook = workBook;
        this.xsheet = sheet;
        this.gridData = gridData;
        this.cellStyleHashMap = cellStyleHashMap;
        this.cellFontHashMap = cellFontHashMap;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    private void setDefaultTitleFont() {
        this.xtitleFont = this.xworkBook.createFont();
        this.xtitleFont.setFontName("\u5b8b\u4f53");
        this.xtitleFont.setBold(true);
        this.xtitleFont.setFontHeightInPoints((short)12);
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
        }
        catch (Exception e) {
            throw new ExcelWritingException(e.getMessage(), e);
        }
    }

    private void addTitle() {
        if (this.addTitle) {
            this.setDefaultTitleFont();
            XSSFRichTextString richText = new XSSFRichTextString(this.title);
            Cell cell = this.xsheet.createRow(0).createCell(0);
            cell.setCellValue(richText);
            CellStyle titleCellStyle = this.xworkBook.createCellStyle();
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCellStyle.setFont(this.xtitleFont);
            cell.setCellStyle(titleCellStyle);
            this.xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, this.gridData.getColCount() - 2));
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
        Row row = this.xsheet.getRow(rowNum) == null ? this.xsheet.createRow(rowNum) : this.xsheet.getRow(rowNum);
        Cell cell = row.createCell(colNum);
        try {
            XSSFCellStyle cellStyle = (XSSFCellStyle)this.buildCellStyle(gridCell);
            cell.setCellStyle(cellStyle);
            switch (gridCell.getDataType()) {
                case 0: {
                    String strValue = gridCell.getCellData();
                    if (strValue != null) {
                        cell.setCellValue(new XSSFRichTextString(strValue));
                        break;
                    }
                    cell.setCellValue((String)null);
                    break;
                }
                case 1: 
                case 4: {
                    String strValue = gridCell.getCellData();
                    if (strValue != null) {
                        XSSFRichTextString richTextString = new XSSFRichTextString(strValue);
                        cell.setCellValue(richTextString);
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
                    cell.setCellValue(new XSSFRichTextString(formatValue));
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
                        cell.setCellValue(new XSSFRichTextString(value));
                        break;
                    }
                    double value = Double.parseDouble(cellData);
                    cell.setCellValue(value);
                    break;
                }
                case 9: {
                    String linkInfoStr = gridCell.getCellData();
                    if (linkInfoStr == null || "".equals(linkInfoStr)) break;
                    String[] info = GridCell.parserLinkInformation((String)linkInfoStr);
                    XSSFRichTextString hssfStr = new XSSFRichTextString(info[0]);
                    cell.setCellValue(hssfStr);
                    break;
                }
            }
            for (ICellOperaterEx obj : this.cellOperaters) {
                obj.handle(gridCell, cell);
            }
        }
        catch (Exception e) {
            throw new ExcelWritingException(e);
        }
    }

    private String makeCurFotmat(CurrencyCellProperty curCellProperty) {
        String currencyFormat = "#,##0";
        String colorStr = "";
        boolean isBracketNegative = false;
        currencyFormat = currencyFormat + this.buildDecimal((NumberCellPropertyIntf)curCellProperty);
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

    private Font getCellFont(GridCell gridCell) {
        HSSFColor fontColor;
        Font font = this.cellFontHashMap.get(gridCell);
        if (font != null) {
            return font;
        }
        font = this.xworkBook.createFont();
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

    private CellStyle buildCellStyle(GridCell gridCell) {
        short formatIndex;
        short formatIndex2;
        HSSFColor backColor;
        CellStyle cellStyle = this.cellStyleHashMap.get(new CellStyleKey(gridCell));
        if (cellStyle != null) {
            return cellStyle;
        }
        GridCell tempGridCell = this.gridData.getCellEx(gridCell.getColNum(), gridCell.getRowNum());
        cellStyle = this.xworkBook.createCellStyle();
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
        short leftBorder = this.transBorderStyle(tempGridCell.getLEdgeStyle());
        short topBorder = this.transBorderStyle(tempGridCell.getTEdgeStyle());
        short rightBorder = this.transBorderStyle(tempGridCell.getREdgeStyle());
        short bottomBorder = this.transBorderStyle(tempGridCell.getBEdgeStyle());
        HSSFColor leftBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getLEdgeColor());
        HSSFColor topBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getTEdgeColor());
        HSSFColor righttBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getREdgeColor());
        HSSFColor bottomBorderColor = HSSFHelper.getApproximateEdgeColor(tempGridCell.getBEdgeColor());
        cellStyle.setBorderLeft(BorderStyle.valueOf(leftBorder));
        cellStyle.setBorderTop(BorderStyle.valueOf(topBorder));
        cellStyle.setBorderRight(BorderStyle.valueOf(rightBorder));
        cellStyle.setBorderBottom(BorderStyle.valueOf(bottomBorder));
        leftBorderColor = this.buildBorderColor(leftBorderColor, tempGridCell.getLEdgeColor());
        topBorderColor = this.buildBorderColor(topBorderColor, tempGridCell.getTEdgeColor());
        righttBorderColor = this.buildBorderColor(righttBorderColor, tempGridCell.getREdgeColor());
        bottomBorderColor = this.buildBorderColor(bottomBorderColor, tempGridCell.getBEdgeColor());
        if (leftBorderColor != null) {
            cellStyle.setLeftBorderColor(leftBorderColor.getIndex());
        } else {
            cellStyle.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        }
        if (topBorderColor != null) {
            cellStyle.setTopBorderColor(topBorderColor.getIndex());
        } else {
            cellStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        }
        if (righttBorderColor != null) {
            cellStyle.setRightBorderColor(righttBorderColor.getIndex());
        } else {
            cellStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        }
        if (bottomBorderColor != null) {
            cellStyle.setBottomBorderColor(bottomBorderColor.getIndex());
        } else {
            cellStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        }
        short horzAlign = this.transCellAlign(tempGridCell.getHorzAlign(), true);
        short vertAlign = this.transCellAlign(tempGridCell.getVertAlign(), false);
        cellStyle.setAlignment(HorizontalAlignment.forInt(horzAlign));
        cellStyle.setVerticalAlignment(VerticalAlignment.forInt(vertAlign));
        cellStyle.setWrapText(tempGridCell.getWrapLine());
        cellStyle.setIndention((short)tempGridCell.getIndent());
        XSSFFont font = (XSSFFont)this.getCellFont(tempGridCell);
        cellStyle.setFont(font);
        if (tempGridCell.getDataType() == 0) {
            cellStyle.setDataFormat((short)0);
        }
        if (tempGridCell.getDataType() == 1) {
            formatIndex2 = this.xworkBook.createDataFormat().getFormat("@");
            cellStyle.setDataFormat(formatIndex2);
        }
        if (tempGridCell.getDataType() == 2) {
            String formatStr;
            NumberCellProperty ncp = new NumberCellProperty(tempGridCell);
            if (ncp.getIsPercent()) {
                formatStr = "0" + this.buildDecimal((NumberCellPropertyIntf)ncp) + "%";
                formatIndex = this.xworkBook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            } else if (tempGridCell.getShowText() != null && tempGridCell.getShowText().endsWith("\u2030")) {
                String numStr = "0";
                String colorStr = "";
                numStr = ncp.getThoundsMark() ? "#,##0" + this.buildDecimal((NumberCellPropertyIntf)ncp) : numStr + this.buildDecimal((NumberCellPropertyIntf)ncp);
                if (ncp.getWarningNegative()) {
                    colorStr = "[Red]";
                }
                String formatStr2 = "";
                formatStr2 = ncp.getBracketNegative() ? numStr + ";" + colorStr + "(" + numStr + ")" : numStr + ";" + colorStr + "-" + numStr;
                short formatIndex3 = this.xworkBook.createDataFormat().getFormat(formatStr2);
                cellStyle.setDataFormat(formatIndex3);
            } else {
                formatStr = "0";
                formatStr = ncp.getThoundsMark() ? "#,##0" + this.buildDecimal((NumberCellPropertyIntf)ncp) : formatStr + this.buildDecimal((NumberCellPropertyIntf)ncp);
                formatStr = formatStr + "_ ";
                formatIndex = this.xworkBook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            }
        }
        if (tempGridCell.getDataType() == 3) {
            CurrencyCellProperty curCellProperty = new CurrencyCellProperty(tempGridCell);
            String currencyFormat = this.makeCurFotmat(curCellProperty);
            formatIndex = this.xworkBook.createDataFormat().getFormat(currencyFormat);
            cellStyle.setDataFormat(formatIndex);
        }
        if (gridCell.getDataType() == 5) {
            formatIndex2 = this.xworkBook.createDataFormat().getFormat("yyyy-m-d");
            cellStyle.setDataFormat(formatIndex2);
        }
        if (gridCell.getDataType() == 9) {
            // empty if block
        }
        this.cellStyleHashMap.put(new CellStyleKey(tempGridCell), cellStyle);
        return cellStyle;
    }

    private short transCellAlign(int align, boolean isHorizontal) {
        if (isHorizontal) {
            switch (align) {
                case 0: {
                    return HorizontalAlignment.GENERAL.getCode();
                }
                case 1: {
                    return HorizontalAlignment.LEFT.getCode();
                }
                case 2: {
                    return HorizontalAlignment.RIGHT.getCode();
                }
                case 3: {
                    return HorizontalAlignment.CENTER.getCode();
                }
                case 4: 
                case 5: {
                    return HorizontalAlignment.JUSTIFY.getCode();
                }
                case 6: {
                    return HorizontalAlignment.RIGHT.getCode();
                }
            }
            return HorizontalAlignment.GENERAL.getCode();
        }
        switch (align) {
            case 0: {
                return VerticalAlignment.CENTER.getCode();
            }
            case 1: {
                return VerticalAlignment.TOP.getCode();
            }
            case 2: {
                return VerticalAlignment.BOTTOM.getCode();
            }
            case 3: {
                return VerticalAlignment.CENTER.getCode();
            }
            case 4: 
            case 5: {
                return VerticalAlignment.JUSTIFY.getCode();
            }
            case 6: {
                return VerticalAlignment.BOTTOM.getCode();
            }
        }
        return VerticalAlignment.CENTER.getCode();
    }

    private short transBorderStyle(int idx) {
        switch (idx) {
            case 0: {
                return BorderStyle.THIN.getCode();
            }
            case 1: {
                return BorderStyle.NONE.getCode();
            }
            case 2: {
                return BorderStyle.THIN.getCode();
            }
            case 3: {
                return BorderStyle.DASHED.getCode();
            }
            case 4: {
                return BorderStyle.DOTTED.getCode();
            }
            case 5: {
                return BorderStyle.DASH_DOT.getCode();
            }
            case 6: {
                return BorderStyle.DASH_DOT_DOT.getCode();
            }
            case 7: 
            case 8: 
            case 9: {
                return BorderStyle.THICK.getCode();
            }
            case 10: 
            case 11: 
            case 12: {
                return BorderStyle.DOUBLE.getCode();
            }
            case 13: 
            case 14: 
            case 15: {
                return BorderStyle.THICK.getCode();
            }
        }
        return BorderStyle.THIN.getCode();
    }

    private void mergeCells() {
        GridFieldList fieldList = this.gridData.merges();
        for (int i = 0; i < fieldList.count(); ++i) {
            CellField field = fieldList.get(i);
            int rowFrom = field.top - 1;
            int rowTo = field.bottom - 1;
            int colFrom = field.left - 1;
            int colTo = field.right - 1;
            if (this.addTitle) {
                ++rowFrom;
                ++rowTo;
            }
            this.xsheet.addMergedRegion(new CellRangeAddress(rowFrom, rowTo, colFrom, colTo));
        }
    }

    private void adjustHW() {
        int colCount = this.gridData.getColCount();
        int rowCount = this.gridData.getRowCount();
        if (this.autoAdjust) {
            for (int col = 0; col < colCount; ++col) {
                this.xsheet.autoSizeColumn((short)col);
            }
        } else {
            for (int col = 1; col < colCount; ++col) {
                int colWidth = this.gridData.getColWidths(col);
                this.xsheet.setColumnWidth(col - 1, HSSFHelper.pixelToWidth(colWidth));
            }
            for (int row = 1; row < rowCount; ++row) {
                if (!this.gridData.getRowVisible(row)) continue;
                int rowHeight = this.gridData.getRowHeights(row);
                Row hssfRow = this.addTitle ? this.xsheet.getRow(row) : this.xsheet.getRow(row - 1);
                hssfRow.setHeight(HSSFHelper.pixelToHeight(rowHeight));
            }
        }
    }

    private void hideRowCol() {
        for (int col = 1; col <= this.gridData.getColCount() - 1; ++col) {
            if (this.gridData.getColVisible(col)) continue;
            this.xsheet.setColumnHidden(col - 1, true);
        }
        for (int row = 1; row <= this.gridData.getRowCount() - 1; ++row) {
            if (this.gridData.getRowVisible(row)) continue;
            Row hssfRow = this.addTitle ? this.xsheet.getRow(row) : this.xsheet.getRow(row - 1);
            hssfRow.setZeroHeight(true);
        }
    }

    private HSSFColor buildBorderColor(HSSFColor hssfColor, int borderColor) {
        if (hssfColor != null && HSSFColor.HSSFColorPredefined.WHITE.getIndex() == hssfColor.getIndex() && !Html.getHtmlColor((int)borderColor).equalsIgnoreCase("#ffffff")) {
            return null;
        }
        return hssfColor;
    }
}

