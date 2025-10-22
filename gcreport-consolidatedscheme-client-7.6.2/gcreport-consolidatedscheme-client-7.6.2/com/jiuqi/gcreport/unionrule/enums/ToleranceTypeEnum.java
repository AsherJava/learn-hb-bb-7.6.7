/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum ToleranceTypeEnum {
    BY_MONEY("BY_MONEY", "\u6309\u5bb9\u5dee\u91d1\u989d"),
    BY_PROPORTION("BY_PROPORTION", "\u6309\u5bb9\u5dee\u6bd4\u4f8b");

    private String code;
    private String name;

    private ToleranceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ToleranceTypeEnum codeOf(String code) {
        for (ToleranceTypeEnum toleranceTypeEnum : ToleranceTypeEnum.values()) {
            if (!Objects.equals(toleranceTypeEnum.getCode(), code)) continue;
            return toleranceTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684ToleranceTypeEnum\u679a\u4e3e\uff01");
    }
}

