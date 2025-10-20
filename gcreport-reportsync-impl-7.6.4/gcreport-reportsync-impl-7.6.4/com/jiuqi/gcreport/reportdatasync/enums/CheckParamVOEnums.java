/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.reportdatasync.enums.ParamPackageStatus;

public enum CheckParamVOEnums {
    PROFESSIONAL_UNIT_STATUS_CHECK("500005", "\u4e13\u4e1a\u7248\u5355\u4f4d\u72b6\u6001\u68c0\u6d4b\u7ed3\u679c");

    private String code;
    private String title;

    private CheckParamVOEnums(String code, String title) {
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

