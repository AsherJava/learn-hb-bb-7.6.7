/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print;

public class FontProp {
    private String fontFamily;
    private int size;
    private String color;
    private boolean isBold;
    private boolean isItalic;

    public String getFontFamily() {
        return this.fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isBold() {
        return this.isBold;
    }

    public void setBold(boolean isBold) {
        this.isBold = isBold;
    }

    public boolean isItalic() {
        return this.isItalic;
    }

    public void setItalic(boolean isItalic) {
        this.isItalic = isItalic;
    }
}

