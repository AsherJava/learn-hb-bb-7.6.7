/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.multilang;

public class WrapperData {
    private final int year;
    private final int period;
    private final int type;

    public WrapperData(int year, int type, int period) {
        this.year = year;
        this.type = type;
        this.period = period;
    }

    public int getType() {
        return this.type;
    }

    public int getPeriod() {
        return this.period;
    }

    public int getYear() {
        return this.year;
    }
}

