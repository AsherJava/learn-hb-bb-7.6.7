/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class GridColor
implements Serializable,
Cloneable,
Comparable<GridColor> {
    private static final long serialVersionUID = -7019369813270334224L;
    private final int value;
    private final String title;
    public static final int SYSTEM_COLOR = -16777216;
    private static final int COLOR_WINDOW = 5;
    private static final int COLOR_BTNFACE = 15;
    public static final GridColor WINDOW = new GridColor(-16777211, "\u7a97\u53e3\u9ed8\u8ba4");
    public static final GridColor HEADCELL = new GridColor(-16777201, "\u8868\u5934\u9ed8\u8ba4");
    public static final GridColor WHITE = new GridColor(255, 255, 255, "\u767d\u8272");
    public static final GridColor BLACKISH_GREEN = new GridColor(0, 64, 0, "\u58a8\u7eff");
    public static final GridColor BLACK = new GridColor(0, 0, 0, "\u9ed1\u8272");
    public static final GridColor DARK_GREEN = new GridColor(0, 51, 0, "\u6df1\u7eff");
    public static final GridColor BLUE = new GridColor(0, 0, 112, "\u84dd\u8272");
    public static final GridColor RED = new GridColor(212, 10, 20, "\u7ea2\u8272");
    public static final GridColor GREEN = new GridColor(0, 176, 80, "\u7eff\u8272");
    public static final GridColor GREY = new GridColor(150, 150, 150, "\u7070\u8272");
    public static final GridColor GREY_10_PERCENT = new GridColor(232, 232, 232, "\u7070\u8272-10%");
    public static final GridColor GREY_60_PERCENT = new GridColor(127, 127, 127, "\u7070\u8272-60%");
    public static final GridColor[] PALLETTE = new GridColor[]{WHITE, BLACK, RED, GREEN, BLUE, GREY, GREY_10_PERCENT, GREY_60_PERCENT};
    private static final Map<Integer, GridColor> COLOR_CACHE = new HashMap<Integer, GridColor>();
    private static final int[] SYS_COLOR_VALUES;

    public GridColor(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public GridColor(int value) {
        this(value, null);
    }

    public GridColor(int red, int green, int blue, String title) {
        this((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF, title);
    }

    public GridColor(int red, int green, int blue) {
        this(red, green, blue, null);
    }

    public int value() {
        return this.value;
    }

    public int red() {
        return (this.value & 0xFF0000) >> 16;
    }

    public int green() {
        return (this.value & 0xFF00) >> 8;
    }

    public int blue() {
        return this.value & 0xFF;
    }

    public int toBGR() {
        if (this.value < 0) {
            return this.value;
        }
        int red = (this.value & 0xFF0000) >> 16;
        int blue = (this.value & 0xFF) << 16;
        int green = this.value & 0xFF00;
        return red | blue | green;
    }

    public String toString() {
        return this.title == null ? Integer.toHexString(this.value).toUpperCase() : this.title;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GridColor)) {
            return false;
        }
        return this.value == ((GridColor)obj).value;
    }

    @Override
    public int compareTo(GridColor o) {
        return this.value - o.value;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public static GridColor valueOf(int value, String title) {
        GridColor color = COLOR_CACHE.get(new Integer(value));
        return color == null ? new GridColor(value, title) : color;
    }

    public static GridColor valueOf(int value) {
        GridColor color = COLOR_CACHE.get(new Integer(value));
        return color == null ? new GridColor(value) : color;
    }

    public static String getColorTitle(int color) {
        GridColor obj = COLOR_CACHE.get(new Integer(color));
        return obj == null ? Integer.toHexString(color).toUpperCase() : obj.toString();
    }

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

    public static int getColorValue(int color) {
        if (color >= 0) {
            return color;
        }
        int sysColor = color & 0xFFFFFF;
        if (sysColor >= SYS_COLOR_VALUES.length) {
            return 0;
        }
        return SYS_COLOR_VALUES[sysColor];
    }

    public static byte[] getTriple(int color) {
        return new byte[]{(byte)((color & 0xFF0000) >> 16), (byte)((color & 0xFF00) >> 8), (byte)(color & 0xFF)};
    }

    public static int getColorValue(short[] triple) {
        return (triple[0] & 0xFF) << 16 | (triple[1] & 0xFF) << 8 | triple[2] & 0xFF;
    }

    static {
        for (int i = 0; i < PALLETTE.length; ++i) {
            Integer key = GridColor.PALLETTE[i].value;
            COLOR_CACHE.put(key, PALLETTE[i]);
        }
        SYS_COLOR_VALUES = new int[]{0xC8C8C8, 0, 13743257, 14405055, 0xF0F0F0, 0xFFFFFF, 0x646464, 0, 0, 0, 0xB4B4B4, 16578548, 0xABABAB, 0xFF9933, 0xFFFFFF, 0xF0F0F0, 0xA0A0A0, 0x6D6D6D, 0, 5525059, 0xFFFFFF, 0x696969, 0xE3E3E3, 0, 0xE1FFFF, 0, 0xCC6600, 15389113, 15918295, 0xFF9933, 0xF0F0F0};
    }
}

