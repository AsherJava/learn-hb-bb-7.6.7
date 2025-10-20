/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.print;

import com.jiuqi.nr.definition.facade.print.NumericTextFormatDefine;

public class NumericTextFormatDefineImpl
implements NumericTextFormatDefine {
    private static final long serialVersionUID = 9001392061530261812L;
    private String separator = "";
    private String nullResult = "";
    private String zeroResult = "";
    private String roundingZeroResult = "";
    private String decimal = "";

    @Override
    public String getFormatNullResult() {
        return this.nullResult;
    }

    @Override
    public String getFormatRoundingZeroResult() {
        return this.roundingZeroResult;
    }

    @Override
    public String getFormatZeroResult() {
        return this.zeroResult;
    }

    @Override
    public String getThousandsSeparator() {
        return this.separator;
    }

    @Override
    public String getDecimal() {
        return this.decimal;
    }

    @Override
    public void setFormatNullResult(String nullResult) {
        this.nullResult = nullResult;
    }

    @Override
    public void setFormatRoundingZeroResult(String roundingZeroResult) {
        this.roundingZeroResult = roundingZeroResult;
    }

    @Override
    public void setFormatZeroResult(String zeroResult) {
        this.zeroResult = zeroResult;
    }

    @Override
    public void setThousandsSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }
}

