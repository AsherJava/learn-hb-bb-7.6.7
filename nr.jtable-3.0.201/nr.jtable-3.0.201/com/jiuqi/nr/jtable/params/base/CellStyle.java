/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.jtable.params.base.Style;

public class CellStyle {
    private Style style;
    private String ShowText;

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getShowText() {
        return this.ShowText;
    }

    public void setShowText(String showText) {
        this.ShowText = showText;
    }

    public void setStyle(String fontColor, String groundColor, Boolean bold, Boolean italic, Boolean readOnly, Boolean strikeThrough, String showText) {
        this.style = new Style();
        this.style.setFontColor(fontColor);
        this.style.setGroundColor(groundColor);
        this.style.setBold(bold);
        this.style.setItalic(italic);
        this.style.setReadOnly(readOnly);
        this.style.setStrikeThrough(strikeThrough);
        this.setShowText(showText);
    }
}

