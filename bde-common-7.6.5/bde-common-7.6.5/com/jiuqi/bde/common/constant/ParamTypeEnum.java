/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum ParamTypeEnum {
    SINGLE("SINGLE", "\u5355\u503c"),
    MULTIVALUED("LIKE", "\u591a\u503c"),
    RANGE("RANGE", "\u8303\u56f4");

    private String code;
    private String name;

    private ParamTypeEnum(String code, String name) {
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

    public static ParamTypeEnum getEnumByCode(String code) {
        for (ParamTypeEnum paramTypeEnum : ParamTypeEnum.values()) {
            if (!paramTypeEnum.getCode().equals(code)) continue;
            return paramTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684ParamTypeEnum\u679a\u4e3e code=" + code);
    }
}

