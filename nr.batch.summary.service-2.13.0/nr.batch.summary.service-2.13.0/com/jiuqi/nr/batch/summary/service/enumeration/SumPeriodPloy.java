/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.enumeration;

public enum SumPeriodPloy {
    CURRENT_PERIOD("1"),
    RANGE_PERIOD("2"),
    ALL_PERIOD("3"),
    SELECT_PERIOD("4");

    public String ployValue;

    private SumPeriodPloy(String ployValue) {
        this.ployValue = ployValue;
    }

    public static SumPeriodPloy translate(String ployValue) {
        for (SumPeriodPloy inst : SumPeriodPloy.values()) {
            if (!inst.ployValue.equals(ployValue)) continue;
            return inst;
        }
        return CURRENT_PERIOD;
    }
}

