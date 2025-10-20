/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import java.awt.Color;

public class ColorInfo {
    public int A;
    public int R;
    public int G;
    public int B;

    public int toRGB() {
        return this.R << 16 | this.G << 8 | this.B;
    }

    public Color toAWTColor() {
        return new Color(this.R, this.G, this.B, this.A);
    }

    public static ColorInfo fromARGB(int red, int green, int blue) {
        return new ColorInfo(255, red, green, blue);
    }

    public static ColorInfo fromARGB(int alpha, int red, int green, int blue) {
        return new ColorInfo(alpha, red, green, blue);
    }

    public ColorInfo(int a, int r, int g, int b) {
        this.A = a;
        this.B = b;
        this.R = r;
        this.G = g;
    }
}

