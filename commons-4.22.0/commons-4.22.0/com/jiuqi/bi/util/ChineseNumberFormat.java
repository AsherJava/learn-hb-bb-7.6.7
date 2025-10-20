/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.chinese.ChineseDouble
 *  com.jiuqi.bi.util.chinese.ChineseInt
 *  com.jiuqi.bi.util.chinese.NoValidCharException
 *  com.jiuqi.bi.util.chinese.NotValidChineseNumberException
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.chinese.ChineseDouble;
import com.jiuqi.bi.util.chinese.ChineseInt;
import com.jiuqi.bi.util.chinese.NoValidCharException;
import com.jiuqi.bi.util.chinese.NotValidChineseNumberException;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class ChineseNumberFormat
extends NumberFormat {
    private static final long serialVersionUID = 3053552989943992690L;
    private boolean useCapital;
    private int decimal;
    private static String[] chDigitUnits = new String[]{"", "\u5341", "\u767e", "\u5343", "\u4e07", "\u5341\u4e07", "\u767e\u4e07", "\u5343\u4e07", "\u4ebf", "\u5341\u4ebf", "\u767e\u4ebf", "\u5343\u4ebf", "\u4e07\u4ebf", "\u5146"};

    public ChineseNumberFormat() {
        this.useCapital = false;
        this.decimal = 2;
    }

    public ChineseNumberFormat(boolean useCapital) {
        this.useCapital = useCapital;
        this.decimal = 2;
    }

    public ChineseNumberFormat(boolean useCapital, int decimal) {
        this.useCapital = useCapital;
        this.decimal = decimal;
    }

    public boolean getUseCapital() {
        return this.useCapital;
    }

    public void setUseCapital(boolean value) {
        this.useCapital = value;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int value) {
        this.decimal = value;
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        if (source == null) {
            return null;
        }
        if (source.indexOf("\u70b9") != -1 || source.indexOf("\u5206\u4e4b") != -1) {
            try {
                return new Double(ChineseDouble.valueOf((String)source));
            }
            catch (NotValidChineseNumberException e) {
                e.printStackTrace();
                return null;
            }
            catch (NoValidCharException e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            return new Long(ChineseInt.valueOf((String)source));
        }
        catch (NotValidChineseNumberException e) {
            e.printStackTrace();
            return null;
        }
        catch (NoValidCharException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        String value = ChineseDouble.parseToChinese((String)String.valueOf(number), (int)this.decimal, (boolean)false, (boolean)this.useCapital);
        toAppendTo.append(value);
        return toAppendTo;
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        String value = ChineseInt.parseToChinese((String)String.valueOf(number), (boolean)false, (boolean)this.useCapital);
        toAppendTo.append(value);
        return toAppendTo;
    }

    public static String toChineseDigitUnit(double unitValue) {
        return ChineseNumberFormat.toChineseDigitUnit((long)Math.rint(unitValue));
    }

    public static String toChineseDigitUnit(long unitValue) {
        int factor = 0;
        while (unitValue >= 10L) {
            unitValue /= 10L;
            ++factor;
        }
        return factor >= chDigitUnits.length ? null : chDigitUnits[factor];
    }
}

