/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.inspector.common;

public enum TaskStateEnum {
    NONE("NONE", "\u4efb\u52a1\u65e0\u72b6\u6001"),
    WAITTING("WAITTING", "\u4efb\u52a1\u7b49\u5f85\u6267\u884c"),
    RUNNING("RUNNING", "\u4efb\u52a1\u6267\u884c\u4e2d"),
    SUCCESS("SUCCESS", "\u4efb\u52a1\u6267\u884c\u6210\u529f"),
    FAILED("FAILED", "\u4efb\u52a1\u4e0d\u901a\u8fc7"),
    EXCEPTION("EXCEPTION", "\u4efb\u52a1\u62a5\u9519\uff0c\u51fa\u73b0\u5f02\u5e38"),
    UNSUPPORT("UNSUPPORT", "\u4e0d\u652f\u6301");

    private final String code;
    private final String title;

    private TaskStateEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String titleOfCode(String code) {
        for (TaskStateEnum value : TaskStateEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return value.getTitle();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static String codeOfTitle(String title) {
        for (TaskStateEnum value : TaskStateEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getCode();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }
}

