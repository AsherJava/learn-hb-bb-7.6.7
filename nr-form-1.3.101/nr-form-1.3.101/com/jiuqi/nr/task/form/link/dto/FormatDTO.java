/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.link.dto;

public class FormatDTO {
    private Integer formatType = 0;
    private Boolean thousands = true;
    private Integer displayDigits = 0;
    private String currency = "\uffe5";
    private String negativeStyle = "0";
    private String fixMode = null;

    public Integer getFormatType() {
        return this.formatType;
    }

    public void setFormatType(Integer formatType) {
        this.formatType = formatType;
    }

    public Boolean getThousands() {
        return this.thousands;
    }

    public void setThousands(Boolean thousands) {
        this.thousands = thousands;
    }

    public Integer getDisplayDigits() {
        return this.displayDigits;
    }

    public void setDisplayDigits(Integer displayDigits) {
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
}

