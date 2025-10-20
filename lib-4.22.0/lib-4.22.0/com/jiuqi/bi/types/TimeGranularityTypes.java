/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.types;

import java.util.Arrays;
import java.util.List;

public class TimeGranularityTypes {
    public static final int UNKNOWN = -1;
    public static final int YEAR = 0;
    public static final int HALFYEAR = 1;
    public static final int QUARTER = 2;
    public static final int SEASON = 2;
    public static final int MONTH = 3;
    public static final int XUN = 4;
    public static final int DAY = 5;
    public static final int WEEK = 6;
    public static final List<String> NAMES = Arrays.asList("YEAR", "HALFYEAR", "QUARTER", "MONTH", "XUN", "DAY", "WEEK");
    private static final int[] DAYS = new int[]{360, 180, 90, 30, 10, 1, 7};

    private TimeGranularityTypes() {
    }

    public static int valueOf(String granularityName) {
        if (granularityName == null) {
            return -1;
        }
        int v = NAMES.indexOf(granularityName.toUpperCase());
        if (v != -1) {
            return v;
        }
        if ("SEASON".equalsIgnoreCase(granularityName)) {
            return 2;
        }
        return -1;
    }

    public static String toString(int type) {
        if (type < 0 || type >= NAMES.size()) {
            return "UNKNOWN";
        }
        return NAMES.get(type);
    }

    public static int daysOf(int gruanularity) {
        return DAYS[gruanularity];
    }

    public static int compare(int granularity1, int granularity2) {
        return DAYS[granularity1] - DAYS[granularity2];
    }
}

