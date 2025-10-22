/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common;

import org.springframework.util.Assert;

public enum GCAdjTypeEnum {
    BEFOREADJ("00000000-0000-0000-0000-000000000001", "BEFOREADJ", "\u8c03\u6574\u524d");

    private String id;
    private String code;
    private String title;

    private GCAdjTypeEnum(String id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public static GCAdjTypeEnum getEnumByID(String id) {
        Assert.notNull((Object)id, "ID is required; it must not be null");
        for (GCAdjTypeEnum orgType : GCAdjTypeEnum.values()) {
            if (!orgType.getId().equals(id)) continue;
            return orgType;
        }
        return null;
    }

    public static GCAdjTypeEnum getEnumByCode(String code) {
        Assert.notNull((Object)code, "code is required; it must not be null");
        for (GCAdjTypeEnum orgType : GCAdjTypeEnum.values()) {
            if (!orgType.getCode().equals(code)) continue;
            return orgType;
        }
        return null;
    }

    public static GCAdjTypeEnum getEnumByTaskID(String taskID) {
        return BEFOREADJ;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

