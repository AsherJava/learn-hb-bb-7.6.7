/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.enumeration;

public enum DataSnapshotPeriodType {
    NOT_PERIOD("NotPeriod"),
    LASTYEAR_SAMEPERIOD("LastYearPeriod"),
    LAST_PERIOD("LastPeriod");

    public final String value;

    private DataSnapshotPeriodType(String value) {
        this.value = value;
    }

    public static DataSnapshotPeriodType toPeriodType(String value) {
        for (DataSnapshotPeriodType type : DataSnapshotPeriodType.values()) {
            if (!type.value.equals(value)) continue;
            return type;
        }
        return null;
    }
}

