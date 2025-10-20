/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFPalette
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.office.excel.CellPropertyTransition;
import com.jiuqi.bi.office.excel.WorksheetFormatRGB;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class SheetCellStyle {
    private int backStyle;
    private int backColor;
    private int backAlpha;
    private short leftBorderStyle;
    private short topBorderStyle;
    private short rightBorderStyle;
    private short bottomBorderStyle;
    private int leftBorderColor;
    private int topBorderColor;
    private int rightBorderColor;
    private int bottomBorderColor;
    private short horzAlign;
    private short vertAlign;
    private boolean wrapLine;
    private int indent;
    private boolean fitFontSize;
    private boolean vertText;
    private final CellStyle cellStyle;
    private HSSFPalette palette;

    public SheetCellStyle(CellStyle cellStyle, Workbook workBook) {
        this.cellStyle = cellStyle;
        if (workBook instanceof HSSFWorkbook) {
            this.palette = ((HSSFWorkbook)workBook).getCustomPalette();
        }
        this.init();
    }

    private void init() {
        this.backStyle = CellPropertyTransition.transBackStyle(this.cellStyle.getFillPattern());
        if (this.cellStyle instanceof HSSFCellStyle) {
            short color = this.cellStyle.getFillForegroundColor();
            HSSFColor hssfColor = this.palette.getColor(color);
            if (hssfColor == null) {
                hssfColor = HSSFColor.HSSFColorPredefined.WHITE.getColor();
            }
            int rgb = WorksheetFormatRGB.formatRGB(hssfColor.getTriplet());
            this.backColor = CellPropertyTransition.transBackColor(rgb);
            this.backAlpha = 100;
        }
        if (this.cellStyle instanceof XSSFCellStyle) {
            XSSFColor xc = ((XSSFCellStyle)this.cellStyle).getFillForegroundXSSFColor();
            if (xc != null && xc.getRGB() != null) {
                this.backColor = CellPropertyTransition.transBackColor(WorksheetFormatRGB.formatRGB(xc.getRGB(), xc.hasTint(), xc.getTint()));
                if (xc.hasAlpha()) {
                    int alpha = xc.getARGB()[0] & 0xFF;
                    this.backAlpha = (int)Math.round((double)(alpha * 100) / 255.0);
                } else {
                    this.backAlpha = 100;
                }
            } else {
                this.backColor = -1;
            }
        }
        this.leftBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderLeft());
        this.topBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderTop());
        this.rightBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderRight());
        this.bottomBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderBottom());
        if (this.cellStyle instanceof HSSFCellStyle) {
            if (this.cellStyle.getLeftBorderColor() != 0) {
                this.leftBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.palette.getColor(this.cellStyle.getLeftBorderColor()).getTriplet()));
            }
            if (this.cellStyle.getTopBorderColor() != 0) {
                this.topBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.palette.getColor(this.cellStyle.getTopBorderColor()).getTriplet()));
            }
            if (this.cellStyle.getRightBorderColor() != 0) {
                this.rightBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.palette.getColor(this.cellStyle.getRightBorderColor()).getTriplet()));
            }
            if (this.cellStyle.getBottomBorderColor() != 0) {
                this.bottomBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.palette.getColor(this.cellStyle.getBottomBorderColor()).getTriplet()));
            }
        }
        if (this.cellStyle instanceof XSSFCellStyle) {
            XSSFColor xc;
            if (((XSSFCellStyle)this.cellStyle).getLeftBorderXSSFColor() != null && (xc = ((XSSFCellStyle)this.cellStyle).getLeftBorderXSSFColor()).getRGB() != null) {
                this.leftBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(xc.getRGB(), xc.hasTint(), xc.getTint()));
            }
            if (((XSSFCellStyle)this.cellStyle).getTopBorderXSSFColor() != null && (xc = ((XSSFCellStyle)this.cellStyle).getTopBorderXSSFColor()).getRGB() != null) {
                this.topBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(xc.getRGB(), xc.hasTint(), xc.getTint()));
            }
            if (((XSSFCellStyle)this.cellStyle).getRightBorderXSSFColor() != null && (xc = ((XSSFCellStyle)this.cellStyle).getRightBorderXSSFColor()).getRGB() != null) {
                this.rightBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(xc.getRGB(), xc.hasTint(), xc.getTint()));
            }
            if (((XSSFCellStyle)this.cellStyle).getBottomBorderXSSFColor() != null && (xc = ((XSSFCellStyle)this.cellStyle).getBottomBorderXSSFColor()).getRGB() != null) {
                this.bottomBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(xc.getRGB(), xc.hasTint(), xc.getTint()));
            }
        }
        this.horzAlign = CellPropertyTransition.transCellHorizontal(this.cellStyle.getAlignment());
        this.vertAlign = CellPropertyTransition.transCellVertical(this.cellStyle.getVerticalAlignment());
        this.wrapLine = CellPropertyTransition.transWrapLine(this.cellStyle.getWrapText());
        this.indent = CellPropertyTransition.transIndent(this.cellStyle.getIndention());
        this.fitFontSize = CellPropertyTransition.transFitFontSize(this.cellStyle.getShrinkToFit());
        this.vertText = CellPropertyTransition.transVertText(this.cellStyle.getRotation());
    }

    public int getBackStyle() {
        return this.backStyle;
    }

    public int getBackColor() {
        return this.backColor;
    }

    public int getBackAlpha() {
        return this.backAlpha;
    }

    public short getLeftBorderStyle() {
        return this.leftBorderStyle;
    }

    public short getTopBorderStyle() {
        return this.topBorderStyle;
    }

    public short getRightBorderStyle() {
        return this.rightBorderStyle;
    }

    public short getBottomBorderStyle() {
        return this.bottomBorderStyle;
    }

    public int getLeftBorderColor() {
        return this.leftBorderColor;
    }

    public int getTopBorderColor() {
        return this.topBorderColor;
    }

    public int getRightBorderColor() {
        return this.rightBorderColor;
    }

    public int getBottomBorderColor() {
        return this.bottomBorderColor;
    }

    public short getHorzAlign() {
        return this.horzAlign;
    }

    public short getVertAlign() {
        return this.vertAlign;
    }

    public boolean isWrapLine() {
        return this.wrapLine;
    }

    public int getIndent() {
        return this.indent;
    }

    public boolean isFitFontSize() {
        return this.fitFontSize;
    }

    public boolean isVertText() {
        return this.vertText;
    }
}

