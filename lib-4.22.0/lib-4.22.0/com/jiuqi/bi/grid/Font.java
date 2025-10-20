/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import java.io.Serializable;

public class Font
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -7290350592804388817L;
    public static final String DefaultFontName = "\u5b8b\u4f53";
    public static final int DefaultFontSize = 9;
    public static final int DefaultColor = 9;
    private String name = "\u5b8b\u4f53";
    private int size = 9;
    private int color = 0;
    private int style;

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public int getColor() {
        return this.color;
    }

    public boolean getBold() {
        return (this.style & 1) > 0;
    }

    public boolean getItalic() {
        return (this.style & 2) > 0;
    }

    public boolean getUnderline() {
        return (this.style & 4) > 0;
    }

    public boolean getStrikeout() {
        return (this.style & 8) > 0;
    }

    public void setName(String value) {
        this.name = value == null ? this.name : value;
    }

    public void setSize(int value) {
        this.size = value;
    }

    public void setColor(int value) {
        this.color = value;
    }

    public void setBold(boolean value) {
        this.style = value ? (this.style |= 1) : (this.style &= 0xE);
    }

    public void setItalic(boolean value) {
        this.style = value ? (this.style |= 2) : (this.style &= 0xD);
    }

    public void setUnderline(boolean value) {
        this.style = value ? (this.style |= 4) : (this.style &= 0xB);
    }

    public void setStrikeout(boolean value) {
        this.style = value ? (this.style |= 8) : (this.style &= 0xF);
    }

    public int getStylevalue() {
        return this.style;
    }

    public void setStylevalue(int value) {
        this.style = value;
    }

    public String toString() {
        StringBuffer result = new StringBuffer(super.toString());
        result.append("name=");
        result.append(this.name);
        result.append("size=");
        result.append(this.size);
        return result.toString();
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}

