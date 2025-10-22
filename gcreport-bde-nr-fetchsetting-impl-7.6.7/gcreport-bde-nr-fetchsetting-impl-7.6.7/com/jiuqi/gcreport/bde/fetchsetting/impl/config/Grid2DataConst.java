/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.config;

public class Grid2DataConst {
    protected static final String PROPERTY_SHOW_TEXT = "showText";
    protected static final String PROPERTY_EDIT_TEXT = "editText";
    protected static final String PROPERTY_COL_INDEX = "colIndex";
    protected static final String PROPERTY_ROW_INDEX = "rowIndex";
    protected static final String PROPERTY_HTML = "html";
    protected static final String PROPERTY_CHECKED = "checked";
    protected static final String PROPERTY_CHECKABLE = "checkable";
    protected static final String PROPERTY_EXPANDABLE = "expandable";
    protected static final String PROPERTY_EXPANDED = "expanded";
    protected static final String PROPERTY_DEPTH = "depth";
    protected static final String PROPERTY_ISTREEEND = "isTreeEnd";
    protected static final String PROPERTY_MERGED = "isMerged";
    protected static final String PROPERTY_CLIENT_DATA = "clientData";
    protected static final String PROPERTY_CELL_MODE = "cellMode";
    protected static final String MERGE_INFO_COL = "col";
    protected static final String MERGE_INFO_ROW = "row";
    protected static final String SHORT_PROPERTY_SHOW_TEXT = "1";
    protected static final String SHORT_PROPERTY_EDIT_TEXT = "2";
    protected static final String SHORT_PROPERTY_COL_INDEX = "3";
    protected static final String SHORT_PROPERTY_ROW_INDEX = "4";
    public static final String PROPERTY_BACK_GROUND_COLOR = "backColor";
    public static final String PROPERTY_BACK_STYLE = "backStyle";
    public static final String PROPERTY_BACK_IMAGE = "backImage";
    public static final String PROPERTY_BACK_IMAGE_RUI = "backImageUri";
    public static final String PROPERTY_BACK_IMAGE_STYLE = "backImageStyle";
    public static final String PROPERTY_BACK_IMAGE_HORIZION = "backImageHorizion";
    public static final String PROPERTY_BACK_IMAGE_VERTICAL = "backImageVertical";
    public static final String PROPERTY_BACK_IMAGE_BOUNDS = "backImageBounds";
    public static final String PROPERTY_BORDER_COLOR = "borderColor";
    public static final String PROPERTY_BORDER = "border";
    public static final String PROPERTY_SELECTABLE = "selectable";
    public static final String PROPERTY_EDITABLE = "editable";
    public static final String PROPERTY_WRAP_LINE = "wrapLine";
    public static final String PROPERTY_INDENT = "indent";
    public static final String PROPERTY_INDENT_PX = "indentPx";
    public static final String PROPERTY_VERT_ALIGN = "vertAlign";
    public static final String PROPERTY_HORZ_ALIGN = "horzAlign";
    public static final String PROPERTY_VERT_TEXT = "vertText";
    public static final String PROPERTY_MULTI_LINE = "multiLine";
    public static final String PROPERTY_FONT_NAME = "fontName";
    public static final String PROPERTY_FONT_SIZE = "fontSize";
    public static final String PROPERTY_FONT_SIZE_UNIT = "fontSizeUnit";
    public static final String PROPERTY_FIT_FONT_SIZE = "fitFontSize";
    public static final String PROPERTY_DECORATION = "decoration";
    public static final String PROPERTY_FONT_BOLD = "fontBold";
    public static final String PROPERTY_FONT_ITALIC = "fontItalic";
    public static final String PROPERTY_FONT_DECORATION = "decoration";
    public static final String PROPERTY_FONT_COLOR = "fontColor";
    public static final String PROPERTY_FORE_GROUND_COLOR = "foregroundColor";
    public static final String PROPERTY_FONT_STYLE = "fontStyle";
    public static final String PROPERTY_TEXT_STROKE = "textStroke";
    public static final String PROPERTY_TEXT_SHADOW = "textShadow";
    public static final String PROPERTY_ROW_SPAN = "rowSpan";
    public static final String PROPERTY_COL_SPAN = "colSpan";
    public static final String PROPERTY_PADDING = "padding";
    public static final String PROPERTY_SILVERHEAD = "silverHead";
    public static final String PROPERTY_DATATYPE = "dataType";
    public static final String PROPERTY_TEXT_SHADOW_OFFSETX = "offsetX";
    public static final String PROPERTY_TEXT_SHADOW_OFFSETY = "offsetY";
    public static final String PROPERTY_TEXT_SHADOW_BLUR = "blur";
    public static final String PROPERTY_TEXT_SHADOW_COLOR = "color";
    public static final int FONT_STYLE_PLAIN = 1;
    public static final int FONT_STYLE_BOLD = 2;
    public static final int FONT_STYLE_ITALIC = 4;
    public static final int FONT_STYLE_UNDERLINE = 8;
    public static final int FONT_STYLE_INLINE = 16;
    public static final int FONT_STYLE_TEXT_STROKE = 32;
    public static final String FONT_STYLE_NAME_PLAIN = "plain";
    public static final String FONT_STYLE_NAME_BOLD = "bold";
    public static final String FONT_STYLE_NAME_ITALIC = "italic";
    public static final String FONT_STYLE_NAME_UNDERLINE = "underline";
    public static final String FONT_STYLE_NAME_INLINE = "inline";

    static String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (color == -1) {
                return "";
            }
            if (Grid2DataConst.isNotEmpty(defaultColor)) {
                return defaultColor;
            }
            return "#" + Grid2DataConst.leftPad(Integer.toHexString(color & 0xFFFFFF), 6, "0");
        }
        return "#" + Grid2DataConst.leftPad(Integer.toHexString(color), 6, "0");
    }

    private static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    private static String leftPad(String str, int size, String delim) {
        if ((size = (size - str.length()) / delim.length()) > 0) {
            str = Grid2DataConst.repeat(delim, size) + str;
        }
        return str;
    }

    static String repeat(String str, int repeat) {
        StringBuffer buffer = new StringBuffer(repeat * str.length());
        for (int i = 0; i < repeat; ++i) {
            buffer.append(str);
        }
        return buffer.toString();
    }
}

