/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.office.excel.CellPropertyTransition;
import com.jiuqi.np.office.excel.WorksheetFormatRGB;
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
    private CellStyle cellStyle;
    private HSSFPalette palette;
    private XSSFColor xc;

    public SheetCellStyle(CellStyle cellStyle, Workbook workBook) {
        this.cellStyle = cellStyle;
        if (workBook instanceof HSSFWorkbook) {
            this.palette = ((HSSFWorkbook)workBook).getCustomPalette();
        }
        this.init();
    }

    private void init() {
        this.backStyle = CellPropertyTransition.transBackStyle(this.cellStyle.getFillPattern().getCode());
        if (this.cellStyle instanceof HSSFCellStyle) {
            short color = this.cellStyle.getFillForegroundColor();
            HSSFColor hssfColor = this.palette.getColor(color);
            int rgb = WorksheetFormatRGB.formatRGB(hssfColor != null ? hssfColor.getTriplet() : HSSFColor.HSSFColorPredefined.WHITE.getTriplet());
            this.backColor = CellPropertyTransition.transBackColor(rgb);
        }
        if (this.cellStyle instanceof XSSFCellStyle) {
            this.xc = ((XSSFCellStyle)this.cellStyle).getFillForegroundXSSFColor();
            this.backColor = this.xc != null && this.xc.getRGB() != null ? CellPropertyTransition.transBackColor(WorksheetFormatRGB.formatRGB(this.xc.getRGB())) : -1;
        }
        this.leftBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderLeft().getCode());
        this.topBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderTop().getCode());
        this.rightBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderRight().getCode());
        this.bottomBorderStyle = CellPropertyTransition.transBorderStyle(this.cellStyle.getBorderBottom().getCode());
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
            if (((XSSFCellStyle)this.cellStyle).getLeftBorderXSSFColor() != null) {
                this.xc = ((XSSFCellStyle)this.cellStyle).getLeftBorderXSSFColor();
                if (this.xc.getRGB() != null) {
                    this.leftBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.xc.getRGB()));
                }
            }
            if (((XSSFCellStyle)this.cellStyle).getTopBorderXSSFColor() != null) {
                this.xc = ((XSSFCellStyle)this.cellStyle).getTopBorderXSSFColor();
                if (this.xc.getRGB() != null) {
                    this.topBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.xc.getRGB()));
                }
            }
            if (((XSSFCellStyle)this.cellStyle).getRightBorderXSSFColor() != null) {
                this.xc = ((XSSFCellStyle)this.cellStyle).getRightBorderXSSFColor();
                if (this.xc.getRGB() != null) {
                    this.rightBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.xc.getRGB()));
                }
            }
            if (((XSSFCellStyle)this.cellStyle).getBottomBorderXSSFColor() != null) {
                this.xc = ((XSSFCellStyle)this.cellStyle).getBottomBorderXSSFColor();
                if (this.xc.getRGB() != null) {
                    this.bottomBorderColor = CellPropertyTransition.transBorderColor(WorksheetFormatRGB.formatRGB(this.xc.getRGB()));
                }
            }
        }
        this.horzAlign = CellPropertyTransition.transCellHorizontal(this.cellStyle.getAlignment().getCode());
        this.vertAlign = CellPropertyTransition.transCellVertical(this.cellStyle.getVerticalAlignment().getCode());
        this.wrapLine = CellPropertyTransition.transWrapLine(this.cellStyle.getWrapText());
        this.indent = CellPropertyTransition.transIndent(this.cellStyle.getIndention());
        this.fitFontSize = CellPropertyTransition.transFitFontSize(false);
        this.vertText = CellPropertyTransition.transVertText(this.cellStyle.getRotation());
    }

    public int getBackStyle() {
        return this.backStyle;
    }

    public int getBackColor() {
        return this.backColor;
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

