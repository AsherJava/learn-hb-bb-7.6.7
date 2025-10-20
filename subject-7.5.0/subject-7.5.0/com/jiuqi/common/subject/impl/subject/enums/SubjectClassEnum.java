/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.enums;

public enum SubjectClassEnum {
    ASSET("ASSET", "\u8d44\u4ea7\u7c7b"),
    LIABILITY("LIABILITY", "\u8d1f\u503a\u7c7b"),
    EQUITY("EQUITY", "\u6743\u76ca\u7c7b"),
    GAIN_LOSS("GAIN_LOSS", "\u635f\u76ca\u7c7b"),
    COST("COST", "\u6210\u672c\u7c7b"),
    CASH("CASH", "\u73b0\u91d1\u6d41\u91cf\u7c7b"),
    OTHER("OTHER", "\u5176\u4ed6\u7c7b");

    private String code;
    private String name;

    private SubjectClassEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static SubjectClassEnum fromCode(String code) {
        SubjectClassEnum[] types;
        for (SubjectClassEnum type : types = SubjectClassEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }

    public static SubjectClassEnum fromName(String name) {
        SubjectClassEnum[] types;
        for (SubjectClassEnum type : types = SubjectClassEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

