/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.holiday.manager.common;

import java.util.HashMap;
import java.util.Map;

public enum HolidayEvent {
    HOLIDAY(0),
    WORK(1);

    private int value;
    private static final Map<Integer, HolidayEvent> EVENT_MAP;

    private HolidayEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public HolidayEvent valueOf(int value) {
        return EVENT_MAP.get(value);
    }

    static {
        EVENT_MAP = new HashMap<Integer, HolidayEvent>();
        for (HolidayEvent event : HolidayEvent.values()) {
            EVENT_MAP.put(event.getValue(), event);
        }
    }
}

