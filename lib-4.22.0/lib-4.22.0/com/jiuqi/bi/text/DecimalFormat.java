/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;

public class DecimalFormat
extends java.text.DecimalFormat {
    private static final long serialVersionUID = -5756460389222543549L;

    public DecimalFormat() {
        this.setRoundingMode(RoundingMode.HALF_UP);
    }

    public DecimalFormat(String pattern) {
        super(pattern);
        this.setRoundingMode(RoundingMode.HALF_UP);
    }

    public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
        super(pattern, symbols);
        this.setRoundingMode(RoundingMode.HALF_UP);
    }

    @Override
    public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
        BigDecimal value = new BigDecimal(number, MathContext.DECIMAL64);
        return this.format(value, result, fieldPosition);
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        if (obj instanceof Double) {
            obj = new BigDecimal((Double)obj, MathContext.DECIMAL64);
        }
        return super.formatToCharacterIterator(obj);
    }
}

