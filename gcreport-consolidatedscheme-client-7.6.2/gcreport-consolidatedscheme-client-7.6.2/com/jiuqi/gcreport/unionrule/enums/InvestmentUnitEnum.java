/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum InvestmentUnitEnum {
    INVESTMENT_UNIT("INVESTMENT_UNIT", "\u6295\u8d44\u5355\u4f4d"),
    INVESTED_UNIT("INVESTED_UNIT", "\u88ab\u6295\u8d44\u5355\u4f4d");

    private String code;
    private String name;

    private InvestmentUnitEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static InvestmentUnitEnum codeOf(String code) {
        for (InvestmentUnitEnum unitEnum : InvestmentUnitEnum.values()) {
            if (!Objects.equals(unitEnum.getCode(), code)) continue;
            return unitEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684InvestmentUnitEnum\u679a\u4e3e\uff01");
    }
}

