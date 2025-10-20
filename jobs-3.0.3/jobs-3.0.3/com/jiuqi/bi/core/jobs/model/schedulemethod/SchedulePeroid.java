/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

public enum SchedulePeroid {
    SECOND("\u79d2"),
    MINUTE("\u5206\u949f"),
    HOUR("\u5c0f\u65f6"),
    DAY("\u5929"),
    WEEK("\u5468"),
    MONTH("\u6708"),
    SEASON("\u5b63"),
    HALFYEAR("\u534a\u5e74"),
    YEAR("\u5e74");

    private String title;

    private SchedulePeroid(String title) {
        this.title = title;
    }

    public String title() {
        return this.title;
    }
}

