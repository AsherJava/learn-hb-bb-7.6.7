/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 */
package com.jiuqi.np.office.excel2;

import com.jiuqi.nvwa.grid2.GridCellStyleData;
import java.awt.Color;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class ColorOperateUtil {
    public static final int COLOR_COUNT = 6;
    public static final int COLOR_RADIX = 16;
    GridCellStyleData styleData;

    public ColorOperateUtil(GridCellStyleData styleData) {
        this.styleData = styleData;
    }

    public static XSSFColor getFontColor(GridCellStyleData styleData) {
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
        String color = "000000";
        if (styleData.getForeGroundColor() != 0 && styleData.getForeGroundColor() != -1 && (color = Integer.toHexString(styleData.getForeGroundColor())).length() < 6) {
            int count = 6 - color.length();
            String temp = "";
            for (int i = 0; i < count; ++i) {
                temp = temp + "0";
            }
            color = temp + color;
        }
        clr.setARGBHex(color);
        return clr;
    }

    public XSSFColor getForeGroundColor() {
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
        String color = "FFFFFF";
        if (this.styleData.isSilverHead()) {
            color = "F1F1F1";
        } else {
            color = Integer.toHexString(this.styleData.getBackGroundColor());
            if (color.length() < 6) {
                int count = 6 - color.length();
                String temp = "";
                for (int i = 0; i < count; ++i) {
                    temp = temp + "0";
                }
                color = temp + color;
            } else if (color.length() > 6) {
                color = "FFFFFF";
            }
        }
        clr.setARGBHex(color);
        return clr;
    }

    public XSSFColor getEdgeColor(boolean isBottom) {
        int colorValue = isBottom ? this.styleData.getBottomBorderColor() : this.styleData.getRightBorderColor();
        return this.getEdgeColor(colorValue);
    }

    public XSSFColor getEdgeColor(int gridCellStyleColor) {
        DefaultIndexedColorMap defaultIndexedColorMap = new DefaultIndexedColorMap();
        if (-1 == gridCellStyleColor) {
            return new XSSFColor(new Color(212, 212, 212), (IndexedColorMap)defaultIndexedColorMap);
        }
        String color = Integer.toHexString(gridCellStyleColor);
        if (color.length() < 6) {
            int count = 6 - color.length();
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < count; ++i) {
                temp.append("0");
            }
            color = temp + color;
        }
        XSSFColor clr = new XSSFColor(defaultIndexedColorMap);
        clr.setARGBHex(color);
        return clr;
    }

    public void setEdgeColor(XSSFCellStyle style) {
        style.setBottomBorderColor(this.getEdgeColor(true));
        style.setRightBorderColor(this.getEdgeColor(false));
    }
}

