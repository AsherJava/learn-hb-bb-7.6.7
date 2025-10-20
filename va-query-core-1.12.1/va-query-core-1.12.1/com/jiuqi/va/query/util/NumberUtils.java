/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.util.Assert;

public class NumberUtils {
    public static final double TINY_MIN_DOUBLE_VALUE = 1.0E-4;
    public static final double INNY_MAX_DOUBLE_VALUE = 1.0E9;
    public static final double INNY_MAX_DOUBLE_DIVIDER = 1.0E12;
    private static ThreadLocal<NumberFormat> nftl = new ThreadLocal<NumberFormat>(){

        @Override
        protected synchronized NumberFormat initialValue() {
            NumberFormat nf = NumberFormat.getInstance(Locale.SIMPLIFIED_CHINESE);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            if (nf instanceof DecimalFormat) {
                ((DecimalFormat)nf).setDecimalSeparatorAlwaysShown(true);
            }
            return nf;
        }
    };
    private static final DecimalFormat df = new DecimalFormat(",###,##0");

    public static String formatThousand(Number value) {
        Number number = value;
        if (number instanceof Double || number instanceof Float) {
            number = NumberUtils.round(number.doubleValue(), 2);
        }
        return nftl.get().format(number);
    }

    public static String formatInteger(Number value) {
        return df.format(value);
    }

    public static String formatThousand(String str) {
        return NumberUtils.tryFormat(str, 0);
    }

    public static String tryFormat(String str, Number def) {
        return NumberUtils.formatThousand(NumberUtils.tryParseNumber(str, def));
    }

    public static Number tryParseNumber(String str, Number def) {
        try {
            return nftl.get().parse(str);
        }
        catch (Exception e) {
            return def;
        }
    }

    public static final String parsePercent(double progress) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);
        percentFormat.setMaximumIntegerDigits(3);
        percentFormat.setMinimumFractionDigits(2);
        percentFormat.setMinimumIntegerDigits(1);
        return percentFormat.format(progress);
    }

    public static String doubleToString(double value, int decimal) {
        return NumberUtils.doubleToString(NumberUtils.round(value, decimal), decimal, true);
    }

    public static String doubleToString(double value) {
        return NumberUtils.doubleToString(value, 2);
    }

    public static String doubleToString(double value, int decimal, boolean group) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(decimal);
        format.setMinimumFractionDigits(decimal);
        format.setGroupingUsed(group);
        return format.format(value);
    }

    public static BigDecimal createBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = NumberUtils.createBigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }

    public static double round(double value, int scale) {
        return NumberUtils.round(value, scale, 4);
    }

    public static double round(double value) {
        return NumberUtils.round(value, 2);
    }

    public static double sum(double d1, double d2) {
        return NumberUtils.sum(NumberUtils.createBigDecimal(d1), NumberUtils.createBigDecimal(d2)).doubleValue();
    }

    public static double sum(Double d1, Double d2) {
        return NumberUtils.sum(d1 == null ? BigDecimal.ZERO : NumberUtils.createBigDecimal(d1), d2 == null ? BigDecimal.ZERO : NumberUtils.createBigDecimal(d2)).doubleValue();
    }

    public static double sub(double d1, double d2) {
        return NumberUtils.sub(NumberUtils.createBigDecimal(d1), NumberUtils.createBigDecimal(d2)).doubleValue();
    }

    public static double sub(Double d1, Double d2) {
        return NumberUtils.sub(d1 == null ? BigDecimal.ZERO : NumberUtils.createBigDecimal(d1), d2 == null ? BigDecimal.ZERO : NumberUtils.createBigDecimal(d2)).doubleValue();
    }

    public static double mul(double d1, double d2) {
        return NumberUtils.mul(NumberUtils.createBigDecimal(d1), NumberUtils.createBigDecimal(d2)).doubleValue();
    }

    public static double div(double d1, double d2, int scale) {
        return NumberUtils.round(NumberUtils.div(NumberUtils.createBigDecimal(d1), NumberUtils.createBigDecimal(d2)).doubleValue(), scale);
    }

    public static double div(double d1, double d2) {
        return NumberUtils.div(d1, d2, 2);
    }

    public static BigDecimal sum(BigDecimal bd1, BigDecimal bd2) {
        return bd1.add(bd2);
    }

    public static BigDecimal sub(BigDecimal bd1, BigDecimal bd2) {
        return bd1.subtract(bd2);
    }

    public static BigDecimal mul(BigDecimal bd1, BigDecimal bd2) {
        return bd1.multiply(bd2);
    }

    public static BigDecimal div(BigDecimal bd1, BigDecimal bd2) {
        Assert.isTrue(!bd2.equals(BigDecimal.ZERO), "\u9664\u6570\u4e0d\u80fd\u4e3a0\u3002");
        return bd1.divide(bd2, 12, 4);
    }

    public static BigDecimal sum(BigDecimal bd1, double d2) {
        return NumberUtils.sum(bd1, NumberUtils.createBigDecimal(d2));
    }

    public static BigDecimal sub(BigDecimal bd1, double d2) {
        return NumberUtils.sub(bd1, NumberUtils.createBigDecimal(d2));
    }

    public static BigDecimal mul(BigDecimal bd1, double d2) {
        return NumberUtils.mul(bd1, NumberUtils.createBigDecimal(d2));
    }

    public static BigDecimal div(BigDecimal bd1, double d2) {
        return NumberUtils.div(bd1, NumberUtils.createBigDecimal(d2));
    }

    public static double[] split(double value, int times) {
        Assert.isTrue(times > 0, "\u5747\u5206\u4efd\u6570\u5fc5\u987b\u5927\u4e8e0");
        double[] result = new double[times];
        BigDecimal dbValue = NumberUtils.createBigDecimal(value);
        BigDecimal average = NumberUtils.div(dbValue, (double)times).setScale(2, 4);
        for (int i = 0; i < times - 1; ++i) {
            result[i] = average.doubleValue();
        }
        result[times - 1] = NumberUtils.sub(dbValue, NumberUtils.mul(average, (double)(times - 1))).setScale(2, 4).doubleValue();
        return result;
    }

    public static double parseDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Double) {
            return (Double)value;
        }
        return Double.parseDouble(String.valueOf(value));
    }

    public static boolean bigOrEquals(double db1, double db2) {
        if (NumberUtils.compareDouble(db1, db2)) {
            return true;
        }
        return db1 > db2;
    }

    public static boolean smallOrEquals(double db1, double db2) {
        return NumberUtils.bigOrEquals(db2, db1);
    }

    public static boolean compareDouble(double db1, double db2) {
        return Math.abs(db1 - db2) <= NumberUtils.getMinDoubleValue(db1);
    }

    public static boolean isZreo(Double value) {
        if (value == null) {
            return true;
        }
        return Math.abs(value) <= 1.0E-4;
    }

    private static double getMinDoubleValue(double value) {
        if (Math.abs(value) > 1.0E9) {
            return Math.abs(value) / 1.0E12;
        }
        return 1.0E-4;
    }

    public static BigDecimal formatObject(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal)value;
        }
        if (value instanceof Double) {
            return new BigDecimal((Double)value);
        }
        if (value instanceof Integer) {
            return new BigDecimal((Integer)value);
        }
        if (value instanceof Long) {
            return new BigDecimal((Long)value);
        }
        return new BigDecimal(String.valueOf(value));
    }

    public static BigDecimal add(BigDecimal firstDecimal, BigDecimal ... otherDecimals) {
        BigDecimal result;
        BigDecimal bigDecimal = result = firstDecimal == null ? BigDecimal.ZERO : firstDecimal;
        if (null != otherDecimals) {
            for (BigDecimal otherDecimal : otherDecimals) {
                BigDecimal augend = otherDecimal == null ? BigDecimal.ZERO : otherDecimal;
                result = result.add(augend);
            }
        }
        return result;
    }

    public static BigDecimal subtract(BigDecimal firstDecimal, BigDecimal ... otherDecimals) {
        BigDecimal result;
        BigDecimal bigDecimal = result = firstDecimal == null ? BigDecimal.ZERO : firstDecimal;
        if (null != otherDecimals) {
            for (BigDecimal otherDecimal : otherDecimals) {
                BigDecimal subtrahend = otherDecimal == null ? BigDecimal.ZERO : otherDecimal;
                result = result.subtract(subtrahend);
            }
        }
        return result;
    }
}

