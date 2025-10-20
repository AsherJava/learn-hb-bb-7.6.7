/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.RichTextString
 */
package com.jiuqi.bi.office.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;

public interface IExcelProcessor {
    public short getColorIndex(HSSFColor.HSSFColorPredefined var1);

    public void setBackgroudColor(CellStyle var1, int var2);

    public void setBackgroudColor(CellStyle var1, int var2, int var3);

    public void setTopBorderColor(CellStyle var1, int var2);

    public void setRightBorderColor(CellStyle var1, int var2);

    public void setLeftBorderColor(CellStyle var1, int var2);

    public void setBottomBorderColor(CellStyle var1, int var2);

    public void setFontColor(Font var1, int var2);

    public RichTextString createRichTextString(String var1);
}

