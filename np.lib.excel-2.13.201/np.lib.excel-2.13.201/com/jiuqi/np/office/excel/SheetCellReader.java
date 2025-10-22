/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.CurrencyCellPropertyIntf
 *  com.jiuqi.np.grid.GridCell
 *  com.jiuqi.np.grid.GridData
 *  com.jiuqi.np.grid.NumberCellPropertyIntf
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.CurrencyCellPropertyIntf;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid.NumberCellPropertyIntf;
import com.jiuqi.np.office.excel.FormulaConvertor;
import com.jiuqi.np.office.excel.NumericFormatParser;
import com.jiuqi.np.office.excel.SheetCellFont;
import com.jiuqi.np.office.excel.SheetCellStyle;
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
        this.gridCell.setFontSize((int)this.cf.getFontSize());
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
        }
        if (this.cs.getLeftBorderStyle() != 0) {
            this.gridCell.setLEdgeStyle((int)this.cs.getLeftBorderStyle());
            this.gridCell.setLEdgeColor(this.cs.getLeftBorderColor());
        }
        if (this.cs.getTopBorderStyle() != 0) {
            this.gridCell.setTEdgeStyle((int)this.cs.getTopBorderStyle());
            this.gridCell.setTEdgeColor(this.cs.getTopBorderColor());
        }
        if (this.cs.getRightBorderStyle() != 0) {
            this.gridCell.setREdgeStyle((int)this.cs.getRightBorderStyle());
            this.gridCell.setREdgeColor(this.cs.getRightBorderColor());
        }
        if (this.cs.getBottomBorderStyle() != 0) {
            this.gridCell.setBEdgeStyle((int)this.cs.getBottomBorderStyle());
            this.gridCell.setBEdgeColor(this.cs.getBottomBorderColor());
        }
        this.gridCell.setHorzAlign((int)this.cs.getHorzAlign());
        this.gridCell.setVertAlign((int)this.cs.getVertAlign());
        this.gridCell.setWrapLine(this.cs.isWrapLine());
        this.gridCell.setIndent(this.cs.getIndent());
        this.gridCell.setFitFontSize(this.cs.isFitFontSize());
        this.gridCell.setVertText(this.cs.isVertText());
    }

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
        if (cellType == CellType.NUMERIC) {
            this.ppn = new NumericFormatParser(this.format.getFormat(this.cell.getCellStyle().getDataFormat()));
            String cellText = String.valueOf(this.cell.getNumericCellValue());
            if (this.ppn.isDateType() || DateUtil.isCellDateFormatted(this.cell)) {
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
            if (!this.ppn.isGeneral()) {
                if (this.ppn.getDecimal() > 15) {
                    this.ncp.setDecimal(15);
                } else {
                    this.ncp.setDecimal(this.ppn.getDecimal());
                }
                this.ncp.setIsPercent(this.ppn.isParcent());
                this.ncp.setBracketNegative(this.ppn.isBracketNegative());
                this.ncp.setWarningNegative(this.ppn.isWarnNegative());
            } else {
                this.cell.setCellType(CellType.STRING);
                cellText = this.cell.getStringCellValue();
                int digIndex = cellText.indexOf(46);
                if (digIndex == -1) {
                    this.ncp.setDecimal(0);
                } else {
                    int digSize = cellText.length() - digIndex - 1;
                    if (digSize > 15) {
                        this.ncp.setDecimal(15);
                    } else {
                        this.ncp.setDecimal(digSize);
                    }
                }
            }
            if (cellType != CellType.BLANK) {
                this.gridCell.setString(cellText);
            }
            if (this.ppn.isCurrency()) {
                this.gridCell.setDataType(3);
                this.ccp = this.gridCell.toCurrencyCell();
                this.ccp.setUnitIndex(this.ppn.getCurrencyType());
                this.ccp.setUnitShowType(this.ppn.getCurrencyShowType());
            }
            return;
        }
        if (cellType == CellType.STRING) {
            this.gridCell.setDataType(1);
            this.gridCell.setString(this.cell.getStringCellValue());
            return;
        }
        if (cellType == CellType.FORMULA) {
            block28: {
                if (isParseFormula) {
                    this.gridCell.setDataType(2);
                    try {
                        double value = this.cell.getNumericCellValue();
                        this.gridCell.setString(String.valueOf(value));
                    }
                    catch (Exception e) {
                        this.gridCell.setDataType(1);
                        try {
                            String str = this.cell.getStringCellValue();
                            this.gridCell.setString(str);
                        }
                        catch (Exception e1) {
                            this.gridCell.setDataType(0);
                            if (this.fc != null) {
                                this.fc.setGridCellFormula(this.cell.getCellFormula(), this.gridCell);
                                break block28;
                            }
                            this.gridCell.setString("=" + this.cell.getCellFormula());
                        }
                    }
                } else {
                    if (!this.tryReadFormulaValue()) {
                        this.gridCell.setDataType(0);
                    }
                    if (this.fc != null) {
                        this.fc.setGridCellFormula(this.cell.getCellFormula(), this.gridCell);
                    } else {
                        this.gridCell.setString("=" + this.cell.getCellFormula());
                    }
                }
            }
            return;
        }
        if (cellType == CellType.BOOLEAN) {
            this.gridCell.setDataType(4);
            this.gridCell.setBoolean(this.cell.getBooleanCellValue());
            return;
        }
        if (cellType != CellType.ERROR) {
            this.gridCell.setString(this.cell.toString());
        }
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
}

