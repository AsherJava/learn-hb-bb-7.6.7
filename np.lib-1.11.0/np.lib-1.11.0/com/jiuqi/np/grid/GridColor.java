/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class GridColor
implements Serializable,
Cloneable,
Comparable {
    private static final long serialVersionUID = -7019369813270334224L;
    private final int value;
    private final String title;
    public static final int SYSTEM_COLOR = -16777216;
    private static final int COLOR_WINDOW = 5;
    private static final int COLOR_BTNFACE = 15;
    public static final GridColor WINDOW = new GridColor(-16777211, "\u7a97\u53e3\u9ed8\u8ba4");
    public static final GridColor HEADCELL = new GridColor(-16777201, "\u8868\u5934\u9ed8\u8ba4");
    public static final GridColor HEADCELLEDGE = new GridColor(0x808080, "\u8868\u5934\u8fb9\u6846\u9ed8\u8ba4");
    public static final GridColor WHITE = new GridColor(255, 255, 255, "\u767d\u8272");
    public static final GridColor LIGHT_AQUAMARINE = new GridColor(240, 255, 240, "\u5fae\u7eff");
    public static final GridColor YELLOWISH = new GridColor(255, 255, 196, "\u5fae\u9ec4");
    public static final GridColor LIGHT_TURQUOISE = new GridColor(204, 255, 255, "\u6d45\u84dd\u7eff");
    public static final GridColor LIGHT_CORNFLOWER_BLUE = new GridColor(204, 204, 255, "\u77e2\u8f66\u83ca");
    public static final GridColor LEMON_CHIFFON = new GridColor(255, 255, 204, "\u6d45\u67e0\u6aac");
    public static final GridColor GREY_10_PERCENT = new GridColor(208, 208, 208, "\u7070\u8272-15%");
    public static final GridColor LIGHT_BLUE_GREY = new GridColor(60, 216, 255, "\u6d45\u7070\u7eff");
    public static final GridColor MICRO_BLUE = new GridColor(166, 202, 240, "\u5fae\u84dd");
    public static final GridColor LIGHT_GREEN = new GridColor(204, 255, 204, "\u6d45\u7eff");
    public static final GridColor LIGHT_TAN = new GridColor(240, 222, 185, "\u6d45\u8910");
    public static final GridColor TURQUOISE = new GridColor(0, 255, 255, "\u84dd\u7eff");
    public static final GridColor LAVENDER = new GridColor(204, 153, 255, "\u6de1\u7d2b");
    public static final GridColor LIGHT_YELLOW = new GridColor(255, 255, 157, "\u6d45\u9ec4");
    public static final GridColor GREY_25_PERCENT = new GridColor(192, 192, 192, "\u7070\u8272-25%");
    public static final GridColor SKY_BLUE = new GridColor(0, 204, 255, "\u5929\u84dd");
    public static final GridColor PALE_BLUE = new GridColor(153, 204, 255, "\u6de1\u84dd");
    public static final GridColor LIGHT_TEAL = new GridColor(192, 220, 192, "\u6d45\u9752");
    public static final GridColor TAN = new GridColor(255, 204, 153, "\u9ec4\u8910\u8272");
    public static final GridColor AQUA = new GridColor(51, 204, 204, "\u6c34\u7eff");
    public static final GridColor ROSE = new GridColor(255, 153, 204, "\u73ab\u7470\u7ea2");
    public static final GridColor YELLOW = new GridColor(255, 255, 0, "\u9ec4\u8272");
    public static final GridColor GREY_40_PERCENT = new GridColor(150, 150, 150, "\u7070\u8272-40%");
    public static final GridColor LIGHT_BLUE = new GridColor(51, 102, 255, "\u6d45\u84dd");
    public static final GridColor CORNFLOWER_BLUE = new GridColor(153, 153, 255, "\u6d77\u84dd");
    public static final GridColor BRIGHT_GREEN = new GridColor(0, 255, 0, "\u9c9c\u7eff");
    public static final GridColor CORAL = new GridColor(255, 128, 128, "\u73ca\u745a\u7ea2");
    public static final GridColor TEAL = new GridColor(0, 128, 128, "\u9752\u8272");
    public static final GridColor LINGHT_PINK = new GridColor(255, 91, 255, "\u6d45\u7c89\u8272");
    public static final GridColor GOLD = new GridColor(255, 204, 0, "\u91d1\u8272");
    public static final GridColor GREY_50_PERCENT = new GridColor(128, 128, 128, "\u7070\u8272-50%");
    public static final GridColor ROYAL_BLUE = new GridColor(0, 102, 204, "\u54c1\u84dd");
    public static final GridColor BLUE_GREY = new GridColor(102, 102, 153, "\u84dd\u7070");
    public static final GridColor LIME = new GridColor(153, 204, 0, "\u7eff\u9ec4");
    public static final GridColor RED = new GridColor(255, 0, 0, "\u7ea2\u8272");
    public static final GridColor SEA_GREEN = new GridColor(51, 153, 102, "\u6d77\u7eff");
    public static final GridColor PINK = new GridColor(255, 0, 255, "\u7c89\u8272");
    public static final GridColor TEA = new GridColor(224, 192, 0, "\u8336\u8272");
    public static final GridColor GREY_60_PERCENT = new GridColor(115, 115, 115, "\u7070\u8272-60%");
    public static final GridColor BLUE = new GridColor(0, 0, 255, "\u84dd\u8272");
    public static final GridColor LIGHT_INDIGO = new GridColor(63, 63, 188, "\u6d45\u975b\u84dd");
    public static final GridColor DARK_YELLOW = new GridColor(128, 128, 0, "\u6df1\u9ec4");
    public static final GridColor LIGHT_PLUM = new GridColor(199, 86, 142, "\u6d45\u6885\u7ea2");
    public static final GridColor GREEN = new GridColor(0, 128, 0, "\u7eff\u8272");
    public static final GridColor BIGHT_ORCHID = new GridColor(198, 0, 198, "\u4eae\u7d2b");
    public static final GridColor LIGHT_ORANGE = new GridColor(255, 153, 0, "\u6d45\u6a59\u8272");
    public static final GridColor GREY_80_PERCENT = new GridColor(51, 51, 51, "\u7070\u8272-80%");
    public static final GridColor DARK_CYAN = new GridColor(0, 64, 128, "\u6697\u9752\u8272");
    public static final GridColor INDIGO = new GridColor(51, 51, 153, "\u975b\u84dd");
    public static final GridColor OLIVE = new GridColor(64, 64, 0, "\u6a44\u6984");
    public static final GridColor PLUM = new GridColor(153, 51, 102, "\u6885\u7ea2");
    public static final GridColor BLACKISH_GREEN = new GridColor(0, 64, 0, "\u58a8\u7eff");
    public static final GridColor VIOLET = new GridColor(128, 0, 128, "\u7d2b\u7f57\u5170");
    public static final GridColor ORANGE = new GridColor(255, 102, 0, "\u6a59\u8272");
    public static final GridColor BLACK = new GridColor(0, 0, 0, "\u9ed1\u8272");
    public static final GridColor DARK_TEAL = new GridColor(0, 51, 102, "\u6df1\u9752");
    public static final GridColor DARK_BLUE = new GridColor(0, 0, 128, "\u6df1\u84dd");
    public static final GridColor OLIVE_GREEN = new GridColor(51, 51, 0, "\u6a44\u6984\u7eff");
    public static final GridColor DARK_RED = new GridColor(128, 0, 0, "\u6df1\u7ea2");
    public static final GridColor DARK_GREEN = new GridColor(0, 51, 0, "\u6df1\u7eff");
    public static final GridColor ORCHID = new GridColor(102, 0, 102, "\u7d2b\u8272");
    public static final GridColor BROWN = new GridColor(153, 51, 0, "\u8910\u8272");
    public static final GridColor[] PALLETTE64 = new GridColor[]{WINDOW, HEADCELL, WHITE, LIGHT_AQUAMARINE, YELLOWISH, LIGHT_TURQUOISE, LIGHT_CORNFLOWER_BLUE, LEMON_CHIFFON, GREY_10_PERCENT, LIGHT_BLUE_GREY, MICRO_BLUE, LIGHT_GREEN, LIGHT_TAN, TURQUOISE, LAVENDER, LIGHT_YELLOW, GREY_25_PERCENT, SKY_BLUE, PALE_BLUE, LIGHT_TEAL, TAN, AQUA, ROSE, YELLOW, GREY_40_PERCENT, LIGHT_BLUE, CORNFLOWER_BLUE, BRIGHT_GREEN, CORAL, TEAL, LINGHT_PINK, GOLD, GREY_50_PERCENT, ROYAL_BLUE, BLUE_GREY, LIME, RED, SEA_GREEN, PINK, TEA, GREY_60_PERCENT, BLUE, LIGHT_INDIGO, DARK_YELLOW, LIGHT_PLUM, GREEN, BIGHT_ORCHID, LIGHT_ORANGE, GREY_80_PERCENT, DARK_CYAN, INDIGO, OLIVE, PLUM, BLACKISH_GREEN, VIOLET, ORANGE, BLACK, DARK_TEAL, DARK_BLUE, OLIVE_GREEN, DARK_RED, DARK_GREEN, ORCHID, BROWN};
    private static final Map COLOR_CACHE = new HashMap();

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
        if (this == null || obj == null) {
            return false;
        }
        if (!(obj instanceof GridColor)) {
            return false;
        }
        return this.value == ((GridColor)obj).value;
    }

    public int compareTo(Object o) {
        return this.value - ((GridColor)o).value;
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
        GridColor color = (GridColor)COLOR_CACHE.get(new Integer(value));
        return color == null ? new GridColor(value, title) : color;
    }

    public static GridColor valueOf(int value) {
        GridColor color = (GridColor)COLOR_CACHE.get(new Integer(value));
        return color == null ? new GridColor(value) : color;
    }

    public static String getColorTitle(int color) {
        GridColor obj = (GridColor)COLOR_CACHE.get(new Integer(color));
        return obj == null ? Integer.toHexString(color) : obj.toString();
    }

    static {
        for (int i = 0; i < PALLETTE64.length; ++i) {
            Integer key = new Integer(GridColor.PALLETTE64[i].value);
            COLOR_CACHE.put(key, PALLETTE64[i]);
        }
    }
}

