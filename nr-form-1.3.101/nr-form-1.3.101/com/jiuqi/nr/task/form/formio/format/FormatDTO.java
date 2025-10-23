/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format;

import com.jiuqi.nr.task.form.formio.format.Currency;
import com.jiuqi.nr.task.form.formio.format.FormatType;
import com.jiuqi.nr.task.form.formio.format.NegativeStyle;

public class FormatDTO {
    private FormatType formatType = FormatType.GENERAL;
    private boolean hasThousand;
    private int decimal;
    private NegativeStyle negativeStyle = NegativeStyle.NS_0;
    private Currency currency = Currency.CNY;
    private String pattern;

    public FormatType getFormatType() {
        return this.formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    public boolean isHasThousand() {
        return this.hasThousand;
    }

    public void setHasThousand(boolean hasThousand) {
        this.hasThousand = hasThousand;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public NegativeStyle getNegativeStyle() {
        return this.negativeStyle;
    }

    public void setNegativeStyle(NegativeStyle negativeStyle) {
        this.negativeStyle = negativeStyle;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String toString() {
        return "FormatDTO{formatType=" + (Object)((Object)this.formatType) + ", hasThousand=" + this.hasThousand + ", decimal=" + this.decimal + ", negativeStyle=" + (Object)((Object)this.negativeStyle) + ", currency='" + (Object)((Object)this.currency) + '\'' + ", pattern=" + this.pattern + '}';
    }
}

