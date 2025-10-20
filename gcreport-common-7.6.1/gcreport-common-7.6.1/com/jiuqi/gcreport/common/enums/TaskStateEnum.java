/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.enums;

public enum TaskStateEnum {
    WAITTING("WAITTING", "\u7b49\u5f85\u6267\u884c"),
    EXECUTING("EXECUTING", "\u6267\u884c\u4e2d"),
    SUCCESS("SUCCESS", "\u6210\u529f"),
    ERROR("ERROR", "\u9519\u8bef"),
    STOP("STOP", "\u505c\u6b62"),
    SKIP("SKIP", "\u8df3\u8fc7");

    private String code;
    private String title;

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

    public static TaskStateEnum getByCode(String code) {
        for (TaskStateEnum state : TaskStateEnum.values()) {
            if (!state.code.equalsIgnoreCase(code)) continue;
            return state;
        }
        return null;
    }
}

