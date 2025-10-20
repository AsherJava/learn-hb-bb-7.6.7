/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

public class JobResult {
    public static final int UNFINISHED = 1;
    public static final int CANCELED = 2;
    public static final int TERMINATED = 3;
    public static final int EXCEPTION = 4;
    public static final int TIMEOUT = 5;
    public static final int SUCCESS = 100;
    public static final int FAILURE = -100;
    public static final int UNCERTIFICATED = -9999;

    private JobResult() {
    }

    public static String getResultTitle(int result) {
        if (result >= 100) {
            return "\u8fd0\u884c\u6210\u529f";
        }
        if (result == -9999) {
            return "\u672a\u8ba4\u8bc1\u901a\u8fc7";
        }
        if (result <= -100) {
            return "\u8fd0\u884c\u5931\u8d25";
        }
        if (result == 1) {
            return "\u672a\u5b8c\u6210";
        }
        if (result == 2) {
            return "\u5df2\u53d6\u6d88";
        }
        if (result == 3) {
            return "\u7ec8\u6b62";
        }
        if (result == 4) {
            return "\u5f02\u5e38";
        }
        return null;
    }
}

