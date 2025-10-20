/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 */
package com.jiuqi.bi.office.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class CellPropertyTransition {
    public static int transBackStyle(FillPatternType patternType) {
        switch (patternType) {
            case NO_FILL: {
                return 1;
            }
            case SOLID_FOREGROUND: {
                return 1;
            }
            case FINE_DOTS: {
                return 3;
            }
            case ALT_BARS: {
                return 26;
            }
            case SPARSE_DOTS: {
                return 4;
            }
            case THICK_HORZ_BANDS: {
                return 8;
            }
            case THICK_VERT_BANDS: {
                return 9;
            }
            case THICK_BACKWARD_DIAG: {
                return 11;
            }
            case THICK_FORWARD_DIAG: {
                return 10;
            }
            case BIG_SPOTS: {
                return 23;
            }
            case BRICKS: {
                return 1;
            }
            case THIN_HORZ_BANDS: {
                return 12;
            }
            case THIN_VERT_BANDS: {
                return 13;
            }
            case THIN_BACKWARD_DIAG: {
                return 15;
            }
            case THIN_FORWARD_DIAG: {
                return 14;
            }
            case SQUARES: {
                return 24;
            }
            case DIAMONDS: {
                return 5;
            }
            case LESS_DOTS: {
                return 7;
            }
            case LEAST_DOTS: {
                return 6;
            }
        }
        return 1;
    }

    public static int transBackColor(int color) {
        return color;
    }

    public static short transBorderStyle(BorderStyle borderStyle) {
        switch (borderStyle) {
            case NONE: {
                return 1;
            }
            case THIN: {
                return 2;
            }
            case MEDIUM: {
                return 7;
            }
            case DASHED: {
                return 3;
            }
            case HAIR: {
                return 9;
            }
            case THICK: {
                return 13;
            }
            case DOUBLE: {
                return 10;
            }
            case DOTTED: {
                return 4;
            }
            case MEDIUM_DASHED: {
                return 8;
            }
            case DASH_DOT: {
                return 5;
            }
            case MEDIUM_DASH_DOT: {
                return 5;
            }
            case DASH_DOT_DOT: {
                return 6;
            }
            case MEDIUM_DASH_DOT_DOT: {
                return 6;
            }
            case SLANTED_DASH_DOT: {
                return 2;
            }
        }
        return 0;
    }

    public static int transBorderColor(int color) {
        return color;
    }

    public static short transCellHorizontal(HorizontalAlignment align) {
        switch (align) {
            case GENERAL: {
                return 0;
            }
            case LEFT: {
                return 1;
            }
            case CENTER: {
                return 3;
            }
            case RIGHT: {
                return 2;
            }
            case FILL: {
                return 0;
            }
            case JUSTIFY: {
                return 6;
            }
            case CENTER_SELECTION: {
                return 3;
            }
        }
        return 0;
    }

    public static short transCellVertical(VerticalAlignment align) {
        switch (align) {
            case TOP: {
                return 1;
            }
            case CENTER: {
                return 3;
            }
            case BOTTOM: {
                return 2;
            }
            case JUSTIFY: {
                return 6;
            }
        }
        return 0;
    }

    public static boolean transWrapLine(boolean wrapLine) {
        return wrapLine;
    }

    public static int transIndent(int indent) {
        return indent;
    }

    public static boolean transFitFontSize(boolean fitFontSize) {
        return fitFontSize;
    }

    public static boolean transVertText(int vertText) {
        return vertText == -165 || vertText == 255;
    }
}

