/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.de;

public enum FillDataType {
    SUCCESS("\u5141\u8bb8\u586b\u62a5"),
    PERIOD_BEFORE_TASK("\u586b\u62a5\u65f6\u671f\u5c0f\u4e8e\u4efb\u52a1\u6709\u6548\u671f"),
    PERIOD_AFTER_TASK("\u586b\u62a5\u65f6\u671f\u5927\u4e8e\u4efb\u52a1\u6709\u6548\u671f"),
    TIME_BEFORE_PERIOD("\u586b\u62a5\u65f6\u95f4\u672a\u5f00\u59cb"),
    TIME_AFTER_PERIOD("\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f");

    private String message;

    private FillDataType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

