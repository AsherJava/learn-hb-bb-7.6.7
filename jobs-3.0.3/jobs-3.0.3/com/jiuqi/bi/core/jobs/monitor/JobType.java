/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

public enum JobType {
    SCHEDULED_JOB(1, "\u8ba1\u5212\u4efb\u52a1"),
    MANUAL_JOB(2, "\u624b\u5de5\u6267\u884c"),
    REALTIME_JOB(3, "\u5373\u65f6\u4efb\u52a1"),
    SUB_JOB(10, "\u5b50\u4efb\u52a1");

    private int value;
    private String title;

    private JobType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static JobType valueOf(int value) {
        for (JobType j : JobType.values()) {
            if (j.getValue() != value) continue;
            return j;
        }
        return null;
    }
}

