/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFFont
 *  org.apache.poi.hssf.usermodel.HSSFPalette
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.office.excel.WorksheetFormatRGB;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class SheetCellFont {
    private int color;
    private boolean bold;
    private short size;
    private boolean italic;
    private String name;
    private boolean strikeOut;
    private boolean underLine;
    private Font font;
    private HSSFPalette palette;
    private HSSFColor hc;
    private XSSFColor xc;

    public SheetCellFont(CellStyle cellStyle, Workbook workBook) {
        if (cellStyle instanceof HSSFCellStyle) {
            this.font = ((HSSFCellStyle)cellStyle).getFont(workBook);
        } else if (cellStyle instanceof XSSFCellStyle) {
            this.font = ((XSSFCellStyle)cellStyle).getFont();
        }
        if (workBook instanceof HSSFWorkbook) {
            this.palette = ((HSSFWorkbook)workBook).getCustomPalette();
        }
        this.init();
    }

    private void init() {
        if (this.font instanceof HSSFFont) {
            this.hc = this.palette.getColor(this.font.getColor());
            if (this.hc != null) {
                this.color = WorksheetFormatRGB.formatRGB(this.hc.getTriplet());
            }
        } else if (this.font instanceof XSSFFont) {
            this.xc = ((XSSFFont)this.font).getXSSFColor();
            if (this.xc != null && this.xc.getRGB() != null) {
                this.color = WorksheetFormatRGB.formatRGB(this.xc.getRGB(), this.xc.hasTint(), this.xc.getTint());
            }
        }
        this.bold = this.font.getBold();
        this.size = this.font.getFontHeightInPoints();
        this.italic = this.font.getItalic();
        this.name = this.font.getFontName();
        this.strikeOut = this.font.getStrikeout();
        this.underLine = this.font.getUnderline() == 1 || this.font.getUnderline() == 2;
    }

    public int getFontColor() {
        return this.color;
    }

    public boolean isFontBold() {
        return this.bold;
    }

    public short getFontSize() {
        return this.size;
    }

    public boolean isFontItalic() {
        return this.italic;
    }

    public String getFontName() {
        return this.name;
    }

    public boolean isFontStrikeOut() {
        return this.strikeOut;
    }

    public boolean isFontUnderLine() {
        return this.underLine;
    }
}

