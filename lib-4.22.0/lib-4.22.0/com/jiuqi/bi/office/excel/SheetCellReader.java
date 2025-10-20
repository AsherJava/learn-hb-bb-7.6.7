/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DataFormat
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Hyperlink
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.CurrencyCellPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.office.excel.FormulaConvertor;
import com.jiuqi.bi.office.excel.NumericFormatParser;
import com.jiuqi.bi.office.excel.SheetCellFont;
import com.jiuqi.bi.office.excel.SheetCellStyle;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Workbook;

public class SheetCellReader {
    private Workbook workBook;
    private GridData gridData;
    private GridCell gridCell;
    private Cell cell;
    private CellStyle cellStyle;
    private DataFormat format;
    private NumericFormatParser ppn;
    private NumberCellPropertyIntf ncp;
    private CurrencyCellPropertyIntf ccp;
    private FormulaConvertor fc;
    private SheetCellFont cf;
    private SheetCellStyle cs;
    private Hyperlink link;
    private static final String PATTERN_SCIENTIFIC_NOTATION = "^[+-]?\\d+\\.?\\d*[Ee][+-]?\\d+$";

    public SheetCellReader(Workbook workBook, GridData gridData) {
        this.workBook = workBook;
        this.gridData = gridData;
        this.format = workBook.createDataFormat();
    }

    public GridCell readCell(Cell cell, boolean isParseFormula) {
        this.cell = cell;
        this.cellStyle = cell.getCellStyle();
        this.gridCell = new GridCell();
        this.gridCell.init(this.gridData, cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
        this.readCellFont();
        this.readCellStyle();
        this.readCellData(isParseFormula);
        return this.gridCell;
    }

    public GridCell readCell(Cell cell) {
        return this.readCell(cell, false);
    }

    private void readCellFont() {
        this.cf = new SheetCellFont(this.cellStyle, this.workBook);
        this.gridCell.setFontColor(this.cf.getFontColor());
        this.gridCell.setFontBold(this.cf.isFontBold());
        this.gridCell.setFontSize(this.cf.getFontSize());
        this.gridCell.setFontItalic(this.cf.isFontItalic());
        this.gridCell.setFontName(this.cf.getFontName());
        this.gridCell.setFontStrikeOut(this.cf.isFontStrikeOut());
        this.gridCell.setFontUnderLine(this.cf.isFontUnderLine());
    }

    private void readCellStyle() {
        this.cs = new SheetCellStyle(this.cellStyle, this.workBook);
        this.gridCell.setBackStyle(this.cs.getBackStyle());
        if (this.cellStyle.getFillForegroundColor() != 64) {
            this.gridCell.setBackColor(this.cs.getBackColor());
            this.gridCell.setBackAlpha(this.cs.getBackAlpha());
        }
        if (this.cs.getLeftBorderStyle() != 0 && (this.cs.getLeftBorderStyle() != 1 || this.gridCell.getColNum() == 1)) {
            this.gridCell.setLEdgeStyle(this.cs.getLeftBorderStyle());
            this.gridCell.setLEdgeColor(this.cs.getLeftBorderColor());
        }
        if (this.cs.getTopBorderStyle() != 0 && (this.cs.getTopBorderStyle() != 1 || this.gridCell.getRowNum() == 1)) {
            this.gridCell.setTEdgeStyle(this.cs.getTopBorderStyle());
            this.gridCell.setTEdgeColor(this.cs.getTopBorderColor());
        }
        if (this.cs.getRightBorderStyle() != 0) {
            this.gridCell.setREdgeStyle(this.cs.getRightBorderStyle());
            this.gridCell.setREdgeColor(this.cs.getRightBorderColor());
        }
        if (this.cs.getBottomBorderStyle() != 0) {
            this.gridCell.setBEdgeStyle(this.cs.getBottomBorderStyle());
            this.gridCell.setBEdgeColor(this.cs.getBottomBorderColor());
        }
        this.gridCell.setHorzAlign(this.cs.getHorzAlign());
        this.gridCell.setVertAlign(this.cs.getVertAlign());
        this.gridCell.setWrapLine(this.cs.isWrapLine());
        int rIdx = this.gridCell.getRowNum();
        if (this.gridCell.getWrapLine() && !this.gridData.getRowAutoSize(rIdx)) {
            this.gridData.setRowAutoSize(rIdx, true);
        }
        this.gridCell.setIndent(this.cs.getIndent());
        this.gridCell.setFitFontSize(this.cs.isFitFontSize());
        this.gridCell.setVertText(this.cs.isVertText());
    }

    private static String removeDotUnusedZero(String numberStr) {
        int pos_dot = numberStr.indexOf(46);
        if (pos_dot > 0 && !SheetCellReader.isScientificNotation(numberStr)) {
            char c;
            int pos = numberStr.length() - 1;
            boolean contain_zero = false;
            int i = pos;
            while (i > pos_dot && (c = numberStr.charAt(i)) == '0') {
                pos = i--;
                contain_zero = true;
            }
            if (pos == pos_dot + 1) {
                --pos;
            }
            if (contain_zero) {
                return numberStr.substring(0, pos);
            }
        }
        return numberStr;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void readCellData(boolean isParseFormula) {
        String f;
        CellType cellType = this.cell.getCellType();
        this.link = this.cell.getHyperlink();
        if (this.cell.getHyperlink() != null) {
            this.gridCell.setDataType(9);
            this.gridCell.setLinkInformation(this.cell.getStringCellValue(), this.link.getAddress(), null);
            return;
        }
        if (cellType == CellType.BLANK && ((f = this.format.getFormat(this.cell.getCellStyle().getDataFormat())) == null || f.equals("General") || f.equals("@"))) {
            this.gridCell.setDataType(0);
            return;
        }
        if (cellType == null) {
            this.gridCell.setString(this.cell.toString());
            return;
        }
        switch (cellType) {
            case BLANK: 
            case NUMERIC: {
                this.ppn = new NumericFormatParser(this.format.getFormat(this.cell.getCellStyle().getDataFormat()));
                String cellText = Double.toString(this.cell.getNumericCellValue());
                cellText = SheetCellReader.removeDotUnusedZero(cellText);
                if (this.ppn.isDateType() || DateUtil.isCellDateFormatted((Cell)this.cell)) {
                    this.gridCell.setDataType(5);
                    this.gridCell.setDateTime(this.cell.getDateCellValue());
                    this.gridCell.setDataProperty(this.ppn.getDateShowType());
                    return;
                }
                this.gridCell.setDataType(2);
                this.ncp = this.gridCell.toNumberCell();
                this.ncp.setChineseNumber(this.ppn.isChineseNumber());
                this.ncp.setBigChineseChar(this.ppn.isBigChineseNumber());
                this.ncp.setThoundsMarks(this.ppn.isThoundsMarks());
                this.ncp.setIsPercent(this.ppn.isParcent());
                this.ncp.setBracketNegative(this.ppn.isBracketNegative());
                this.ncp.setWarningNegative(this.ppn.isWarnNegative());
                if (this.ppn.getDecimal() > 0) {
                    this.ncp.setDecimal(this.ppn.getDecimal());
                } else {
                    int digIndex = cellText.indexOf(46);
                    if (digIndex == -1) {
                        this.ncp.setDecimal(0);
                    } else {
                        int digSize = cellText.length() - digIndex - 1;
                        this.ncp.setDecimal(Math.min(15, digSize));
                    }
                }
                this.gridCell.setCellData(cellText);
                if (cellType == CellType.BLANK) {
                    this.gridCell.setCellData("");
                }
                if (!this.ppn.isCurrency()) return;
                this.gridCell.setDataType(3);
                this.ccp = this.gridCell.toCurrencyCell();
                this.ccp.setUnitIndex(this.ppn.getCurrencyType());
                this.ccp.setUnitShowType(this.ppn.getCurrencyShowType());
                return;
            }
            case STRING: {
                this.gridCell.setDataType(1);
                this.gridCell.setString(this.cell.getStringCellValue());
                return;
            }
            case FORMULA: {
                if (isParseFormula) {
                    this.gridCell.setDataType(2);
                    try {
                        double value = this.cell.getNumericCellValue();
                        this.gridCell.setString(String.valueOf(value));
                        return;
                    }
                    catch (Exception e) {
                        this.gridCell.setDataType(1);
                        try {
                            String str = this.cell.getStringCellValue();
                            this.gridCell.setString(str);
                            return;
                        }
                        catch (Exception e1) {
                            this.gridCell.setDataType(0);
                            if (this.fc != null) {
                                this.fc.setGridCellFormula(this.cell.getCellFormula(), this.gridCell);
                                return;
                            }
                            this.gridCell.setString("=" + this.cell.getCellFormula());
                        }
                    }
                    return;
                }
                if (!this.tryReadFormulaValue()) {
                    this.gridCell.setDataType(0);
                }
                if (this.fc != null) {
                    this.fc.setGridCellFormula(this.cell.getCellFormula(), this.gridCell);
                    return;
                } else {
                    this.gridCell.setString("=" + this.cell.getCellFormula());
                }
                return;
            }
            case BOOLEAN: {
                this.gridCell.setDataType(4);
                this.gridCell.setBoolean(this.cell.getBooleanCellValue());
                return;
            }
        }
        this.gridCell.setString(this.cell.toString());
    }

    private boolean tryReadFormulaValue() {
        try {
            double d = this.cell.getNumericCellValue();
            this.gridCell.setDataType(2);
            this.gridCell.setFloat(d);
        }
        catch (Exception e) {
            try {
                String s = this.cell.getStringCellValue();
                this.gridCell.setDataType(0);
                this.gridCell.setCellData(s);
            }
            catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    public void setFormulaConvertor(FormulaConvertor fc) {
        this.fc = fc;
    }

    private static boolean isScientificNotation(String str) {
        return Pattern.compile(PATTERN_SCIENTIFIC_NOTATION).matcher(str).matches();
    }

    public static void main(String[] args) {
        String numberStr1 = "123.45670890";
        String numberStr2 = "1.234567089E10";
        System.out.println(SheetCellReader.removeDotUnusedZero(numberStr1));
        System.out.println(SheetCellReader.removeDotUnusedZero(numberStr2));
    }
}

