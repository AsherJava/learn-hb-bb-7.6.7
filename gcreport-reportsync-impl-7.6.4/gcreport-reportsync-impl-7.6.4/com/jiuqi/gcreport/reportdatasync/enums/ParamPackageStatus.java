/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum ParamPackageStatus {
    NOT_START("NOT_START", "\u672a\u542f\u7528"),
    STARTED("STARTED", "\u5df2\u542f\u7528"),
    DEPRECATED("DEPRECATED", "\u5df2\u8fc7\u671f");

    private String code;
    private String title;

    private ParamPackageStatus(String code, String title) {
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
        for (ParamPackageStatus type : ParamPackageStatus.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return null;
    }
}

