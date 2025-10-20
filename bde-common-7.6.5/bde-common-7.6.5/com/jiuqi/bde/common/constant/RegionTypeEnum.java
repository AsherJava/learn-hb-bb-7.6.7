/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum RegionTypeEnum {
    FIXED("FIXED", "\u56fa\u5b9a\u533a\u57df"),
    FLOAT("FLOAT", "\u6d6e\u52a8\u533a\u57df");

    private String code;
    private String name;

    private RegionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

