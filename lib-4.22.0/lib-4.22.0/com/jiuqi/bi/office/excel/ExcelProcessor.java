/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.RichTextString
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 *  org.apache.poi.xssf.usermodel.XSSFRichTextString
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.office.excel.IExcelProcessor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public abstract class ExcelProcessor
implements IExcelProcessor {
    protected abstract XSSFColor colorOf(int var1);

    @Override
    public short getColorIndex(HSSFColor.HSSFColorPredefined color) {
        return color.getIndex();
    }

    @Override
    public void setBackgroudColor(CellStyle cellStyle, int color) {
        XSSFColor xssfColor = this.colorOf(color);
        ((XSSFCellStyle)cellStyle).setFillForegroundColor(xssfColor);
    }

    @Override
    public void setBackgroudColor(CellStyle cellStyle, int color, int alpha) {
        XSSFColor xssfColor = this.colorOf(color);
        byte[] rgb = xssfColor.getRGB();
        alpha = (int)Math.round((double)(alpha * 255) / 100.0);
        byte[] tmp = new byte[4];
        tmp[0] = (byte)(alpha & 0xFF);
        System.arraycopy(rgb, 0, tmp, 1, 3);
        xssfColor.setRGB(tmp);
        ((XSSFCellStyle)cellStyle).setFillForegroundColor(xssfColor);
    }

    @Override
    public void setTopBorderColor(CellStyle cellStyle, int color) {
        XSSFColor xssfColor = this.colorOf(color);
        ((XSSFCellStyle)cellStyle).setTopBorderColor(xssfColor);
    }

    @Override
    public void setRightBorderColor(CellStyle cellStyle, int color) {
        XSSFColor xssfColor = this.colorOf(color);
        ((XSSFCellStyle)cellStyle).setRightBorderColor(xssfColor);
    }

    @Override
    public void setLeftBorderColor(CellStyle cellStyle, int color) {
        XSSFColor xssfColor = this.colorOf(color);
        ((XSSFCellStyle)cellStyle).setLeftBorderColor(xssfColor);
    }

    @Override
    public void setBottomBorderColor(CellStyle cellStyle, int color) {
        XSSFColor xssfColor = this.colorOf(color);
        ((XSSFCellStyle)cellStyle).setBottomBorderColor(xssfColor);
    }

    @Override
    public void setFontColor(Font font, int color) {
        XSSFColor xssfColor = this.colorOf(color);
        ((XSSFFont)font).setColor(xssfColor);
    }

    @Override
    public RichTextString createRichTextString(String text) {
        return new XSSFRichTextString(text);
    }
}

