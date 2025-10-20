/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.RichTextString
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFFont
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.office.OfficeError;
import com.jiuqi.bi.office.excel.CellEdge;
import com.jiuqi.bi.office.excel.CellFontKey;
import com.jiuqi.bi.office.excel.CellStyleKey;
import com.jiuqi.bi.office.excel.HSSFProcessor;
import com.jiuqi.bi.office.excel.IExcelProcessor;
import com.jiuqi.bi.office.excel.SXSSFProcessor;
import com.jiuqi.bi.office.excel.XSSFProcessor;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookContext {
    private final Workbook workbook;
    private final IExcelProcessor processor;
    private final Map<CellStyleKey, CellStyle> cellStyles;
    private final Map<CellFontKey, Font> cellFonts;
    private final int options;
    private final short INDEX_WHITE;
    private final short INDEX_HEADER;
    private CellStyle titleStyle;

    public WorkbookContext(Workbook workbook) {
        this(workbook, 0);
    }

    public WorkbookContext(Workbook workbook, int options) {
        this.workbook = workbook;
        this.options = options;
        this.cellStyles = new HashMap<CellStyleKey, CellStyle>();
        this.cellFonts = new HashMap<CellFontKey, Font>();
        this.processor = this.createProcessor();
        this.INDEX_WHITE = this.processor.getColorIndex(HSSFColor.HSSFColorPredefined.WHITE);
        this.INDEX_HEADER = this.processor.getColorIndex(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT);
    }

    private IExcelProcessor createProcessor() {
        if (this.workbook instanceof SXSSFWorkbook) {
            return new SXSSFProcessor((SXSSFWorkbook)this.workbook);
        }
        if (this.workbook instanceof XSSFWorkbook) {
            return new XSSFProcessor((XSSFWorkbook)this.workbook);
        }
        if (this.workbook instanceof HSSFWorkbook) {
            return new HSSFProcessor((HSSFWorkbook)this.workbook);
        }
        throw new OfficeError("\u672a\u652f\u6301\u7684Excel\u6587\u6863\u7c7b\u578b\uff1a" + this.workbook.getClass());
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public int getOptions() {
        return this.options;
    }

    public CellStyle getTitleStyle() {
        if (this.titleStyle == null) {
            this.titleStyle = this.workbook.createCellStyle();
            Font font = this.workbook.createFont();
            font.setFontName("\u5b8b\u4f53");
            font.setBold(true);
            font.setFontHeightInPoints((short)12);
            this.titleStyle.setAlignment(HorizontalAlignment.CENTER);
            this.titleStyle.setFont(font);
        }
        return this.titleStyle;
    }

    public CellStyle getCellStyle(GridData gridData, GridCell gridCell) {
        String content;
        CellStyle cellStyle;
        int col = gridCell.getColNum();
        int row = gridCell.getRowNum();
        CellEdge cellEdge = new CellEdge(gridData);
        CellEdge.EdgeInfo[] edgeInfos = cellEdge.getSelfBorders(col, row);
        if ((this.getOptions() & 2) == 0) {
            edgeInfos = cellEdge.fixBordersByAround(edgeInfos, col, row);
        }
        GridCell tempGridCell = gridCell;
        String cellData = gridCell.getContent();
        if (StringUtils.isNotEmpty(cellData) && cellData.indexOf(10) >= 0) {
            tempGridCell = new GridCell(gridCell);
            tempGridCell.setWrapLine(true);
        }
        if ((cellStyle = this.cellStyles.get(new CellStyleKey(tempGridCell, edgeInfos))) != null) {
            return cellStyle;
        }
        cellStyle = this.workbook.createCellStyle();
        if (!Html.isTransparent((tempGridCell = new GridCell(tempGridCell)).getBackColor()) && tempGridCell.getBackColor() != 0xFFFFFF) {
            if (tempGridCell.getBackStyle() == 0) {
                short backColorIdx = tempGridCell.getSilverHead() ? this.INDEX_HEADER : this.INDEX_WHITE;
                cellStyle.setFillForegroundColor(backColorIdx);
            } else {
                this.processor.setBackgroudColor(cellStyle, tempGridCell.getBackColor(), tempGridCell.getBackAlpha());
                if (tempGridCell.getBackColor() != GridColor.DARK_GREEN.value()) {
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                }
            }
        }
        if (edgeInfos != null) {
            cellStyle.setBorderTop(this.transBorderStyle(edgeInfos[0].style));
            this.processor.setTopBorderColor(cellStyle, edgeInfos[0].color);
            cellStyle.setBorderRight(this.transBorderStyle(edgeInfos[1].style));
            this.processor.setRightBorderColor(cellStyle, edgeInfos[1].color);
            cellStyle.setBorderBottom(this.transBorderStyle(edgeInfos[2].style));
            this.processor.setBottomBorderColor(cellStyle, edgeInfos[2].color);
            cellStyle.setBorderLeft(this.transBorderStyle(edgeInfos[3].style));
            this.processor.setLeftBorderColor(cellStyle, edgeInfos[3].color);
        }
        HorizontalAlignment horzAlign = this.transHorizCellAlign(tempGridCell.getHorzAlign());
        if (tempGridCell.getType() == 4 && tempGridCell.getHorzAlign() == 0 && StringUtils.isNotEmpty(content = tempGridCell.getContent()) && StringUtils.isNumeric(content)) {
            horzAlign = HorizontalAlignment.RIGHT;
        }
        VerticalAlignment vertAlign = this.transVerticalCellAlign(tempGridCell.getVertAlign());
        cellStyle.setAlignment(horzAlign);
        cellStyle.setVerticalAlignment(vertAlign);
        cellStyle.setWrapText(tempGridCell.getWrapLine());
        cellStyle.setIndention((short)tempGridCell.getIndent());
        Font font = this.getCellFont(tempGridCell);
        cellStyle.setFont(font);
        cellStyle.setShrinkToFit(tempGridCell.getFitFontSize());
        if (tempGridCell.getType() == 0) {
            cellStyle.setDataFormat((short)0);
        } else if (tempGridCell.getType() == 1) {
            short formatIndex = this.workbook.createDataFormat().getFormat("@");
            cellStyle.setDataFormat(formatIndex);
        } else if (tempGridCell.getType() == 2) {
            NumberCellProperty ncp = new NumberCellProperty(tempGridCell);
            if (ncp.getIsPercent()) {
                String formatStr = "0" + this.buildDecimal(ncp) + "%";
                short formatIndex = this.workbook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            } else if (tempGridCell.getShowText() != null && tempGridCell.getShowText().endsWith("\u2030")) {
                String numStr = "0";
                String colorStr = "";
                numStr = ncp.getThoundsMark() ? "#,##0" + this.buildDecimal(ncp) : numStr + this.buildDecimal(ncp);
                if (ncp.getWarningNegative()) {
                    colorStr = "[Red]";
                }
                String formatStr = ncp.getBracketNegative() ? numStr + ";" + colorStr + "(" + numStr + ")" : numStr + ";" + colorStr + "-" + numStr;
                short formatIndex = this.workbook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            } else {
                String formatStr = "0";
                formatStr = ncp.getThoundsMark() ? "#,##0" + this.buildDecimal(ncp) : formatStr + this.buildDecimal(ncp);
                formatStr = !ncp.getBracketNegative() ? formatStr + "_ " : formatStr + ";(" + formatStr + ")";
                short formatIndex = this.workbook.createDataFormat().getFormat(formatStr);
                cellStyle.setDataFormat(formatIndex);
            }
        } else if (tempGridCell.getType() == 3) {
            CurrencyCellProperty curCellProperty = new CurrencyCellProperty(tempGridCell);
            String currencyFormat = this.makeCurFormat(curCellProperty);
            short formatIndex = this.workbook.createDataFormat().getFormat(currencyFormat);
            cellStyle.setDataFormat(formatIndex);
        } else if (gridCell.getType() == 5) {
            DateCellProperty dateCellProperty = new DateCellProperty(new CellDataProperty(tempGridCell));
            String dateFormat = this.makeDateFormat(dateCellProperty);
            short formatIndex = this.workbook.createDataFormat().getFormat(dateFormat);
            cellStyle.setDataFormat(formatIndex);
        } else if (gridCell.getType() == 100 && StringUtils.isNotEmpty(tempGridCell.getShowFormat())) {
            String customShowFormat = tempGridCell.getShowFormat();
            short formatIndex = this.workbook.createDataFormat().getFormat(customShowFormat);
            cellStyle.setDataFormat(formatIndex);
        }
        gridCell.getType();
        this.cellStyles.put(new CellStyleKey(tempGridCell, edgeInfos), cellStyle);
        return cellStyle;
    }

    private Font getCellFont(GridCell gridCell) {
        CellFontKey fontKey = new CellFontKey(gridCell);
        Font font = this.cellFonts.get(fontKey);
        if (font != null) {
            return font;
        }
        font = this.workbook.createFont();
        if (gridCell.getFontBold()) {
            font.setBold(true);
        }
        this.processor.setFontColor(font, gridCell.getFontColor());
        font.setFontHeight((short)gridCell.getFontHeight());
        font.setItalic(gridCell.getFontItalic());
        font.setFontName(gridCell.getFontName());
        if (font instanceof XSSFFont) {
            ((XSSFFont)font).setFontHeight((double)gridCell.getFontSizeF());
        } else {
            font.setFontHeightInPoints((short)gridCell.getFontSize());
        }
        font.setStrikeout(gridCell.getFontStrikeOut());
        if (gridCell.getFontUnderLine()) {
            font.setUnderline((byte)1);
        }
        this.cellFonts.put(fontKey, font);
        return font;
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
                return BorderStyle.MEDIUM;
            }
        }
        return BorderStyle.THIN;
    }

    private String buildDecimal(NumberCellPropertyIntf numberCellProperty) {
        int decimalCount = numberCellProperty.getDecimal();
        if (decimalCount > 0) {
            StringBuilder decimal = new StringBuilder(".");
            for (int i = 0; i < decimalCount; ++i) {
                decimal.append("0");
            }
            return decimal.toString();
        }
        return "";
    }

    private String makeCurFormat(CurrencyCellProperty curCellProperty) {
        String currencyFormat = "#,##0";
        String colorStr = "";
        currencyFormat = currencyFormat + this.buildDecimal(curCellProperty);
        int idx = curCellProperty.getUnitIndex();
        if (curCellProperty.getWarningNegative()) {
            colorStr = "[Red]";
        }
        boolean isBracketNegative = curCellProperty.getBracketNegative();
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

    private String makeDateFormat(DateCellProperty dateCellProperty) {
        short idx = dateCellProperty.getDateShowType();
        if (idx < DateCellProperty.PATTERNS.length) {
            return DateCellProperty.PATTERNS[idx];
        }
        return DateCellProperty.PATTERNS[0];
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

    public RichTextString createRichText(String text) {
        return this.processor.createRichTextString(text);
    }
}

