/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.util;

import java.math.BigDecimal;
import java.security.SecureRandom;

public class FloatOrderGenerator {
    private final SecureRandom sr = new SecureRandom();
    private double floatolder;

    public FloatOrderGenerator() {
        this.init();
    }

    public FloatOrderGenerator(double currMaxVal) {
        this.floatolder = currMaxVal;
    }

    public void init() {
        this.floatolder = System.currentTimeMillis() / 1000L - 1482800000L;
    }

    public void reset() {
        this.init();
    }

    public double next() {
        double newOlder = 1000.0 + this.floatolder;
        double adjuster = this.sr.nextInt();
        this.floatolder = newOlder += (adjuster /= Math.pow(10.0, 8.0));
        return this.floatolder;
    }

    public String nextString() {
        this.next();
        return this.value();
    }

    public String value() {
        BigDecimal bDecimal = new BigDecimal(this.floatolder);
        String olderStr = bDecimal.toString();
        int dot = olderStr.indexOf(".");
        if (dot + 6 < olderStr.length()) {
            return olderStr.substring(0, dot + 6);
        }
        return olderStr;
    }

    public static void main(String[] args) {
        FloatOrderGenerator f = new FloatOrderGenerator();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; ++i) {
            String string = f.nextString();
        }
        long t2 = System.currentTimeMillis();
    }
}

