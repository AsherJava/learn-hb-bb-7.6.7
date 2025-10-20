/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFPalette
 *  org.apache.poi.hssf.usermodel.HSSFRichTextString
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.RichTextString
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.office.excel.IExcelProcessor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;

public class HSSFProcessor
implements IExcelProcessor {
    private final HSSFWorkbook workbook;
    private Map<Integer, HSSFColor> colors;
    private Set<Integer> usedColors;

    public HSSFProcessor(HSSFWorkbook workbook) {
        this.workbook = workbook;
        this.colors = new HashMap<Integer, HSSFColor>();
        this.usedColors = new HashSet<Integer>(56);
    }

    private HSSFColor getColor(int color) {
        HSSFColor hssfColor = this.colors.get(color);
        if (hssfColor == null) {
            hssfColor = this.createColor(color);
            this.colors.put(color, hssfColor);
        }
        return hssfColor;
    }

    private HSSFColor createColor(int color) {
        HSSFColor hssfColor = null;
        HSSFPalette palette = this.workbook.getCustomPalette();
        byte[] rgb = GridColor.getTriple(color);
        if (this.usedColors.size() < 48 && (hssfColor = palette.findColor(rgb[0], rgb[1], rgb[2])) == null) {
            hssfColor = this.resetColor(palette, rgb[0], rgb[1], rgb[2]);
        }
        if (hssfColor == null) {
            hssfColor = palette.findSimilarColor(rgb[0], rgb[1], rgb[2]);
        }
        this.usedColors.add(Integer.valueOf(hssfColor.getIndex()));
        return hssfColor;
    }

    private HSSFColor resetColor(HSSFPalette palette, byte red, byte gree, byte blue) {
        for (int i = 8; i < 56; ++i) {
            HSSFColor color = palette.getColor(i);
            if (color == null || this.usedColors.contains(color.getIndex())) continue;
            palette.setColorAtIndex((short)i, red, gree, blue);
            return palette.getColor(i);
        }
        return null;
    }

    @Override
    public short getColorIndex(HSSFColor.HSSFColorPredefined color) {
        int rgb = GridColor.getColorValue(color.getColor().getTriplet());
        HSSFColor hssfColor = this.getColor(rgb);
        return hssfColor.getIndex();
    }

    @Override
    public void setBackgroudColor(CellStyle cellStyle, int color) {
        HSSFColor hssfColor = this.getColor(color);
        cellStyle.setFillForegroundColor(hssfColor.getIndex());
    }

    @Override
    public void setBackgroudColor(CellStyle cellStyle, int color, int alpha) {
        this.setBackgroudColor(cellStyle, color);
    }

    @Override
    public void setTopBorderColor(CellStyle cellStyle, int color) {
        HSSFColor hssfColor = this.getColor(color);
        cellStyle.setTopBorderColor(hssfColor.getIndex());
    }

    @Override
    public void setRightBorderColor(CellStyle cellStyle, int color) {
        HSSFColor hssfColor = this.getColor(color);
        cellStyle.setRightBorderColor(hssfColor.getIndex());
    }

    @Override
    public void setLeftBorderColor(CellStyle cellStyle, int color) {
        HSSFColor hssfColor = this.getColor(color);
        cellStyle.setLeftBorderColor(hssfColor.getIndex());
    }

    @Override
    public void setBottomBorderColor(CellStyle cellStyle, int color) {
        HSSFColor hssfColor = this.getColor(color);
        cellStyle.setBottomBorderColor(hssfColor.getIndex());
    }

    @Override
    public void setFontColor(Font font, int color) {
        HSSFColor hssfColor = this.getColor(color);
        font.setColor(hssfColor.getIndex());
    }

    @Override
    public RichTextString createRichTextString(String text) {
        return new HSSFRichTextString(text);
    }
}

