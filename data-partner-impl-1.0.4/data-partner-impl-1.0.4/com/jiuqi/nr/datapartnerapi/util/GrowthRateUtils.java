/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GrowthRateUtils {
    private static final int DEFAULT_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    public static BigDecimal calculateGrowthRate(Number current, Number previous) {
        return GrowthRateUtils.calculateGrowthRate(current, previous, 2, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal calculateGrowthRate(BigDecimal current, BigDecimal previous) {
        return GrowthRateUtils.calculateGrowthRate(current, previous, 2, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal calculateGrowthRate(double current, double previous) {
        return GrowthRateUtils.calculateGrowthRate(current, previous, 2, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal calculateGrowthRate(int current, int previous) {
        return GrowthRateUtils.calculateGrowthRate(current, previous, 2, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal calculateGrowthRate(Number current, Number previous, int scale, RoundingMode roundingMode) {
        if (current == null || previous == null) {
            throw new IllegalArgumentException("\u53c2\u6570[current/yearAgo]\u4e0d\u80fd\u4e3a\u7a7a");
        }
        BigDecimal currentVal = GrowthRateUtils.toBigDecimal(current);
        BigDecimal previousVal = GrowthRateUtils.toBigDecimal(previous);
        if (previousVal.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("\u540c\u671f\u503c\u4e0d\u80fd\u4e3a\u96f6");
        }
        return currentVal.subtract(previousVal).divide(previousVal, scale, roundingMode).multiply(BigDecimal.valueOf(100L)).setScale(scale, roundingMode);
    }

    private static BigDecimal toBigDecimal(Number number) {
        if (number instanceof BigDecimal) {
            return (BigDecimal)number;
        }
        if (number instanceof Integer) {
            return BigDecimal.valueOf(number.intValue());
        }
        if (number instanceof Double) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return new BigDecimal(number.toString());
    }
}

