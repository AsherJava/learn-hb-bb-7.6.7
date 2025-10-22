/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.AnaUtils;

public enum BorderStyle {
    DEFAULT(-1, "1px", "solid", "rgb(0, 0, 0)"),
    NONE(0, "0px", "NONE", "rgb(0, 0, 0)"),
    SOLID(1, "1px", "solid", "rgb(0, 0, 0)"),
    BOLDER(4, "2px", "solid", "rgb(0, 0, 0)"),
    DOUBLE(8, "1px", "double", "rgb(0, 0, 0)");

    public int style;
    public String border_width;
    public String border_style;
    public String border_color;

    private BorderStyle(int style, String border_width, String border_style, String border_color) {
        this.style = style;
        this.border_width = border_width;
        this.border_style = border_style;
        this.border_color = border_color;
    }

    public static BorderStyle getBorderStyle(int color, int style) {
        if (color == -1 || style == -1) {
            return DEFAULT;
        }
        for (BorderStyle bStyle : BorderStyle.values()) {
            if (bStyle.style != style) continue;
            bStyle.border_color = AnaUtils.colorLongToARGB(color);
            return bStyle;
        }
        return DEFAULT;
    }
}

