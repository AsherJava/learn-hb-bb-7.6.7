/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum FetchTaskState {
    UNEXECUTE("0", "\u672a\u5904\u7406"),
    FINISHED("1", "\u5df2\u5b8c\u6210"),
    CANCELED("-1", "\u5df2\u53d6\u6d88");

    private final String code;
    private final String title;

    private FetchTaskState(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static boolean isFinish(String code) {
        return FINISHED.getCode().equals(code);
    }
}

