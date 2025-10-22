/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.org.api.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum GcOrgOperateEnum {
    INSERT("INSERT", "\u65b0\u589e"),
    UPDATE("UPDATE", "\u4fee\u6539"),
    DELETE("DELETE", "\u5220\u9664");

    private String code;
    private String title;

    private GcOrgOperateEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static GcOrgOperateEnum findOperateType(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (GcOrgOperateEnum itt : GcOrgOperateEnum.values()) {
            if (!itt.name().equalsIgnoreCase(code)) continue;
            return itt;
        }
        return null;
    }
}

