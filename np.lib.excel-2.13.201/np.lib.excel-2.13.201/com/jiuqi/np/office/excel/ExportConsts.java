/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel;

import org.apache.poi.hssf.util.HSSFColor;

public interface ExportConsts {
    public static final char[] errorCharAry = new char[]{'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '\\', '_', '+', '|', '[', ']', '{', '}', ';', '\'', ',', '.', '/', ':', '\"', '<', '>', '?', '~', '`'};
    public static final String FORMAT_CURRENCY = "#,##0";
    public static final String FORMAT_TEXT = "@";
    public static final String FORMAT_DATE = "yyyy-m-d";
    public static final String TITLE_FONTNAME = "\u5b8b\u4f53";
    public static final short TITLE_BOLDWEIGHT = 700;
    public static final short TITLE_HEIGHTINPOINTS = 12;
    public static final short TITLE_BGCOLOR_INDEX = HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex();
    public static final short TITLE_PATTRRN = 1;
    public static final String DEFAULT_RPTNAME = "report";
    public static final String TITLE_SEPARATOR = "_";
    public static final short TABLEHEAD_COLOR = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex();
}

