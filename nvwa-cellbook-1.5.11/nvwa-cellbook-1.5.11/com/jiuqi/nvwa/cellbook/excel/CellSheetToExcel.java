/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DataValidation
 *  org.apache.poi.ss.usermodel.DataValidationConstraint
 *  org.apache.poi.ss.usermodel.DataValidationHelper
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.CellRangeAddressList
 *  org.apache.poi.ss.util.CellReference
 *  org.apache.poi.xssf.usermodel.DefaultIndexedColorMap
 *  org.apache.poi.xssf.usermodel.IndexedColorMap
 *  org.apache.poi.xssf.usermodel.XSSFCell
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFDataFormat
 *  org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint
 *  org.apache.poi.xssf.usermodel.XSSFFont
 *  org.apache.poi.xssf.usermodel.XSSFRow
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.nvwa.cellbook.excel;

import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.datatype.CellBookDataTypeFactory;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import com.jiuqi.nvwa.cellbook.model.DropBoxData;
import com.jiuqi.nvwa.cellbook.model.Point;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellSheetToExcel {
    public static final Logger LOGGER = LoggerFactory.getLogger(CellSheetToExcel.class);
    private static final String dropBoxHiddenSheetName = "HIDDENSHEETNAME";
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private CellSheet cellSheet;
    private Map<String, XSSFCellStyle> cacheCellStyle = new HashMap<String, XSSFCellStyle>();
    private Map<String, XSSFFont> cacheFont = new HashMap<String, XSSFFont>();
    private Map<String, SimpleDateFormat> cacheDateFormat = new HashMap<String, SimpleDateFormat>();
    private List<DropBoxData> dropBoxData = null;

    public static XSSFWorkbook createWorkBook() {
        return new XSSFWorkbook();
    }

    public CellSheetToExcel(XSSFWorkbook workBook) {
        this.workbook = workBook;
    }

    public void writeToExcel(CellSheet cellSheet) {
        String title = cellSheet.getTitle();
        this.writeToExcel(cellSheet, title);
    }

    public void writeToExcel(CellSheet cellSheet, String sheetName) {
        if (StringUtils.isEmpty(sheetName)) {
            sheetName = "\u7a7a";
        }
        this.sheet = this.workbook.createSheet(sheetName);
        this.cellSheet = cellSheet;
        this.writeSheet();
    }

    private void writeSheet() {
        List<CellMerge> merges;
        int rowCount = this.cellSheet.getRowCount();
        int columnCount = this.cellSheet.getColumnCount();
        for (int i = 0; i < rowCount; ++i) {
            XSSFRow row = this.sheet.createRow(i);
            int rowHeight = this.cellSheet.getRowHeight(i);
            row.setHeight((short)(rowHeight * 15));
            boolean rowHidden = this.cellSheet.getRowHidden(i);
            if (rowHidden) {
                row.setZeroHeight(true);
            }
            for (int j = 0; j < columnCount; ++j) {
                Cell nvwaCell = this.cellSheet.getCell(i, j);
                XSSFCell cell = row.createCell(j);
                this.writeCell(nvwaCell, cell);
            }
        }
        if (this.dropBoxData != null && this.dropBoxData.size() > 0) {
            for (DropBoxData dropBox : this.dropBoxData) {
                this.setDropDownBox(this.workbook, dropBox.getValues(), dropBox.getRowNum(), dropBox.getColNum(), dropBox.getEnueName(), rowCount, columnCount, dropBox.isByCol());
            }
            String hiddenSheetName = dropBoxHiddenSheetName;
            XSSFSheet hiddenSheet = this.workbook.getSheet(hiddenSheetName);
            if (hiddenSheet != null) {
                this.workbook.setSheetOrder(hiddenSheetName, this.workbook.getNumberOfSheets() - 1);
            }
        }
        if (null != (merges = this.cellSheet.getMerges()) && merges.size() > 0) {
            for (CellMerge cellMerge : merges) {
                int firstRow = cellMerge.getRowIndex();
                int lastRow = cellMerge.getRowIndex() + cellMerge.getRowSpan() - 1;
                int firstCol = cellMerge.getColumnIndex();
                int lastCol = cellMerge.getColumnIndex() + cellMerge.getColumnSpan() - 1;
                try {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
                    this.sheet.addMergedRegion(cellRangeAddress);
                }
                catch (Exception e) {
                    LOGGER.error("\u975e\u6cd5\u7684\u5408\u5e76\u5355\u5143\u683c\uff01", (Object)cellMerge, (Object)e);
                }
            }
        }
        for (int col = 0; col < columnCount; ++col) {
            boolean colAutoWide = this.cellSheet.getColAutoWide(col);
            if (colAutoWide) {
                this.sheet.autoSizeColumn(col);
            } else {
                int colWidth = this.cellSheet.getColWide(col);
                this.sheet.setColumnWidth(col, colWidth * 32);
            }
            boolean colHidden = this.cellSheet.getColHidden(col);
            if (!colHidden) continue;
            this.sheet.setColumnHidden(col, true);
        }
    }

    private void writeCell(Cell nvwaCell, XSSFCell cell) {
        CommonCellDataType excelType = nvwaCell.getCommonDataType();
        boolean merged = nvwaCell.isMerged();
        Point mergeInfo = nvwaCell.getMergeInfo();
        if (!merged || null != mergeInfo && mergeInfo.getX() == nvwaCell.getRowIndex() && mergeInfo.getY() == nvwaCell.getColIndex()) {
            String formula;
            block51: {
                String value;
                boolean isAuto = false;
                String dataTypeId = nvwaCell.getDataTypeId();
                if (StringUtils.isNotEmpty(dataTypeId)) {
                    if ("auto".equals(dataTypeId)) {
                        isAuto = true;
                    } else if (CellBookDataTypeFactory.isOldBaseType(dataTypeId)) {
                        CommonCellDataType oldBaseType = CellBookDataTypeFactory.getOldBaseType(dataTypeId);
                        if (null != oldBaseType) {
                            excelType = oldBaseType;
                        } else if (null == excelType && "6".equals(dataTypeId)) {
                            isAuto = true;
                        }
                    }
                }
                if (StringUtils.isEmpty(value = nvwaCell.getValue())) {
                    value = nvwaCell.getShowText();
                }
                if (StringUtils.isNotEmpty(value) || null != excelType) {
                    if (CommonCellDataType.STRING == excelType) {
                        cell.setCellValue(value);
                    } else if (CommonCellDataType.NUMBER == excelType) {
                        if (!StringUtils.isEmpty(value)) {
                            try {
                                double parseDouble = Double.parseDouble(value);
                                cell.setCellValue(parseDouble);
                                cell.setCellType(CellType.NUMERIC);
                            }
                            catch (NumberFormatException e) {
                                LOGGER.error("excel\u5bfc\u51fa\uff0c\u6570\u503c\u7c7b\u578b\u5355\u5143\u683c\uff0c\u8bbe\u7f6e\u7684\u503c\u5374\u4e0d\u662f\u6570\u503c\uff01value:" + value + ";row:" + nvwaCell.getRowIndex() + ";col:" + nvwaCell.getColIndex(), e);
                                cell.setCellValue(value);
                            }
                        }
                    } else if (CommonCellDataType.BOOLEAN == excelType) {
                        if ("true".equalsIgnoreCase(value)) {
                            cell.setCellValue(true);
                            cell.setCellType(CellType.BOOLEAN);
                        } else if ("false".equalsIgnoreCase(value)) {
                            cell.setCellValue(false);
                            cell.setCellType(CellType.BOOLEAN);
                        } else if (!StringUtils.isEmpty(value)) {
                            LOGGER.error("excel\u5bfc\u51fa\uff0c\u5e03\u5c14\u7c7b\u578b\u5355\u5143\u683c\uff0c\u8bbe\u7f6e\u7684\u503c\u5374\u4e0d\u662f\u5e03\u5c14\uff01value:" + value + ";row:" + nvwaCell.getRowIndex() + ";col:" + nvwaCell.getColIndex());
                            cell.setCellValue(value);
                        }
                    } else if (CommonCellDataType.DATE == excelType) {
                        if (!StringUtils.isEmpty(value)) {
                            String formatter = nvwaCell.getFormatter();
                            if (!StringUtils.isEmpty(formatter)) {
                                try {
                                    SimpleDateFormat simpleDateFormat = this.cacheDateFormat.get(formatter);
                                    if (null == simpleDateFormat) {
                                        simpleDateFormat = new SimpleDateFormat(formatter);
                                        this.cacheDateFormat.put(formatter, simpleDateFormat);
                                    }
                                    Date parse = simpleDateFormat.parse(value);
                                    cell.setCellValue(parse);
                                    cell.setCellType(CellType.NUMERIC);
                                }
                                catch (ParseException e) {
                                    LOGGER.error("excel\u5bfc\u51fa\uff0c\u65e5\u671f\u7c7b\u578b\u5355\u5143\u683c\uff0c\u503c\u548c\u683c\u5f0f\u5316\u4e0d\u7b26\u5408\uff01value:" + value + ";formatter:" + formatter + ";row:" + nvwaCell.getRowIndex() + ";col:" + nvwaCell.getColIndex(), e);
                                    cell.setCellValue(value);
                                }
                            } else {
                                LOGGER.error("excel\u5bfc\u51fa\uff0c\u65e5\u671f\u7c7b\u578b\u5355\u5143\u683c\uff0c\u65e0\u683c\u5f0f\u5316\u516c\u5f0f\uff01value:" + value + ";row:" + nvwaCell.getRowIndex() + ";col:" + nvwaCell.getColIndex());
                                cell.setCellValue(value);
                            }
                        }
                    } else if (CommonCellDataType.ERROR == excelType) {
                        cell.setBlank();
                    } else if (isAuto) {
                        if (!StringUtils.isEmpty(value)) {
                            if ("true".equalsIgnoreCase(value)) {
                                cell.setCellValue(true);
                                cell.setCellType(CellType.BOOLEAN);
                            } else if ("false".equalsIgnoreCase(value)) {
                                cell.setCellValue(false);
                                cell.setCellType(CellType.BOOLEAN);
                            } else {
                                try {
                                    double parseDouble = Double.parseDouble(value);
                                    cell.setCellValue(parseDouble);
                                    cell.setCellType(CellType.NUMERIC);
                                }
                                catch (Exception e) {
                                    String formatter = nvwaCell.getFormatter();
                                    if (!StringUtils.isEmpty(formatter)) {
                                        try {
                                            SimpleDateFormat simpleDateFormat = this.cacheDateFormat.get(formatter);
                                            if (null == simpleDateFormat) {
                                                simpleDateFormat = new SimpleDateFormat(formatter);
                                                this.cacheDateFormat.put(formatter, simpleDateFormat);
                                            }
                                            Date parse = simpleDateFormat.parse(value);
                                            cell.setCellValue(parse);
                                            cell.setCellType(CellType.NUMERIC);
                                        }
                                        catch (Exception e1) {
                                            cell.setCellValue(value);
                                        }
                                        break block51;
                                    }
                                    cell.setCellValue(value);
                                }
                            }
                        }
                    } else {
                        cell.setCellValue(value);
                    }
                }
            }
            if (StringUtils.isNotEmpty(formula = nvwaCell.getFormula())) {
                try {
                    int indexOf = formula.indexOf("=");
                    if (indexOf == 0) {
                        formula = formula.substring(1, formula.length());
                    }
                    cell.setCellFormula(formula);
                    cell.setCellType(CellType.FORMULA);
                }
                catch (Exception e) {
                    LOGGER.error("excel\u5bfc\u51fa\uff0c\u516c\u5f0f\u4e0d\u5408\u6cd5\uff1a" + formula, e);
                }
            }
        }
        XSSFCellStyle cellStyle = this.getCellStyle(nvwaCell);
        cell.setCellStyle((org.apache.poi.ss.usermodel.CellStyle)cellStyle);
    }

    private XSSFCellStyle getCellStyle(Cell nvwaCell) {
        XSSFCellStyle cellStyle;
        CellStyle nvwaCellStyle = nvwaCell.getCellStyle();
        int hashCode = nvwaCellStyle.hashCode();
        String styleKey = hashCode + "";
        int topStyleCode = nvwaCell.getTopBorderStyle().getCode();
        String topBorderColorIndex = "";
        styleKey = styleKey + topStyleCode;
        topBorderColorIndex = nvwaCell.getTopBorderColorHex();
        if (StringUtils.isNotEmpty(topBorderColorIndex)) {
            styleKey = styleKey + topBorderColorIndex;
        }
        styleKey = styleKey + "_";
        int leftStyleCode = nvwaCell.getLeftBorderStyle().getCode();
        String leftBorderColorIndex = "";
        styleKey = styleKey + leftStyleCode;
        leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
        if (StringUtils.isNotEmpty(leftBorderColorIndex)) {
            styleKey = styleKey + leftBorderColorIndex;
        }
        styleKey = styleKey + "_";
        int rightStyleCode = nvwaCell.getRightBorderStyle().getCode();
        String rightBorderColorIndex = "";
        styleKey = styleKey + rightStyleCode;
        rightBorderColorIndex = nvwaCell.getRightBorderColorHex();
        if (StringUtils.isNotEmpty(rightBorderColorIndex)) {
            styleKey = styleKey + rightBorderColorIndex;
        }
        styleKey = styleKey + "_";
        int bottomStyleCode = nvwaCell.getBottomBorderStyle().getCode();
        String bottomBorderColorIndex = "";
        styleKey = styleKey + bottomStyleCode;
        bottomBorderColorIndex = nvwaCell.getBottomBorderColorHex();
        if (StringUtils.isNotEmpty(bottomBorderColorIndex)) {
            styleKey = styleKey + bottomBorderColorIndex;
        }
        if (null == (cellStyle = this.cacheCellStyle.get(styleKey = styleKey + "_"))) {
            String fontCacheKey;
            XSSFFont font;
            cellStyle = this.workbook.createCellStyle();
            if (nvwaCellStyle.isWrapLine()) {
                cellStyle.setWrapText(true);
            }
            if (nvwaCellStyle.isFitFontSize()) {
                cellStyle.setShrinkToFit(true);
            }
            String backGroundColor = nvwaCellStyle.getBackGroundColor();
            FillPatternType fillPatternType = nvwaCellStyle.getFillPatternType();
            if (!StringUtils.isEmpty(backGroundColor) && FillPatternType.NO_FILL != fillPatternType) {
                cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.forInt((int)fillPatternType.getCode()));
                cellStyle.setFillForegroundColor(this.createExcelColor(backGroundColor, true));
            }
            if (null == (font = this.cacheFont.get(fontCacheKey = nvwaCellStyle.getFontName() + nvwaCellStyle.getFontSizeF() + nvwaCellStyle.getFontColor() + nvwaCellStyle.isBold() + nvwaCellStyle.isItalic() + nvwaCellStyle.isUnderline() + nvwaCellStyle.isInline()))) {
                String fontColor = nvwaCellStyle.getFontColor();
                font = this.workbook.createFont();
                font.setColor(this.createExcelColor(fontColor));
                font.setFontName(nvwaCellStyle.getFontName());
                font.setFontHeightInPoints((short)(nvwaCellStyle.getFontSizeF() * 72.0f / 96.0f));
                if (nvwaCellStyle.isBold()) {
                    font.setBold(true);
                }
                if (nvwaCellStyle.isInline()) {
                    font.setStrikeout(true);
                }
                if (nvwaCellStyle.isItalic()) {
                    font.setItalic(true);
                }
                if (nvwaCellStyle.isUnderline()) {
                    font.setUnderline((byte)1);
                }
                this.cacheFont.put(fontCacheKey, font);
            }
            cellStyle.setFont((Font)font);
            HorizontalAlignment horizontalAlignment = nvwaCellStyle.getHorizontalAlignment();
            cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.forInt((int)horizontalAlignment.getCode()));
            com.jiuqi.nvwa.cellbook.constant.VerticalAlignment verticalAlignment = nvwaCellStyle.getVerticalAlignment();
            if (com.jiuqi.nvwa.cellbook.constant.VerticalAlignment.AUTO == verticalAlignment) {
                verticalAlignment = com.jiuqi.nvwa.cellbook.constant.VerticalAlignment.CENTER;
            }
            cellStyle.setVerticalAlignment(VerticalAlignment.forInt((int)(verticalAlignment.getCode() - 1)));
            BorderStyle borderTop = BorderStyle.valueOf((short)((short)topStyleCode));
            BorderStyle borderLeft = BorderStyle.valueOf((short)((short)leftStyleCode));
            BorderStyle borderRight = BorderStyle.valueOf((short)((short)rightStyleCode));
            BorderStyle borderBottom = BorderStyle.valueOf((short)((short)bottomStyleCode));
            if (borderTop == BorderStyle.NONE) {
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setTopBorderColor(this.createExcelColor("ffffff"));
            } else {
                cellStyle.setBorderTop(borderTop);
                if (StringUtils.isNotEmpty(topBorderColorIndex)) {
                    cellStyle.setTopBorderColor(this.createExcelColor(topBorderColorIndex));
                }
            }
            if (borderLeft == BorderStyle.NONE) {
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setLeftBorderColor(this.createExcelColor("ffffff"));
            } else {
                cellStyle.setBorderLeft(borderLeft);
                if (StringUtils.isNotEmpty(leftBorderColorIndex)) {
                    cellStyle.setLeftBorderColor(this.createExcelColor(leftBorderColorIndex));
                }
            }
            if (borderRight == BorderStyle.NONE) {
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setRightBorderColor(this.createExcelColor("ffffff"));
            } else {
                cellStyle.setBorderRight(borderRight);
                if (StringUtils.isNotEmpty(rightBorderColorIndex)) {
                    cellStyle.setRightBorderColor(this.createExcelColor(rightBorderColorIndex));
                }
            }
            if (borderBottom == BorderStyle.NONE) {
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBottomBorderColor(this.createExcelColor("ffffff"));
            } else {
                cellStyle.setBorderBottom(borderBottom);
                if (StringUtils.isNotEmpty(bottomBorderColorIndex)) {
                    cellStyle.setBottomBorderColor(this.createExcelColor(bottomBorderColorIndex));
                }
            }
            String formatter = nvwaCell.getFormatter();
            if (null != formatter && formatter.length() != 0) {
                XSSFDataFormat format = this.workbook.createDataFormat();
                cellStyle.setDataFormat(format.getFormat(formatter));
            }
            this.cacheCellStyle.put(styleKey, cellStyle);
        }
        return cellStyle;
    }

    private XSSFColor createExcelColor(String hexString) {
        return this.createExcelColor(hexString, false);
    }

    private XSSFColor createExcelColor(String hexString, boolean isBackGround) {
        int alpha;
        double tint;
        String tintHex = "";
        if (hexString.length() < 6) {
            int count = 6 - hexString.length();
            String temp = "";
            for (int i = 0; i < count; ++i) {
                temp = temp + "0";
            }
            hexString = temp + hexString;
        } else if (hexString.length() > 6) {
            if (isBackGround) {
                tintHex = hexString.substring(6, hexString.length());
            }
            hexString = hexString.substring(0, 6);
        }
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        XSSFColor clr = new XSSFColor((IndexedColorMap)defaultIndexedColorMap);
        clr.setARGBHex(hexString);
        if (isBackGround && !StringUtils.isEmpty(tintHex) && (tint = (double)(alpha = Integer.parseInt(tintHex, 16) ^ 0xFF) / 255.0) < 1.0) {
            clr.setTint(tint);
        }
        return clr;
    }

    public List<DropBoxData> getDropBoxData() {
        return this.dropBoxData;
    }

    public void setDropBoxData(List<DropBoxData> dropBoxData) {
        this.dropBoxData = dropBoxData;
    }

    private void setDropDownBox(XSSFWorkbook wb, List<String> values, Integer rowNum, Integer colNum, String enueName, int rowCount, int colCount, boolean byCol) {
        int sheetTotal = wb.getNumberOfSheets();
        if (values != null && values.size() != 0) {
            String colNumString;
            String hiddenSheetName = dropBoxHiddenSheetName;
            XSSFSheet hiddenSheet = wb.getSheet(hiddenSheetName);
            if (hiddenSheet == null) {
                hiddenSheet = wb.createSheet(hiddenSheetName);
                wb.setSheetHidden(sheetTotal, true);
            }
            int duplicateColumnNum = -1;
            int lastCellNumOfFirstRow = -1;
            if (-1 != hiddenSheet.getFirstRowNum()) {
                XSSFRow firstRow = hiddenSheet.getRow(0);
                lastCellNumOfFirstRow = firstRow.getLastCellNum();
                for (int i = 0; i < lastCellNumOfFirstRow; ++i) {
                    XSSFCell enueNameCell = firstRow.getCell(i);
                    if (!enueName.equals(enueNameCell.getStringCellValue())) continue;
                    duplicateColumnNum = i;
                    break;
                }
            }
            String strFormula = "";
            if (duplicateColumnNum == -1) {
                int i;
                XSSFCell cell;
                XSSFRow row;
                if (-1 == hiddenSheet.getFirstRowNum()) {
                    row = hiddenSheet.createRow(0);
                    cell = row.createCell(0);
                    lastCellNumOfFirstRow = 0;
                    cell.setCellValue(enueName);
                    for (i = 0; i < values.size(); ++i) {
                        row = hiddenSheet.createRow(i + 1);
                        cell = row.createCell(0);
                        cell.setCellValue(values.get(i));
                    }
                } else {
                    row = hiddenSheet.getRow(0);
                    cell = row.createCell(lastCellNumOfFirstRow);
                    cell.setCellValue(enueName);
                    for (i = 0; i < values.size(); ++i) {
                        row = null != hiddenSheet.getRow(i + 1) ? hiddenSheet.getRow(i + 1) : hiddenSheet.createRow(i + 1);
                        cell = row.createCell(lastCellNumOfFirstRow);
                        cell.setCellValue(values.get(i));
                    }
                }
                colNumString = CellReference.convertNumToColString((int)lastCellNumOfFirstRow);
                strFormula = hiddenSheetName + "!$" + colNumString + "$2:$" + colNumString + "$" + (values.size() + 1);
            } else {
                colNumString = CellReference.convertNumToColString((int)duplicateColumnNum);
                strFormula = hiddenSheetName + "!$" + colNumString + "$2:$" + colNumString + "$" + (values.size() + 1);
            }
            XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(3, strFormula);
            CellRangeAddressList regions = null;
            regions = byCol ? new CellRangeAddressList(rowNum.intValue(), rowCount - 1, colNum.intValue(), colNum.intValue()) : new CellRangeAddressList(rowNum.intValue(), rowNum.intValue(), colNum.intValue(), colCount - 1);
            DataValidationHelper help = hiddenSheet.getDataValidationHelper();
            DataValidation validation = help.createValidation((DataValidationConstraint)constraint, regions);
            this.sheet.addValidationData(validation);
        }
    }
}

