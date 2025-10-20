/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.inifile.StreamIni
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.Font;
import com.jiuqi.bi.util.inifile.StreamIni;

public class LabelItem {
    private String name;
    private String expression;
    private int position;
    private Font font = new Font();
    public static final int POS_MAIN_TITLE = 1;
    public static final int POS_TOP_LEFT = 3;
    public static final int POS_TOP_CENTER = 4;
    public static final int POS_TOP_RIGHT = 5;
    public static final int POS_BOTTOM_LEFT = 9;
    public static final int POS_BOTTOM_CENTER = 10;
    public static final int POS_BOTTOM_RIGHT = 11;
    static final int[] LABEL_POS_MAPS = new int[]{1, 3, 4, 5, 9, 10, 11};

    public String getName() {
        return this.name;
    }

    public String getExpression() {
        return this.expression;
    }

    public int getPosition() {
        return this.position;
    }

    public Font getFont() {
        return this.font;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setExpression(String value) {
        this.expression = value;
    }

    public void setPosition(int value) {
        this.position = value;
    }

    public void loadFromIni(int index, StreamIni ini) {
        String nullstr = "";
        String sec = "label" + Integer.toString(index);
        this.name = ini.readString(sec, "name", nullstr);
        this.expression = ini.readString(sec, "exp", nullstr);
        this.position = ini.readInteger(sec, "pos", 0);
        this.font.setName(ini.readString(sec, "fontname", "\u5b8b\u4f53"));
        this.font.setSize(ini.readInteger(sec, "fontsize", 9));
        this.font.setColor(ini.readInteger(sec, "fontcolor", 9));
        this.font.setStylevalue(ini.readInteger(sec, "fontstyle", 0));
    }

    public void saveToIni(int index, StreamIni ini) {
        String sec = "label" + Integer.toString(index);
        ini.writeString(sec, "name", this.name);
        ini.writeString(sec, "exp", this.expression);
        ini.writeInteger(sec, "pos", this.position);
        ini.writeString(sec, "fontname", this.font.getName());
        ini.writeInteger(sec, "fontsize", this.font.getSize());
        ini.writeInteger(sec, "fontcolor", this.font.getColor());
        ini.writeInteger(sec, "fontstyle", this.font.getStylevalue());
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("name=");
        result.append(this.name);
        result.append("; exp=");
        result.append(this.expression);
        result.append("; pos=");
        result.append(this.position);
        result.append("; font=");
        result.append(this.name);
        return result.toString();
    }
}

