/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum ReportDataReceiveType {
    ZXXF("ZXXF", "\u5728\u7ebf\u4e0b\u53d1"),
    ZXHQ("ZXHQ", "\u5728\u7ebf\u83b7\u53d6"),
    LXDR("LXDR", "\u79bb\u7ebf\u5bfc\u5165");

    private String code;
    private String title;

    private ReportDataReceiveType(String code, String title) {
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
        for (ReportDataReceiveType type : ReportDataReceiveType.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return null;
    }
}

