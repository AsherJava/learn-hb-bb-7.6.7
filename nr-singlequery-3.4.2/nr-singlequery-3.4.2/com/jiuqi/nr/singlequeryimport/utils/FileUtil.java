/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FileUtil {
    private static final BigDecimal FLOOR_DELTA = new BigDecimal("0.5");

    public static String formatNumberString(BigDecimal value, int decimal, boolean thoundsMark) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        if (thoundsMark) {
            df.setGroupingSize(3);
            df.setGroupingUsed(true);
        }
        if (decimal >= 0) {
            df.setMaximumFractionDigits(decimal);
            df.setMinimumFractionDigits(decimal);
        }
        return df.format(value);
    }

    public static BigDecimal trimDouble(BigDecimal source, int frcLength) {
        if (frcLength < 0) {
            frcLength = 0;
        }
        int multiplier = (int)Math.exp(Math.log(10.0) * (double)frcLength);
        BigDecimal factor = new BigDecimal(multiplier);
        BigDecimal value = source.multiply(factor);
        value = new BigDecimal(value.add(FLOOR_DELTA).toBigInteger());
        return value.divide(factor, frcLength, 4);
    }
}

