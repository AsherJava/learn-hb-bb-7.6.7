/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

public enum JobKind {
    SCHEDULED_JOB(1, "\u8ba1\u5212\u4efb\u52a1"),
    MANUAL_SCHEDULED_JOB(2, "\u624b\u5de5\u6267\u884c\u8ba1\u5212\u4efb\u52a1"),
    REALTIME_JOB(3, "\u5373\u65f6\u4efb\u52a1"),
    SIMPLE_BACKEND_JOB(3, "\u540e\u53f0\u4efb\u52a1"),
    REMOTE_JOB(3, "\u8fdc\u7a0b\u8c03\u7528\u4efb\u52a1");

    private int value;
    private String title;

    private JobKind(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static JobKind valueOf(int value) {
        for (JobKind j : JobKind.values()) {
            if (j.getValue() != value) continue;
            return j;
        }
        return null;
    }
}

