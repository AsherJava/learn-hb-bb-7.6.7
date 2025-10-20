/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import java.util.Objects;

public enum FetchRangeEnum {
    DIFFERENCE("DIFFERENCE", "\u5dee\u989d"),
    UNION("UNION", "\u5408\u5e76"),
    HEADQUARTERS("HEADQUARTERS", "\u672c\u90e8"),
    ALL_DIRECT_SUBS("ALL_DIRECT_SUBS", "\u6240\u6709\u76f4\u63a5\u4e0b\u7ea7"),
    DIRECT_SUB_DETAIL("DIRECT_SUB_DETAIL", "\u76f4\u63a5\u4e0b\u7ea7\u660e\u7ec6"),
    CUSTOMIZE("CUSTOMIZE", "\u81ea\u5b9a\u4e49");

    private String code;
    private String name;

    private FetchRangeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FetchRangeEnum codeOf(String code) {
        for (FetchRangeEnum fetchRangeEnum : FetchRangeEnum.values()) {
            if (!Objects.equals(fetchRangeEnum.getCode(), code)) continue;
            return fetchRangeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684FetchRangeEnum\u679a\u4e3e\uff01");
    }
}

