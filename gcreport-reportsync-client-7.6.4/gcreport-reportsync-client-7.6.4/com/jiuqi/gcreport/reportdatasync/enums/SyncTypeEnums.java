/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.enums;

public enum SyncTypeEnums {
    ORGDATA("orgdata", "\u7ec4\u7ec7\u673a\u6784"),
    REPORTDATA("reportdata", "\u62a5\u8868\u6570\u636e"),
    PARAMDATA("paramdata", "\u53c2\u6570");

    private String code;
    private String name;

    private SyncTypeEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static SyncTypeEnums getSyncTypeEnumsByCode(String code) {
        SyncTypeEnums[] values;
        for (SyncTypeEnums value : values = SyncTypeEnums.values()) {
            if (!value.getCode().equals(code)) continue;
            return value;
        }
        return null;
    }
}

