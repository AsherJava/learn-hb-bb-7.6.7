/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum PublishFlagEnum {
    UNPUBLISHED(0, "\u672a\u53d1\u5e03"),
    PUBLISHED(1, "\u5df2\u53d1\u5e03");

    private Integer code;
    private String name;

    private PublishFlagEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static PublishFlagEnum fromCode(Integer code) {
        for (PublishFlagEnum type : PublishFlagEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }

    public static PublishFlagEnum fromName(String name) {
        for (PublishFlagEnum type : PublishFlagEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

