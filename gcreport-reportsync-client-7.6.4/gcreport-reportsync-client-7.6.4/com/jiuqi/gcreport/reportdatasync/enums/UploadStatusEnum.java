/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import java.util.HashMap;
import java.util.Map;

public enum UploadStatusEnum {
    UPLOADING("UPLOADING", "\u4e0a\u4f20\u4e2d"),
    SUCCESS("SUCCESS", "\u4e0a\u4f20\u6210\u529f"),
    ERROR("ERROR", "\u4e0a\u4f20\u5931\u8d25"),
    REPORTED("REPORTED", "\u5df2\u4e0a\u62a5"),
    REJECTED("REJECTED", "\u5df2\u9000\u56de");

    private final String code;
    private final String title;

    private UploadStatusEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getUploadStatusTitleByCode(String code) {
        for (Map.Entry<String, String> entry : UploadStatusEnum.getUploadStatusMap().entrySet()) {
            if (!code.equals(entry.getKey())) continue;
            return entry.getValue();
        }
        return "\u65e0\u6b64\u7c7b\u578b";
    }

    public static String getUploadStatusCodeByTitle(String title) {
        for (UploadStatusEnum value : UploadStatusEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getCode();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static UploadStatusEnum code(String uploadStatusValue) {
        for (UploadStatusEnum uploadStatusEnum : UploadStatusEnum.values()) {
            if (!uploadStatusEnum.getCode().equals(uploadStatusValue)) continue;
            return uploadStatusEnum;
        }
        return null;
    }

    public static Map<String, String> getUploadStatusMap() {
        UploadStatusEnum[] values;
        HashMap<String, String> map = new HashMap<String, String>();
        for (UploadStatusEnum value : values = UploadStatusEnum.values()) {
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

