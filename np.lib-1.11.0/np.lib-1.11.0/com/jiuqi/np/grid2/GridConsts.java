/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import java.util.HashMap;
import java.util.Map;

public class GridConsts {
    public static final String CHAR_ENCODING_UTF_8 = "UTF-8";
    public static final byte BIFF_EOF = 127;
    public static final byte BIFF_ShowText = 0;
    public static final byte BIFF_EditText = 1;
    public static final byte BIFF_DataEx = 2;
    public static final byte BIFF_FitFontSize = 3;
    public static final byte BIFF_SCRIPT = 4;
    public static final int GRID_EDITOR_FACE_NONE = 0;
    public static final int GRID_EDITOR_FACE_BUTTON = 1;
    public static final int GRID_EDITOR_FACE_LIST = 2;
    public static final int FONT_STYLE_PLAIN = 1;
    public static final int FONT_STYLE_BOLD = 2;
    public static final int FONT_STYLE_ITALIC = 4;
    public static final int FONT_STYLE_UNDERLINE = 8;
    public static final int FONT_STYLE_INLINE = 16;
    public static final String FONT_STYLE_NAME_PLAIN = "plain";
    public static final String FONT_STYLE_NAME_BOLD = "bold";
    public static final String FONT_STYLE_NAME_ITALIC = "italic";
    public static final String FONT_STYLE_NAME_UNDERLINE = "underline";
    public static final String FONT_STYLE_NAME_INLINE = "inline";
    public static final Map<String, Integer> FONT_STYLE = new HashMap<String, Integer>(5);
    public static final int ROWLAYOUT_MARGIN_LEFT = 3;
    public static final int ROWLAYOUT_MARGIN_RIGHT = 3;
    public static final int ROWLAYOUT_MARGIN_TOP = 3;
    public static final int ROWLAYOUT_MARGIN_BOTTOM = 3;
    public static final int ROWLAYOUT_SPACING = 3;
    public static final int BORDER_TYPE_PLAIN = 0;
    public static final int BORDER_TYPE_IMAGE = 1;
    public static final int BORDER_STYLE_SOLID = 0;
    public static final int BORDER_STYLE_DOTTED = 1;
    public static final int BORDER_STYLE_DASHED = 2;
    public static final String[] BORDER_STYLE_ARRAY;
    public static final String BASE_PROPERTY_FONT = "font";
    public static final String BASE_PROPERTY_FOREGROUND = "foreground";
    public static final String BASE_PROPERTY_BACKGROUND = "background";
    public static final String BASE_PROPERTY_BACKIMAGE = "backimage";
    public static final String BASE_PROPERTY_CURSOR = "cursor";
    public static final String BASE_PROPERTY_BORDER = "border";
    public static final String BASE_PROPERTY_STYLE_BORDER = "style_border";
    public static final String STATE_NAME_NORMAL = "";
    public static final String STATE_NAME_HOVER = "hover";
    public static final String STATE_NAME_ACTIVE = "active";
    public static final String STATE_NAME_SELECT_ACTIVE = "select_active";
    public static final String STATE_NAME_SELECT = "select";
    public static final String STATE_NAME_DISABLE = "disable";
    public static final String STATE_NAME_READONLY = "readonly";
    public static final String STATE_NAME_ERROR = "error";
    public static final String CSS_HEADER_BACKGROUND = "header_background";
    public static final String CSS_HEADER_BACKIMAGE = "header_backimage";
    public static final String CSS_HEADER_FOREGROUND = "header_foreground";
    public static final String CSS_HEADER_FONT = "header_font";
    public static final int MODE_NORMAL = 0;
    public static final int MODE_READONLY = 2;
    public static final int MODE_ERROR = 4;
    public static final int MODE_UNSELECTABLE = 8;
    public static final int MODE_DISABLED = 65536;
    public static final int TABLECOLUMN_DATATYPE_STRING = 0;
    public static final int TABLECOLUMN_DATATYPE_NUMBER = 1;
    public static final int CSSSTYLE_TYPE_CONTROL = 0;
    public static final int CSSSTYLE_TYPE_CLASS = 1;

    static {
        FONT_STYLE.put(FONT_STYLE_NAME_PLAIN, 1);
        FONT_STYLE.put(FONT_STYLE_NAME_BOLD, 2);
        FONT_STYLE.put(FONT_STYLE_NAME_ITALIC, 4);
        FONT_STYLE.put(FONT_STYLE_NAME_UNDERLINE, 8);
        FONT_STYLE.put(FONT_STYLE_NAME_INLINE, 16);
        BORDER_STYLE_ARRAY = new String[]{"solid", "dotted", "dashed"};
    }
}

