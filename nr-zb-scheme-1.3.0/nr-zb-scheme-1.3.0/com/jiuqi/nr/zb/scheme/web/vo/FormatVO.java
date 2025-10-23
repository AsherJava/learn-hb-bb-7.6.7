/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

public class FormatVO {
    private int formatType = 0;
    private boolean thousands = true;
    private int displayDigits = 0;
    private String currency = null;
    private String negativeStyle = "0";
    private String fixMode = null;
    private String pattern;

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

