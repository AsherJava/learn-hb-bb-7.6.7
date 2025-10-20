/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum SubjectClassEnum {
    ASSET("ASSET", "\u8d44\u4ea7\u7c7b"),
    LIABILITY("LIABILITY", "\u8d1f\u503a\u7c7b"),
    EQUITY("EQUITY", "\u6240\u6709\u8005\u6743\u76ca\u7c7b"),
    COST("COST", "\u6210\u672c\u7c7b"),
    GAIN_LOSS("GAIN_LOSS", "\u635f\u76ca\u7c7b"),
    RECLASSIFY("RECLASSIFY", "\u91cd\u5206\u7c7b");

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

