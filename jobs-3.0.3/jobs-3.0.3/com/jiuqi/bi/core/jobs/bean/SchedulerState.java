/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.bean;

public enum SchedulerState {
    STARTING(1, "\u6b63\u5728\u542f\u52a8"),
    STARTED(2, "\u5df2\u542f\u52a8"),
    SHUTTINGDOWN(3, "\u6b63\u5728\u505c\u6b62"),
    SHUTDOWN(4, "\u5df2\u505c\u6b62");

    private int value;
    private String title;

    private SchedulerState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SchedulerState getByState(int value) {
        for (SchedulerState state : SchedulerState.values()) {
            if (state.value != value) continue;
            return state;
        }
        throw new IllegalArgumentException("\u672a\u8bc6\u522b\u7684state\uff1a" + value);
    }
}

