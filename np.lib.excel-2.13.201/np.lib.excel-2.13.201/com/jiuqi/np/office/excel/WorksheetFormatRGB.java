/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel;

public class WorksheetFormatRGB {
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
}

