/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util;

import com.jiuqi.np.util.LogHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    public static final int WHITE = -1;
    public static final int BLACK = 0;
    private static final String color16_regEx = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
    private static final Pattern pattern = Pattern.compile("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$");

    public static int BGR2RGB(int color) {
        if (color < 0) {
            return color;
        }
        int red = (color & 0xFF) << 16;
        int blue = (color & 0xFF0000) >> 16;
        int green = color & 0xFF00;
        return red | blue | green;
    }

    public static int RGB2BGR(int color) {
        if (color < 0) {
            return color;
        }
        int red = (color & 0xFF0000) >> 16;
        int blue = (color & 0xFF) << 16;
        int green = color & 0xFF00;
        return red | blue | green;
    }

    public static int parseHexColor(String colorString) {
        int result = -1;
        StringBuffer numberStr = new StringBuffer();
        for (int i = colorString.length() - 1; i >= 0; --i) {
            char chr = colorString.charAt(i);
            if (chr >= '0' && chr <= '9' || chr >= 'a' && chr <= 'f' || chr >= 'A' && chr <= 'F') {
                numberStr.insert(0, chr);
            }
            if (numberStr.length() == 6) break;
            if (chr != '+' && chr != '-') continue;
            numberStr.insert(0, chr);
            break;
        }
        if (numberStr.length() > 0) {
            result = Integer.parseInt(numberStr.toString(), 16);
        }
        return result;
    }

    public static int parseHexRGBColor(String colorString) {
        return ColorUtil.BGR2RGB(ColorUtil.parseHexColor(colorString));
    }

    public static void main(String[] args) {
        LogHelper.info(String.valueOf(ColorUtil.parseHexRGBColor("#FF7792")));
    }

    public static int mergeColor(int color1, int color2) {
        if (color1 == color2) {
            return color1;
        }
        if (color1 == -16777211) {
            return color2;
        }
        if (color2 == -16777211) {
            return color1;
        }
        int red1 = ColorUtil.red(color1);
        int green1 = ColorUtil.green(color1);
        int blue1 = ColorUtil.blue(color1);
        int red2 = ColorUtil.red(color2);
        int green2 = ColorUtil.green(color2);
        int blue2 = ColorUtil.blue(color2);
        int red = red1 * red2 / 255;
        int green = green1 * green2 / 255;
        int blue = blue1 * blue2 / 255;
        return ColorUtil.htmlColorToInt(ColorUtil.convertRGBToHex(red, green, blue));
    }

    private static String convertRGBToHex(int r, int g, int b) {
        int red = r / 16;
        int rred = r % 16;
        String rFString = red == 10 ? "A" : (red == 11 ? "B" : (red == 12 ? "C" : (red == 13 ? "D" : (red == 14 ? "E" : (red == 15 ? "F" : String.valueOf(red))))));
        String rSString = rred == 10 ? "A" : (rred == 11 ? "B" : (rred == 12 ? "C" : (rred == 13 ? "D" : (rred == 14 ? "E" : (rred == 15 ? "F" : String.valueOf(rred))))));
        rFString = rFString + rSString;
        int green = g / 16;
        int rgreen = g % 16;
        String gFString = green == 10 ? "A" : (green == 11 ? "B" : (green == 12 ? "C" : (green == 13 ? "D" : (green == 14 ? "E" : (green == 15 ? "F" : String.valueOf(green))))));
        String gSString = rgreen == 10 ? "A" : (rgreen == 11 ? "B" : (rgreen == 12 ? "C" : (rgreen == 13 ? "D" : (rgreen == 14 ? "E" : (rgreen == 15 ? "F" : String.valueOf(rgreen))))));
        gFString = gFString + gSString;
        int blue = b / 16;
        int rblue = b % 16;
        String bFString = blue == 10 ? "A" : (blue == 11 ? "B" : (blue == 12 ? "C" : (blue == 13 ? "D" : (blue == 14 ? "E" : (blue == 15 ? "F" : String.valueOf(blue))))));
        String bSString = rblue == 10 ? "A" : (rblue == 11 ? "B" : (rblue == 12 ? "C" : (rblue == 13 ? "D" : (rblue == 14 ? "E" : (rblue == 15 ? "F" : String.valueOf(rblue))))));
        bFString = bFString + bSString;
        String result = "#" + rFString + gFString + bFString;
        return result;
    }

    private static int red(int color) {
        return color >> 16 & 0xFF;
    }

    private static int green(int color) {
        return color >> 8 & 0xFF;
    }

    private static int blue(int color) {
        return color & 0xFF;
    }

    public static int htmlColorToInt(String color) {
        Matcher matcher = pattern.matcher(color);
        if (!matcher.matches()) {
            color = "#FFFFFF";
        }
        return Integer.parseInt(color.substring(1), 16);
    }
}

