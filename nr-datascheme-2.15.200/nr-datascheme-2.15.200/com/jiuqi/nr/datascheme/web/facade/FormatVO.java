/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

public class FormatVO {
    int formatType = 0;
    boolean thousands = true;
    int displayDigits = 0;
    String currency = null;
    String negativeStyle = "0";
    String fixMode = null;
    String pattern;

    public int getFormatType() {
        return this.formatType;
    }

    public void setFormatType(int formatType) {
        this.formatType = formatType;
    }

    public boolean isThousands() {
        return this.thousands;
    }

    public void setThousands(boolean thousands) {
        this.thousands = thousands;
    }

    public int getDisplayDigits() {
        return this.displayDigits;
    }

    public void setDisplayDigits(int displayDigits) {
        this.displayDigits = displayDigits;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNegativeStyle() {
        return this.negativeStyle;
    }

    public void setNegativeStyle(String negativeStyle) {
        this.negativeStyle = negativeStyle;
    }

    public String getFixMode() {
        return this.fixMode;
    }

    public void setFixMode(String fixMode) {
        this.fixMode = fixMode;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}

