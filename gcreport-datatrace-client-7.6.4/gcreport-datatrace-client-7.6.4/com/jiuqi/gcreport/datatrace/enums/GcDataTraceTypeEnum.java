/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.datatrace.enums;

import org.springframework.util.ObjectUtils;

public enum GcDataTraceTypeEnum {
    ZC("ZC", "\u8d44\u4ea7\u53f0\u8d26\u6570\u636e\u8ffd\u6eaf"),
    INVEST("INVEST", "\u6295\u8d44\u53f0\u8d26\u6570\u636e\u8ffd\u6eaf"),
    LEASE("LEASE", "\u79df\u8d41\u53f0\u8d26\u6570\u636e\u8ffd\u6eaf"),
    INPUTDATA("INPUTDATA", "\u5185\u90e8\u8868\u6570\u636e\u8ffd\u6eaf"),
    FINANCIALCHECK("FINANCIALCHECK", "\u5173\u8054\u4ea4\u6613\u6570\u636e\u8ffd\u6eaf"),
    FIXED_TABLE("FIXED_TABLE", "\u56fa\u8868\u89c4\u5219\u6570\u636e\u8ffd\u6eaf");

    private String type;
    private String title;

    private GcDataTraceTypeEnum(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static GcDataTraceTypeEnum valueOfType(String type) {
        if (ObjectUtils.isEmpty(type)) {
            return null;
        }
        for (GcDataTraceTypeEnum gcDataTraceTypeEnum : GcDataTraceTypeEnum.values()) {
            if (!gcDataTraceTypeEnum.getType().equals(type)) continue;
            return gcDataTraceTypeEnum;
        }
        return null;
    }
}

