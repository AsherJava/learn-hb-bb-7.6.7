/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class CellPropertyTransition {
    public static int transBackStyle(int index) {
        if (index == FillPatternType.NO_FILL.getCode()) {
            return 1;
        }
        if (index == FillPatternType.SOLID_FOREGROUND.getCode()) {
            return 1;
        }
        if (index == FillPatternType.FINE_DOTS.getCode()) {
            return 3;
        }
        if (index == FillPatternType.ALT_BARS.getCode()) {
            return 26;
        }
        if (index == FillPatternType.SPARSE_DOTS.getCode()) {
            return 4;
        }
        if (index == FillPatternType.THICK_HORZ_BANDS.getCode()) {
            return 8;
        }
        if (index == FillPatternType.THICK_VERT_BANDS.getCode()) {
            return 9;
        }
        if (index == FillPatternType.THICK_BACKWARD_DIAG.getCode()) {
            return 11;
        }
        if (index == FillPatternType.THICK_FORWARD_DIAG.getCode()) {
            return 10;
        }
        if (index == FillPatternType.BIG_SPOTS.getCode()) {
            return 23;
        }
        if (index == FillPatternType.BRICKS.getCode()) {
            return 1;
        }
        if (index == FillPatternType.THIN_HORZ_BANDS.getCode()) {
            return 12;
        }
        if (index == FillPatternType.THIN_VERT_BANDS.getCode()) {
            return 13;
        }
        if (index == FillPatternType.THIN_BACKWARD_DIAG.getCode()) {
            return 15;
        }
        if (index == FillPatternType.THIN_FORWARD_DIAG.getCode()) {
            return 14;
        }
        if (index == FillPatternType.SQUARES.getCode()) {
            return 24;
        }
        if (index == FillPatternType.DIAMONDS.getCode()) {
            return 5;
        }
        if (index == FillPatternType.LESS_DOTS.getCode()) {
            return 7;
        }
        if (index == FillPatternType.LEAST_DOTS.getCode()) {
            return 6;
        }
        return 1;
    }

    public static int transBackColor(int color) {
        return color;
    }

    public static short transBorderStyle(short index) {
        if (index == BorderStyle.NONE.getCode()) {
            return 0;
        }
        if (index == BorderStyle.THIN.getCode()) {
            return 2;
        }
        if (index == BorderStyle.MEDIUM.getCode()) {
            return 7;
        }
        if (index == BorderStyle.DASHED.getCode()) {
            return 3;
        }
        if (index == BorderStyle.HAIR.getCode()) {
            return 9;
        }
        if (index == BorderStyle.THICK.getCode()) {
            return 13;
        }
        if (index == BorderStyle.DOUBLE.getCode()) {
            return 10;
        }
        if (index == BorderStyle.DOTTED.getCode()) {
            return 4;
        }
        if (index == BorderStyle.MEDIUM_DASHED.getCode()) {
            return 8;
        }
        if (index == BorderStyle.DASH_DOT.getCode()) {
            return 5;
        }
        if (index == BorderStyle.MEDIUM_DASH_DOT.getCode()) {
            return 5;
        }
        if (index == BorderStyle.DASH_DOT_DOT.getCode()) {
            return 6;
        }
        if (index == BorderStyle.MEDIUM_DASH_DOT_DOT.getCode()) {
            return 6;
        }
        if (index == BorderStyle.SLANTED_DASH_DOT.getCode()) {
            return 2;
        }
        return 0;
    }

    public static int transBorderColor(int color) {
        return color;
    }

    public static short transCellHorizontal(short align) {
        if (align == HorizontalAlignment.GENERAL.getCode()) {
            return 0;
        }
        if (align == HorizontalAlignment.LEFT.getCode()) {
            return 1;
        }
        if (align == HorizontalAlignment.CENTER.getCode()) {
            return 3;
        }
        if (align == HorizontalAlignment.RIGHT.getCode()) {
            return 2;
        }
        if (align == HorizontalAlignment.FILL.getCode()) {
            return 0;
        }
        if (align == HorizontalAlignment.JUSTIFY.getCode()) {
            return 6;
        }
        if (align == HorizontalAlignment.CENTER_SELECTION.getCode()) {
            return 3;
        }
        return 0;
    }

    public static short transCellVertical(short align) {
        switch (align) {
            case 0: {
                return 1;
            }
            case 1: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 3: {
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

