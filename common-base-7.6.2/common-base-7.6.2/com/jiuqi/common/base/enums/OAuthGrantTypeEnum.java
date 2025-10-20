/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.enums;

public enum OAuthGrantTypeEnum {
    AUTHORIZATION_CODE("authorization_code"),
    PASSWORD("password");

    private String type;

    private OAuthGrantTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OAuthGrantTypeEnum getOAuthGrantTypeEnumByType(String type) {
        return OAuthGrantTypeEnum.valueOf(type);
    }
}

