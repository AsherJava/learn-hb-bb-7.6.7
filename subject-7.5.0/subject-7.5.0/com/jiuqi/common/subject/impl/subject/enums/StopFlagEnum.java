/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.enums;

public enum StopFlagEnum {
    START(0, "\u542f\u7528"),
    STOP(1, "\u505c\u7528");

    private Integer code;
    private String name;

    private StopFlagEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static StopFlagEnum fromCode(Integer code) {
        for (StopFlagEnum type : StopFlagEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }

    public static StopFlagEnum fromName(String name) {
        for (StopFlagEnum type : StopFlagEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

