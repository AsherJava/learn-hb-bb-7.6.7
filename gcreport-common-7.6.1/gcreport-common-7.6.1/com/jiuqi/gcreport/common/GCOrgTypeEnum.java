/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common;

import org.springframework.util.Assert;

public enum GCOrgTypeEnum {
    NONE("00000000-0000-0000-0000-000000000000", "NONE", "\u7a7a"),
    CORPORATE("00000000-0000-0000-0000-000000000001", "MD_ORG_CORPORATE", "\u6cd5\u4eba\u53e3\u5f84"),
    MANAGEMENT("00000000-0000-0000-0000-000000000002", "MD_ORG_MANAGEMENT", "\u7ba1\u7406\u53e3\u5f84");

    private String id;
    private String code;
    private String title;

    private GCOrgTypeEnum(String id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public static GCOrgTypeEnum getEnumByID(String id) {
        Assert.notNull((Object)id, "ID is required; it must not be null");
        for (GCOrgTypeEnum orgType : GCOrgTypeEnum.values()) {
            if (!orgType.getId().equals(id)) continue;
            return orgType;
        }
        return null;
    }

    public static GCOrgTypeEnum getEnumByCode(String code) {
        Assert.notNull((Object)code, "code is required; it must not be null");
        for (GCOrgTypeEnum orgType : GCOrgTypeEnum.values()) {
            if (!orgType.getCode().equals(code)) continue;
            return orgType;
        }
        return null;
    }

    public String getId() {
        return this.getCode();
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

