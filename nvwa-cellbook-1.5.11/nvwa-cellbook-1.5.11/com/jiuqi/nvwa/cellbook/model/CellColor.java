/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellColor
implements Serializable,
Cloneable,
Comparable<CellColor> {
    private static final long serialVersionUID = 1L;
    private final int value;
    private final String title;
    private final int alpha;
    public static final String[][] PALLETTE70 = new String[][]{{"\u9ed1\u8272", "#000000"}, {"\u6697\u8272\u7070", "#262626"}, {"\u4e2d\u7b49\u7070", "#595959"}, {"\u7070", "#8C8C8C"}, {"\u7070", "#BFBFBF"}, {"\u4eae\u7070", "#D9D9D9"}, {"\u6781\u4eae\u7070", "#E9E9E9"}, {"\u767d\u7159\u8272", "#F5F5F5"}, {"\u6781\u4eae\u7070", "#FAFAFA"}, {"\u767d\u8272", "#FFFFFF"}, {"\u4eae\u7c89\u7ea2", "#FDE7E8"}, {"\u4eae\u6a59\u8272", "#FFEFE7"}, {"", "#fff5e6"}, {"", "#fffbe6"}, {"", "#f0f9e7"}, {"", "#e7f9f9"}, {"", "#e8f4ff"}, {"", "#ebeafe"}, {"", "#f5eafe"}, {"", "#fdeaf5"}, {"", "#fab6b9"}, {"", "#fecfb8"}, {"", "#ffe2b5"}, {"", "#fff5b5"}, {"", "#d2edbb"}, {"", "#b8eeee"}, {"", "#b9ddff"}, {"", "#c3c1fd"}, {"", "#e0c1f9"}, {"", "#fac1e0"}, {"", "#f3545d"}, {"", "#ff915a"}, {"", "#ffbb52"}, {"", "#e0ca48"}, {"", "#97d560"}, {"", "#5ad7d7"}, {"", "#5ab0ff"}, {"", "#746ffc"}, {"", "#b86ff2"}, {"", "#f46db8"}, {"", "#B30811"}, {"", "#BF470E"}, {"", "#BF7605"}, {"", "#BFA607"}, {"", "#509214"}, {"", "#0E9595"}, {"", "#0F6BBF"}, {"", "#2925BC"}, {"", "#7324B1"}, {"", "#B32373"}, {"", "#77050B"}, {"", "#7F2F09"}, {"", "#7F4E03"}, {"", "#7F6E04"}, {"", "#35610D"}, {"", "#096363"}, {"", "#0A477F"}, {"", "#1B187D"}, {"", "#4C1876"}, {"", "#77174C"}, {"", "#EF0B17"}, {"", "#FF6014"}, {"", "#FF9E08"}, {"", "#FFDE0A"}, {"", "#6BC41B"}, {"", "#14C7C7"}, {"", "#158FFF"}, {"", "#3832FB"}, {"", "#9A31ED"}, {"", "#F02F9A"}};
    public static final List<CellColor> PALLETTE = new ArrayList<CellColor>();

    public CellColor(int value, String title) {
        this(value, title, 0);
    }

    public CellColor(int value, String title, int alpha) {
        if (value > 0xFFFFFF || value < 0 || alpha > 255 || alpha < 0) {
            throw new RuntimeException("\u6784\u9020\u65b9\u6cd5\u8f93\u5165\u4e0d\u5408\u6cd5");
        }
        this.value = value;
        this.title = title;
        this.alpha = alpha;
    }

    public CellColor(String hex) {
        this(hex, hex);
    }

    public CellColor(String hex, String title) {
        if (hex.length() < 6) {
            int count = 6 - hex.length();
            String temp = "";
            for (int i = 0; i < count; ++i) {
                temp = temp + "0";
            }
            hex = temp + hex;
        }
        Integer value = Integer.parseInt(hex.substring(0, 6), 16);
        this.alpha = hex.length() >= 8 ? Integer.parseInt(hex.substring(6, 8), 16) ^ 0xFF : 0;
        this.value = value;
        this.title = title;
    }

    public CellColor(int value) {
        this(value, null, 0);
    }

    public CellColor(int value, int alpha) {
        this(value, null, alpha);
    }

    public CellColor(int red, int green, int blue, String title) {
        this((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF, title, 0);
    }

    public CellColor(int red, int green, int blue, String title, int alpha) {
        this((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF, title, alpha);
    }

    public CellColor(int red, int green, int blue) {
        this(red, green, blue, null);
    }

    public CellColor(int red, int green, int blue, int alpha) {
        this(red, green, blue, null, alpha);
    }

    public int getValue() {
        return this.value;
    }

    public int getRed() {
        return (this.value & 0xFF0000) >> 16;
    }

    public int getGreen() {
        return (this.value & 0xFF00) >> 8;
    }

    public int getBlue() {
        return this.value & 0xFF;
    }

    public String getTitle() {
        return this.title == null ? this.getHexString() : this.title;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public String getHexString() {
        String hexString = Integer.toHexString(this.value).toUpperCase();
        if (hexString.length() < 6) {
            int count = 6 - hexString.length();
            String temp = "";
            for (int i = 0; i < count; ++i) {
                temp = temp + "0";
            }
            hexString = temp + hexString;
        }
        String alpha = Integer.toHexString(this.getAlpha() ^ 0xFF).toUpperCase();
        if ((hexString = hexString + alpha).length() < 8) {
            int count = 8 - hexString.length();
            String temp = "";
            for (int i = 0; i < count; ++i) {
                temp = temp + "0";
            }
            hexString = hexString + temp;
        }
        return hexString;
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
        if (!(obj instanceof CellColor)) {
            return false;
        }
        return this.value == ((CellColor)obj).value && this.alpha == ((CellColor)obj).alpha;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public int compareTo(CellColor o) {
        return this.value - o.value;
    }

    static {
        for (String[] cellColor : PALLETTE70) {
            PALLETTE.add(new CellColor(cellColor[1].substring(1, cellColor[1].length()), cellColor[0]));
        }
    }
}

