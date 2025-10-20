/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 */
package com.jiuqi.common.expimp.dataexport.excel.consts;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public interface ExcelConsts {

    public static interface CellStyleCache {
        public static final String headAmt = "headAmt";
        public static final String contentAmt = "contentAmt";
        public static final String intervalColorAmt = "intervalColorAmt";
        public static final String headText = "headText";
        public static final String contentText = "contentText";
        public static final String intervalColorText = "intervalColorText";
        public static final String specialContentAmt = "specialContentAmt";
    }

    public static interface ContentCellStyle {
        public static final HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
        public static final VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
        public static final short dataFormat = 0;
        public static final boolean hidden = false;
        public static final boolean locked = false;
        public static final boolean quotePrefix = false;
        public static final boolean wrapped = false;
        public static final short rotation = 0;
        public static final short indent = 0;
        public static final BorderStyle borderLeft = BorderStyle.THIN;
        public static final BorderStyle borderRight = BorderStyle.THIN;
        public static final BorderStyle borderTop = BorderStyle.THIN;
        public static final BorderStyle borderBottom = BorderStyle.THIN;
        public static final short leftBorderColor = 0;
        public static final short rightBorderColor = 0;
        public static final short topBorderColor = 0;
        public static final short bottomBorderColor = 0;
        public static final FillPatternType fillPatternType = FillPatternType.SOLID_FOREGROUND;
        public static final short fillBackgroundColor = 9;
        public static final short fillForegroundColor = 9;
        public static final boolean shrinkToFit = false;
        public static final String fontName = "\u5b8b\u4f53";
        public static final short fontHeightInPoints = 13;
        public static final boolean fontItalic = false;
        public static final boolean fontStrikeout = false;
        public static final short fontColor = 0;
        public static final short fontTypeOffset = 0;
        public static final byte fontUnderline = 0;
        public static final int fontCharset = 0;
        public static final boolean fontBold = false;
    }

    public static interface HeadCellStyle {
        public static final HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
        public static final VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
        public static final short dataFormat = 0;
        public static final boolean hidden = false;
        public static final boolean locked = false;
        public static final boolean quotePrefix = false;
        public static final boolean wrapped = false;
        public static final short rotation = 0;
        public static final short indent = 0;
        public static final BorderStyle borderLeft = BorderStyle.THIN;
        public static final BorderStyle borderRight = BorderStyle.THIN;
        public static final BorderStyle borderTop = BorderStyle.THIN;
        public static final BorderStyle borderBottom = BorderStyle.THIN;
        public static final short leftBorderColor = 0;
        public static final short rightBorderColor = 0;
        public static final short topBorderColor = 0;
        public static final short bottomBorderColor = 0;
        public static final FillPatternType fillPatternType = FillPatternType.SOLID_FOREGROUND;
        public static final short fillBackgroundColor = 9;
        public static final short fillForegroundColor = 22;
        public static final boolean shrinkToFit = false;
        public static final String fontName = "\u5b8b\u4f53";
        public static final short fontHeightInPoints = 14;
        public static final boolean fontItalic = false;
        public static final boolean fontStrikeout = false;
        public static final short fontColor = 0;
        public static final short fontTypeOffset = 0;
        public static final byte fontUnderline = 0;
        public static final int fontCharset = 0;
        public static final boolean fontBold = true;
    }
}

