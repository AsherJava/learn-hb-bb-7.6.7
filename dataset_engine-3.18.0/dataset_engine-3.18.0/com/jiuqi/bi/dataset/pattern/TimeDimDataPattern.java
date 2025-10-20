/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.pattern;

import java.util.HashMap;
import java.util.Map;

public final class TimeDimDataPattern
extends Enum<TimeDimDataPattern> {
    public static final /* enum */ TimeDimDataPattern TIMEKEY;
    public static final /* enum */ TimeDimDataPattern TERMNUM;
    private static final Map<String, TimeDimDataPattern> finder;
    private String key;
    private String title;
    private static final /* synthetic */ TimeDimDataPattern[] $VALUES;

    public static TimeDimDataPattern[] values() {
        return (TimeDimDataPattern[])$VALUES.clone();
    }

    public static TimeDimDataPattern valueOf(String name) {
        return Enum.valueOf(TimeDimDataPattern.class, name);
    }

    private TimeDimDataPattern(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public static TimeDimDataPattern find(String key) {
        TimeDimDataPattern pattern = finder.get(key);
        if (pattern == null) {
            return TIMEKEY;
        }
        return pattern;
    }

    static {
        TimeDimDataPattern[] values;
        TIMEKEY = new TimeDimDataPattern("timekey", "TIMEKEY\u5b57\u7b26\u4e32");
        TERMNUM = new TimeDimDataPattern("term", "\u671f\u6570\u5b57\u7b26\u4e32");
        $VALUES = new TimeDimDataPattern[]{TIMEKEY, TERMNUM};
        finder = new HashMap<String, TimeDimDataPattern>();
        for (TimeDimDataPattern pattern : values = TimeDimDataPattern.values()) {
            finder.put(pattern.getKey(), pattern);
        }
    }
}

