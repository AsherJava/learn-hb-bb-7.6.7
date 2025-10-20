/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel;

public class WorksheetFormatRGB {
    private static int HLSMAX = 255;

    public static int formatRGB(short[] rgb) {
        short red = rgb[0];
        short green = rgb[1];
        short blue = rgb[2];
        return (red << 16) + (green << 8) + blue;
    }

    public static int formatRGB(byte[] rgb) {
        int red = rgb[0] & 0xFF;
        int green = rgb[1] & 0xFF;
        int blue = rgb[2] & 0xFF;
        return (red << 16) + (green << 8) + blue;
    }

    public static int formatRGB(byte[] rgb, boolean hasTint, double tint) {
        if (hasTint) {
            int red = rgb[0] & 0xFF;
            int green = rgb[1] & 0xFF;
            int blue = rgb[2] & 0xFF;
            if (tint < 0.0) {
                red = (int)Math.round((double)red * (1.0 + tint));
                green = (int)Math.round((double)green * (1.0 + tint));
                blue = (int)Math.round((double)blue * (1.0 + tint));
            } else {
                red = (int)((double)red * (1.0 - tint) + ((double)HLSMAX - (double)HLSMAX * (1.0 - tint)));
                green = (int)((double)green * (1.0 - tint) + ((double)HLSMAX - (double)HLSMAX * (1.0 - tint)));
                blue = (int)((double)blue * (1.0 - tint) + ((double)HLSMAX - (double)HLSMAX * (1.0 - tint)));
            }
            return (red << 16) + (green << 8) + blue;
        }
        return WorksheetFormatRGB.formatRGB(rgb);
    }
}

