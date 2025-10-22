/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum FixedAssetsTypeEnum {
    DEPRECIATION_SUBJECT("DEPRECIATION_SUBJECT", "\u8ba1\u63d0\u6298\u65e7\u79d1\u76ee"),
    DISPOSE_SUBJECT("DISPOSE_SUBJECT", "\u5904\u7f6e\u79d1\u76ee"),
    ASSET_SUBJECT("ASSET_SUBJECT", "\u8d44\u4ea7\u7c7b\u578b\u79d1\u76ee"),
    FORMULA("FORMULA", "\u516c\u5f0f"),
    CUSTOMIZE("CUSTOMIZE", "\u81ea\u5b9a\u4e49");

    private String code;
    private String name;

    private FixedAssetsTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FixedAssetsTypeEnum codeOf(String code) {
        for (FixedAssetsTypeEnum typeEnum : FixedAssetsTypeEnum.values()) {
            if (!Objects.equals(typeEnum.getCode(), code)) continue;
            return typeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684FixedAssetsTypeEnum\u679a\u4e3e\uff01");
    }
}

