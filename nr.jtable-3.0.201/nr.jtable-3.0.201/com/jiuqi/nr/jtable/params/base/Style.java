/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

public class Style {
    private String GroundColor;
    private String FontColor;
    private Boolean Bold;
    private Boolean Italic;
    private Boolean ReadOnly;
    private Boolean StrikeThrough;

    public Boolean getStrikeThrough() {
        return this.StrikeThrough;
    }

    public void setStrikeThrough(Boolean strikeThrough) {
        this.StrikeThrough = strikeThrough;
    }

    public String getGroundColor() {
        return this.GroundColor;
    }

    public void setGroundColor(String groundColor) {
        this.GroundColor = groundColor;
    }

    public String getFontColor() {
        return this.FontColor;
    }

    public void setFontColor(String fontColor) {
        this.FontColor = fontColor;
    }

    public Boolean getBold() {
        return this.Bold;
    }

    public void setBold(Boolean bold) {
        this.Bold = bold;
    }

    public Boolean getItalic() {
        return this.Italic;
    }

    public void setItalic(Boolean italic) {
        this.Italic = italic;
    }

    public Boolean getReadOnly() {
        return this.ReadOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.ReadOnly = readOnly;
    }
}

