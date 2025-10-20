/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFFont
 *  org.apache.poi.hssf.usermodel.HSSFPalette
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DataFormat
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.PaneInformation
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 */
package com.jiuqi.nvwa.cellbook.excel;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.excel.IExcelToCellSheetProvider;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellSheetGroup;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PaneInformation;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExcelToCellSheet {
    private final String BORDER_AUTO_COLOR = "000000";
    private Workbook workbook;
    private IExcelToCellSheetProvider provider;

    public ExcelToCellSheet(Workbook workBook) {
        this.workbook = workBook;
    }

    public ExcelToCellSheet(Workbook workBook, IExcelToCellSheetProvider provider) {
        this.workbook = workBook;
        this.provider = provider;
    }

    public CellSheet addCellSheet(String sheetName, CellBook cellBook) {
        Sheet sheet = this.workbook.getSheet(sheetName);
        return this.addCellSheet(sheet, null, cellBook);
    }

    public CellSheet addCellSheet(int sheetIndex, CellBook cellBook) {
        Sheet sheet = this.workbook.getSheetAt(sheetIndex);
        return this.addCellSheet(sheet, null, cellBook);
    }

    public CellSheet addCellSheet(String sheetName, CellSheetGroup cellSheetGroup) {
        Sheet sheet = this.workbook.getSheet(sheetName);
        return this.addCellSheet(sheet, cellSheetGroup, null);
    }

    public CellSheet addCellSheet(int sheetIndex, CellSheetGroup cellSheetGroup) {
        Sheet sheet = this.workbook.getSheetAt(sheetIndex);
        return this.addCellSheet(sheet, cellSheetGroup, null);
    }

    private CellSheet addCellSheet(Sheet sheet, CellSheetGroup cellSheetGroup, CellBook cellBook) {
        int i;
        int i2;
        DataFormat dataFormat = this.workbook.createDataFormat();
        int lastRowNum = sheet.getLastRowNum();
        int rowsNum = lastRowNum + 1;
        int lastCol = 0;
        for (Row row : sheet) {
            if (row.getLastCellNum() <= lastCol) continue;
            lastCol = row.getLastCellNum();
        }
        CellSheet cellSheet = null;
        if (null != cellSheetGroup) {
            cellBook = cellSheetGroup.getCellBook();
            cellSheet = cellSheetGroup.createSheet(sheet.getSheetName(), sheet.getSheetName(), rowsNum, lastCol);
        } else {
            cellSheet = cellBook.createSheet(sheet.getSheetName(), sheet.getSheetName(), rowsNum, lastCol);
        }
        for (i2 = 0; i2 <= lastRowNum; ++i2) {
            Row row = sheet.getRow(i2);
            if (row == null) continue;
            Iterator it = row.cellIterator();
            while (it.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = (org.apache.poi.ss.usermodel.Cell)it.next();
                int columnIndex = cell.getColumnIndex();
                Cell nvwaCell = cellSheet.getCell(i2, columnIndex);
                this.readCellStyle(cell, nvwaCell, cellBook.getBookStyle().getDefaultStyle());
                this.readCellData(cell, nvwaCell, dataFormat);
            }
        }
        for (i2 = 0; i2 < sheet.getNumMergedRegions(); ++i2) {
            CellRangeAddress region = sheet.getMergedRegion(i2);
            int rowFrom = region.getFirstRow();
            int rowTo = region.getLastRow();
            int colFrom = region.getFirstColumn();
            int colTo = region.getLastColumn();
            cellSheet.mergeCells(rowFrom, colFrom, rowTo - rowFrom + 1, colTo - colFrom + 1);
        }
        PaneInformation pim = sheet.getPaneInformation();
        if (null != pim) {
            cellSheet.setHeaderColCount(pim.getVerticalSplitPosition());
            cellSheet.setHeaderRowCount(pim.getHorizontalSplitPosition());
        }
        for (i = 0; i <= lastRowNum; ++i) {
            Row row = sheet.getRow(i);
            if (row != null) {
                cellSheet.setRowHidden(i, row.getZeroHeight());
            }
            if (row == null) continue;
            cellSheet.setRowHeight(i, row.getHeight() / 15);
        }
        for (i = 0; i < lastCol; ++i) {
            cellSheet.setColWide(i, (short)sheet.getColumnWidth(i) / 32);
            cellSheet.setColHidden(i, sheet.isColumnHidden(i));
        }
        return cellSheet;
    }

    private boolean isInteger(double d) {
        try {
            return d == (double)((long)d);
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private void readCellData(org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell, DataFormat dataFormat) {
        CellType cellType = cell.getCellType();
        if (cellType != CellType.BLANK) {
            if (cellType == CellType.NUMERIC) {
                double numericCellValue = cell.getNumericCellValue();
                String dataFormatString = dataFormat.getFormat(cell.getCellStyle().getDataFormat());
                if (DateUtil.isCellDateFormatted((org.apache.poi.ss.usermodel.Cell)cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormatString);
                    Date date = DateUtil.getJavaDate((double)numericCellValue);
                    nvwaCell.setValue(dateFormat.format(date));
                    nvwaCell.setFormatter(dataFormatString);
                    nvwaCell.setCommonDataType(CommonCellDataType.DATE);
                } else {
                    if ("General".equals(dataFormatString)) {
                        nvwaCell.setFormatter("");
                        if (this.isInteger(numericCellValue)) {
                            nvwaCell.setValue((long)numericCellValue + "");
                        } else {
                            nvwaCell.setValue(numericCellValue + "");
                        }
                    } else {
                        int decimal = 0;
                        String[] split = dataFormatString.split("_\\);");
                        String numberFormat = split[0];
                        if (numberFormat.indexOf(".") > -1) {
                            String zoreStr = numberFormat.split("\\.")[1];
                            decimal = zoreStr.length();
                        }
                        if (decimal == 0 && this.isInteger(numericCellValue)) {
                            nvwaCell.setValue((long)numericCellValue + "");
                        } else {
                            nvwaCell.setValue(numericCellValue + "");
                        }
                        nvwaCell.setFormatter(numberFormat);
                    }
                    nvwaCell.setCommonDataType(CommonCellDataType.NUMBER);
                }
            } else if (cellType == CellType.STRING) {
                nvwaCell.setValue(cell.getStringCellValue());
                nvwaCell.setCommonDataType(CommonCellDataType.STRING);
            } else if (cellType == CellType.FORMULA) {
                nvwaCell.setFormula(cell.getCellFormula());
            } else if (cellType == CellType.BOOLEAN) {
                nvwaCell.setValue(cell.getBooleanCellValue() + "");
                nvwaCell.setCommonDataType(CommonCellDataType.BOOLEAN);
            }
        }
        if (null != this.provider) {
            this.provider.readCellDataAfter(cell, nvwaCell);
        }
    }

    private void readCellStyle(org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell, CellStyle defaultStyle) {
        XSSFFont font = null;
        org.apache.poi.ss.usermodel.CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle instanceof HSSFCellStyle) {
            CellBorderStyle borderBottom;
            short[] rgb;
            CellBorderStyle borderTop;
            CellBorderStyle topBorderStyle;
            CellBorderStyle borderRight;
            CellBorderStyle borderLeft;
            CellBorderStyle leftBorderStyle;
            short[] triplet;
            short s;
            HSSFColor hssfColor;
            HSSFCellStyle hCellStyle = (HSSFCellStyle)cellStyle;
            HSSFPalette palette = ((HSSFWorkbook)this.workbook).getCustomPalette();
            HSSFFont hfont = hCellStyle.getFont(this.workbook);
            font = hfont;
            HSSFColor hc = palette.getColor(hfont.getColor());
            if (null != hc) {
                int n = ExcelToCellSheet.formatRGB(hc.getTriplet());
                nvwaCell.setFontColor(new CellColor(n));
            }
            if ((hssfColor = palette.getColor(s = hCellStyle.getFillForegroundColor())) != null && ((triplet = hssfColor.getTriplet())[0] != 0 || triplet[1] != 0 || triplet[2] != 0)) {
                int rgbB = ExcelToCellSheet.formatRGB(triplet);
                nvwaCell.setBackGroundColor(new CellColor(rgbB));
            }
            if ((CellBorderStyle.NONE == (leftBorderStyle = nvwaCell.getLeftBorderStyle()) || leftBorderStyle == defaultStyle.getLeftBorderStyle()) && CellBorderStyle.NONE != (borderLeft = CellBorderStyle.forInt(hCellStyle.getBorderLeft().getCode()))) {
                nvwaCell.setLeftBorderStyle(borderLeft);
                HSSFColor leftBorderXSSFColor = palette.getColor(hCellStyle.getLeftBorderColor());
                if (null != leftBorderXSSFColor) {
                    String leftBorderColorIndex;
                    short[] rgb2 = leftBorderXSSFColor.getTriplet();
                    if (null != rgb2) {
                        leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
                        if (StringUtils.isEmpty(leftBorderColorIndex) || leftBorderColorIndex.equals(defaultStyle.getLeftBorderColor())) {
                            nvwaCell.setLeftBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb2)));
                        }
                    } else {
                        leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
                        if (StringUtils.isEmpty(leftBorderColorIndex) || leftBorderColorIndex.equals(defaultStyle.getLeftBorderColor())) {
                            nvwaCell.setLeftBorderColor(new CellColor("000000"));
                        }
                    }
                } else {
                    String leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
                    if (StringUtils.isEmpty(leftBorderColorIndex) || leftBorderColorIndex.equals(defaultStyle.getLeftBorderColor())) {
                        nvwaCell.setLeftBorderColor(new CellColor("000000"));
                    }
                }
            }
            if (CellBorderStyle.NONE != (borderRight = CellBorderStyle.forInt(hCellStyle.getBorderRight().getCode()))) {
                nvwaCell.setRightBorderStyle(borderRight);
                HSSFColor rightBorderXSSFColor = palette.getColor(hCellStyle.getRightBorderColor());
                if (null != rightBorderXSSFColor) {
                    short[] rgb2 = rightBorderXSSFColor.getTriplet();
                    if (null != rgb2) {
                        nvwaCell.setRightBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb2)));
                    } else {
                        nvwaCell.setRightBorderColor(new CellColor("000000"));
                    }
                } else {
                    nvwaCell.setRightBorderColor(new CellColor("000000"));
                }
            }
            if ((CellBorderStyle.NONE == (topBorderStyle = nvwaCell.getTopBorderStyle()) || topBorderStyle == defaultStyle.getTopBorderStyle()) && CellBorderStyle.NONE != (borderTop = CellBorderStyle.forInt(hCellStyle.getBorderTop().getCode()))) {
                nvwaCell.setTopBorderStyle(borderTop);
                HSSFColor topBorderXSSFColor = palette.getColor(hCellStyle.getTopBorderColor());
                if (null != topBorderXSSFColor) {
                    rgb = topBorderXSSFColor.getTriplet();
                    if (null != rgb) {
                        String topBorderColorIndex = nvwaCell.getTopBorderColorHex();
                        if (StringUtils.isEmpty(topBorderColorIndex) || topBorderColorIndex.equals(defaultStyle.getTopBorderColor())) {
                            nvwaCell.setTopBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb)));
                        }
                    } else {
                        String topBorderColorIndex = nvwaCell.getTopBorderColorHex();
                        if (StringUtils.isEmpty(topBorderColorIndex) || topBorderColorIndex.equals(defaultStyle.getTopBorderColor())) {
                            nvwaCell.setTopBorderColor(new CellColor("000000"));
                        }
                    }
                } else {
                    String topBorderColorIndex = nvwaCell.getTopBorderColorHex();
                    if (StringUtils.isEmpty(topBorderColorIndex) || topBorderColorIndex.equals(defaultStyle.getTopBorderColor())) {
                        nvwaCell.setTopBorderColor(new CellColor("000000"));
                    }
                }
            }
            if (CellBorderStyle.NONE != (borderBottom = CellBorderStyle.forInt(hCellStyle.getBorderBottom().getCode()))) {
                nvwaCell.setBottomBorderStyle(borderBottom);
                HSSFColor bottomBorderXSSFColor = palette.getColor(hCellStyle.getBottomBorderColor());
                if (null != bottomBorderXSSFColor) {
                    rgb = bottomBorderXSSFColor.getTriplet();
                    if (null != rgb) {
                        nvwaCell.setBottomBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb)));
                    } else {
                        nvwaCell.setBottomBorderColor(new CellColor("000000"));
                    }
                } else {
                    nvwaCell.setBottomBorderColor(new CellColor("000000"));
                }
            }
        } else if (cellStyle instanceof XSSFCellStyle) {
            CellBorderStyle borderBottom;
            CellBorderStyle borderTop;
            CellBorderStyle topBorderStyle;
            CellBorderStyle borderRight;
            byte[] rgb;
            CellBorderStyle borderLeft;
            CellBorderStyle cellBorderStyle;
            XSSFColor fillForegroundXSSFColor;
            XSSFFont xfont;
            XSSFCellStyle xCellStyle = (XSSFCellStyle)cellStyle;
            font = xfont = xCellStyle.getFont();
            XSSFColor xssfColor = xfont.getXSSFColor();
            if (null != xssfColor && xssfColor.getRGB() != null) {
                int color = ExcelToCellSheet.formatRGB(xssfColor.getRGB());
                nvwaCell.setFontColor(new CellColor(color));
            }
            if (null != (fillForegroundXSSFColor = xCellStyle.getFillForegroundXSSFColor()) && fillForegroundXSSFColor.getRGB() != null) {
                double d = fillForegroundXSSFColor.getTint();
                if (d < 0.0) {
                    byte[] rgbs = fillForegroundXSSFColor.getRGB();
                    d = Math.max(0.0, Math.min(1.0, Math.abs(d)));
                    byte[] newRgb = new byte[3];
                    for (int i = 0; i < 3; ++i) {
                        int value = rgbs[i] & 0xFF;
                        int newValue = (int)((double)value * (1.0 - d));
                        newRgb[i] = (byte)Math.max(0, Math.min(255, newValue));
                    }
                    int rgb3 = ExcelToCellSheet.formatRGB(newRgb);
                    nvwaCell.setBackGroundColor(new CellColor(rgb3));
                } else if (d > 0.0) {
                    int alpha = (int)(255.0 * d);
                    byte[] rgbBytes = fillForegroundXSSFColor.getRGB();
                    int foregroundR = Byte.toUnsignedInt(rgbBytes[0]);
                    int foregroundG = Byte.toUnsignedInt(rgbBytes[1]);
                    int foregroundB = Byte.toUnsignedInt(rgbBytes[2]);
                    int backgroundR = 255;
                    int backgroundG = 255;
                    int backgroundB = 255;
                    int[] blendColorsWithTransparency = ExcelToCellSheet.blendColorsWithTransparency(foregroundR, foregroundG, foregroundB, alpha, backgroundR, backgroundG, backgroundB);
                    nvwaCell.setBackGroundColor(new CellColor(ExcelToCellSheet.formatRGB(blendColorsWithTransparency)));
                } else {
                    nvwaCell.setBackGroundColor(new CellColor(ExcelToCellSheet.formatRGB(fillForegroundXSSFColor.getRGB())));
                }
            }
            if ((CellBorderStyle.NONE == (cellBorderStyle = nvwaCell.getLeftBorderStyle()) || cellBorderStyle == defaultStyle.getLeftBorderStyle()) && CellBorderStyle.NONE != (borderLeft = CellBorderStyle.forInt(cellStyle.getBorderLeft().getCode()))) {
                nvwaCell.setLeftBorderStyle(borderLeft);
                XSSFColor leftBorderXSSFColor = xCellStyle.getLeftBorderXSSFColor();
                if (null != leftBorderXSSFColor) {
                    String leftBorderColorIndex;
                    rgb = leftBorderXSSFColor.getRGB();
                    if (null != rgb) {
                        leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
                        if (StringUtils.isEmpty(leftBorderColorIndex) || leftBorderColorIndex.equals(defaultStyle.getLeftBorderColor())) {
                            nvwaCell.setLeftBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb)));
                        }
                    } else {
                        leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
                        if (StringUtils.isEmpty(leftBorderColorIndex) || leftBorderColorIndex.equals(defaultStyle.getLeftBorderColor())) {
                            nvwaCell.setLeftBorderColor(new CellColor("000000"));
                        }
                    }
                } else {
                    String leftBorderColorIndex = nvwaCell.getLeftBorderColorHex();
                    if (StringUtils.isEmpty(leftBorderColorIndex) || leftBorderColorIndex.equals(defaultStyle.getLeftBorderColor())) {
                        nvwaCell.setLeftBorderColor(new CellColor("000000"));
                    }
                }
            }
            if (CellBorderStyle.NONE != (borderRight = CellBorderStyle.forInt(cellStyle.getBorderRight().getCode()))) {
                nvwaCell.setRightBorderStyle(borderRight);
                XSSFColor rightBorderXSSFColor = xCellStyle.getRightBorderXSSFColor();
                if (null != rightBorderXSSFColor) {
                    rgb = rightBorderXSSFColor.getRGB();
                    if (null != rgb) {
                        nvwaCell.setRightBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb)));
                    } else {
                        nvwaCell.setRightBorderColor(new CellColor("000000"));
                    }
                } else {
                    nvwaCell.setRightBorderColor(new CellColor("000000"));
                }
            }
            if ((CellBorderStyle.NONE == (topBorderStyle = nvwaCell.getTopBorderStyle()) || topBorderStyle == defaultStyle.getTopBorderStyle()) && CellBorderStyle.NONE != (borderTop = CellBorderStyle.forInt(cellStyle.getBorderTop().getCode()))) {
                nvwaCell.setTopBorderStyle(borderTop);
                XSSFColor topBorderXSSFColor = xCellStyle.getTopBorderXSSFColor();
                if (null != topBorderXSSFColor) {
                    byte[] rgb4 = topBorderXSSFColor.getRGB();
                    if (null != rgb4) {
                        String topBorderColorIndex = nvwaCell.getTopBorderColorHex();
                        if (StringUtils.isEmpty(topBorderColorIndex) || topBorderColorIndex.equals(defaultStyle.getTopBorderColor())) {
                            nvwaCell.setTopBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb4)));
                        }
                    } else {
                        String topBorderColorIndex = nvwaCell.getTopBorderColorHex();
                        if (StringUtils.isEmpty(topBorderColorIndex) || topBorderColorIndex.equals(defaultStyle.getTopBorderColor())) {
                            nvwaCell.setTopBorderColor(new CellColor("000000"));
                        }
                    }
                } else {
                    String topBorderColorIndex = nvwaCell.getTopBorderColorHex();
                    if (StringUtils.isEmpty(topBorderColorIndex) || topBorderColorIndex.equals(defaultStyle.getTopBorderColor())) {
                        nvwaCell.setTopBorderColor(new CellColor("000000"));
                    }
                }
            }
            if (CellBorderStyle.NONE != (borderBottom = CellBorderStyle.forInt(cellStyle.getBorderBottom().getCode()))) {
                nvwaCell.setBottomBorderStyle(borderBottom);
                XSSFColor bottomBorderXSSFColor = xCellStyle.getBottomBorderXSSFColor();
                if (null != bottomBorderXSSFColor) {
                    byte[] rgb5 = bottomBorderXSSFColor.getRGB();
                    if (null != rgb5) {
                        nvwaCell.setBottomBorderColor(new CellColor(ExcelToCellSheet.formatRGB(rgb5)));
                    } else {
                        nvwaCell.setBottomBorderColor(new CellColor("000000"));
                    }
                } else {
                    nvwaCell.setBottomBorderColor(new CellColor("000000"));
                }
            }
        }
        nvwaCell.setBold(font.getBold());
        nvwaCell.setFontSize(font.getFontHeightInPoints() * 96 / 72);
        nvwaCell.setItalic(font.getItalic());
        nvwaCell.setFontName(font.getFontName());
        nvwaCell.setInline(font.getStrikeout());
        if (font.getUnderline() == 1 || font.getUnderline() == 2) {
            nvwaCell.setUnderline(true);
        }
        nvwaCell.setFillPatternType(FillPatternType.forInt(cellStyle.getFillPattern().getCode()));
        nvwaCell.setHorizontalAlignment(HorizontalAlignment.forInt(cellStyle.getAlignment().getCode()));
        nvwaCell.setVerticalAlignment(VerticalAlignment.forInt(cellStyle.getVerticalAlignment().getCode() + 1));
        nvwaCell.setWrapLine(cellStyle.getWrapText());
        nvwaCell.setIndent(cellStyle.getIndention());
        if (null != this.provider) {
            this.provider.readCellStyleAfter(cell, nvwaCell);
        }
    }

    private static int formatRGB(int[] rgb) {
        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];
        return (red << 16) + (green << 8) + blue;
    }

    private static int formatRGB(short[] rgb) {
        short red = rgb[0];
        short green = rgb[1];
        short blue = rgb[2];
        return (red << 16) + (green << 8) + blue;
    }

    private static int formatRGB(byte[] rgb) {
        int red = rgb[0] & 0xFF;
        int green = rgb[1] & 0xFF;
        int blue = rgb[2] & 0xFF;
        return (red << 16) + (green << 8) + blue;
    }

    private static int[] blendColorsWithTransparency(int fgR, int fgG, int fgB, int alpha, int bgR, int bgG, int bgB) {
        double alphaRatio = (double)alpha / 255.0;
        int resultR = (int)Math.round((double)fgR * (1.0 - alphaRatio) + (double)bgR * alphaRatio);
        int resultG = (int)Math.round((double)fgG * (1.0 - alphaRatio) + (double)bgG * alphaRatio);
        int resultB = (int)Math.round((double)fgB * (1.0 - alphaRatio) + (double)bgB * alphaRatio);
        resultR = Math.max(0, Math.min(255, resultR));
        resultG = Math.max(0, Math.min(255, resultG));
        resultB = Math.max(0, Math.min(255, resultB));
        return new int[]{resultR, resultG, resultB};
    }
}

