/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.label;

import java.io.Serializable;

public class ExcelLabel
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text;
    private int rowIndex;
    private int colIndex;
    private int rowSpan;
    private int colSpan;
    private boolean upper = true;
    private String fontName;
    private String fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikeout;
    private String fontColor;

    public ExcelLabel(String text, int rowIndex, int colIndex) {
        this.text = text;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getRowSpan() {
        return this.rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getColSpan() {
        return this.colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public boolean isUpper() {
        return this.upper;
    }

    public void setUpper(boolean upper) {
        this.upper = upper;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return this.bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isStrikeout() {
        return this.strikeout;
    }

    public void setStrikeout(boolean strikeout) {
        this.strikeout = strikeout;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
}

