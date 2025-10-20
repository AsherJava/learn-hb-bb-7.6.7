/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IntegerParser {
    public static int parseInt(String val) {
        BigDecimal bd = new BigDecimal(val.replaceAll("([0-9,\\s%]*)\\.[0-9]*", "$1").replaceAll("[^0-9]", "")).setScale(0, 4);
        return bd.intValue();
    }

    public static BigInteger parseBigInt(String val) {
        BigDecimal bd = new BigDecimal(val.replaceAll("([0-9,\\s%]*)\\.[0-9]*", "$1").replaceAll("[^0-9]", "")).setScale(0, 4);
        return bd.toBigInteger();
    }

    public static BigInteger parseBigInt(long val) {
        return BigInteger.valueOf(val);
    }

    public static double parseDouble(String val) {
        BigDecimal bd = new BigDecimal(val).setScale(2, 4);
        return bd.doubleValue();
    }
}

