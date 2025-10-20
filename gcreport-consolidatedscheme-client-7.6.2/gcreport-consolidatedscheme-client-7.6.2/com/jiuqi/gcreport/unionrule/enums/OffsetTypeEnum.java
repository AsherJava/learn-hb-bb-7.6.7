/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum OffsetTypeEnum {
    MORE_MONEY("MORE_MONEY", "\u6309\u8f83\u5927\u91d1\u989d"),
    LESS_MONEY("LESS_MONEY", "\u6309\u8f83\u5c0f\u91d1\u989d"),
    DEBIT_MONEY("DEBIT_MONEY", "\u6309\u501f\u65b9\u91d1\u989d"),
    CREDIT_MONEY("CREDIT_MONEY", "\u6309\u8d37\u65b9\u91d1\u989d"),
    CUSTOMIZE("CUSTOMIZE", "\u81ea\u5b9a\u4e49");

    private String code;
    private String name;

    private OffsetTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static OffsetTypeEnum codeOf(String code) {
        for (OffsetTypeEnum offsetTypeEnum : OffsetTypeEnum.values()) {
            if (!Objects.equals(offsetTypeEnum.getCode(), code)) continue;
            return offsetTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684OffsetTypeEnum\u679a\u4e3e\uff01");
    }
}

