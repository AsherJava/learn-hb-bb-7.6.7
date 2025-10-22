/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum ReportDataServerType {
    AUTO("AUTO", "\u81ea\u52a8\u6ce8\u518c"),
    MANUAL("MANUAL", "\u624b\u5de5\u65b0\u589e");

    private String code;
    private String title;

    private ReportDataServerType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String findTitleByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (ReportDataServerType type : ReportDataServerType.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return null;
    }
}

