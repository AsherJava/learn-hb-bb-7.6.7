/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import java.util.HashMap;
import java.util.Map;

public enum TaskStatusEnum {
    EXECUTING("EXECUTING", "\u6b63\u5728\u88c5\u5165"),
    WAIT("WAIT", "\u7b49\u5f85\u4e2d"),
    STOP("STOP", "\u5df2\u6682\u505c"),
    SUCCESS("SUCCESS", "\u88c5\u5165\u6210\u529f"),
    ERROR("ERROR", "\u88c5\u5165\u5931\u8d25"),
    REJECTED("REJECTED", "\u5df2\u9000\u56de");

    private final String code;
    private final String title;

    private TaskStatusEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getTaskStatusTitleByCode(String code) {
        for (Map.Entry<String, String> entry : TaskStatusEnum.getTaskStatusMap().entrySet()) {
            if (!code.equals(entry.getKey())) continue;
            return entry.getValue();
        }
        return "\u65e0\u6b64\u7c7b\u578b";
    }

    public static String getTaskStatusCodeByTitle(String title) {
        for (TaskStatusEnum value : TaskStatusEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getCode();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static TaskStatusEnum code(String uploadStatusValue) {
        for (TaskStatusEnum uploadStatusEnum : TaskStatusEnum.values()) {
            if (!uploadStatusEnum.getCode().equals(uploadStatusValue)) continue;
            return uploadStatusEnum;
        }
        return null;
    }

    public static Map<String, String> getTaskStatusMap() {
        TaskStatusEnum[] values;
        HashMap<String, String> map = new HashMap<String, String>();
        for (TaskStatusEnum value : values = TaskStatusEnum.values()) {
            map.put(value.getCode(), value.getTitle());
        }
        return map;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

