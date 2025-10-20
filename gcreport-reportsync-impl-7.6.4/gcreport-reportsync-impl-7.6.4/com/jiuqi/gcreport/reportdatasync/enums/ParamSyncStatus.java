/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.enums;

public enum ParamSyncStatus {
    ALL_FAIL(0, "\u540c\u6b65\u5931\u8d25"),
    ALL_SUCCESS(1, "\u540c\u6b65\u6210\u529f"),
    PARTIAL_SUCCESS(2, "\u90e8\u5206\u6210\u529f");

    private Integer code;
    private String title;

    private ParamSyncStatus(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String findTitleByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ParamSyncStatus type : ParamSyncStatus.values()) {
            if (type.getCode() != code) continue;
            return type.getTitle();
        }
        return null;
    }
}

