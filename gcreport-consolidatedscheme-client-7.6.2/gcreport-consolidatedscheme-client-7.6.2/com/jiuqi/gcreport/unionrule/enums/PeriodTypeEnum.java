/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum PeriodTypeEnum {
    CURRENT("CURRENT", "\u672c\u671f"),
    PREVIOUS("PREVIOUS", "\u524d\u671f");

    private String code;
    private String name;

    private PeriodTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static PeriodTypeEnum codeOf(String code) {
        for (PeriodTypeEnum periodTypeEnum : PeriodTypeEnum.values()) {
            if (!Objects.equals(periodTypeEnum.getCode(), code)) continue;
            return periodTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684PeriodTypeEnum\u679a\u4e3e\uff01");
    }
}

