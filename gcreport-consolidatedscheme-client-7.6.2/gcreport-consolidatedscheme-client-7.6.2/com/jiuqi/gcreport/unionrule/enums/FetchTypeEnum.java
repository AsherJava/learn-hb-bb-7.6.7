/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum FetchTypeEnum {
    DEBIT_DETAIL("DEBIT_DETAIL", "\u501f\u65b9\u660e\u7ec6"),
    CREDIT_DETAIL("CREDIT_DETAIL", "\u8d37\u65b9\u660e\u7ec6"),
    ALL_DETAIL("ALL_DETAIL", "\u6240\u6709\u660e\u7ec6"),
    SUM("SUM", "\u6c47\u603b"),
    DEBIT_SUM("DEBIT_SUM", "\u501f\u65b9\u6c47\u603b"),
    CREDIT_SUM("CREDIT_SUM", "\u8d37\u65b9\u6c47\u603b");

    private String code;
    private String name;

    private FetchTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FetchTypeEnum codeOf(String code) {
        for (FetchTypeEnum fetchTypeEnum : FetchTypeEnum.values()) {
            if (!Objects.equals(fetchTypeEnum.getCode(), code)) continue;
            return fetchTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684FetchTypeEnum\u679a\u4e3e\uff01");
    }

    public static boolean isSum(FetchTypeEnum fetchType) {
        return DEBIT_SUM.equals((Object)fetchType) || CREDIT_SUM.equals((Object)fetchType) || SUM.equals((Object)fetchType);
    }
}

