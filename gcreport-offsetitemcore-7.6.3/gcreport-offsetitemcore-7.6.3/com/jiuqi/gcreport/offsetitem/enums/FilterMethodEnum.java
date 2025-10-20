/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum FilterMethodEnum {
    UNIT("UNIT", 1, "\u6309\u6240\u6709\u4e0b\u7ea7\u5355\u4f4d\u5206\u7ec4\u5c55\u793a"),
    RULE("RULE", 2, "\u6309\u89c4\u5219\u5c55\u793a"),
    AMT("AMT", 3, "\u6309\u91d1\u989d\u5c55\u793a"),
    UNITGROUP("UNITGROUP", 4, "\u6309\u76f4\u63a5\u4e0b\u7ea7\u5206\u7ec4\u53cc\u4efd\u5c55\u793a"),
    RULESUMMARY("RULESUMMARY", 5, "\u6309\u89c4\u5219\u6c47\u603b\u5c55\u793a"),
    CHILDRENUNITGROUP("CHILDRENUNITGROUP", 6, "\u6309\u76f4\u63a5\u4e0b\u7ea7\u5355\u4f4d\u5206\u7ec4\u5c55\u793a"),
    COMMON("COMMON", 7, "\u901a\u7528"),
    OFFSET("OFFSET", 8, "\u62b5\u9500\u5206\u5f55"),
    ACCOUNTDETAILS("ACCOUNTDETAILS", 9, "\u79d1\u76ee\u660e\u7ec6\u884c");

    private final String code;
    private final int value;
    private final String title;

    private FilterMethodEnum(String code, int value, String title) {
        this.code = code;
        this.value = value;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static FilterMethodEnum valueOf(int value) {
        for (FilterMethodEnum methodEnum : FilterMethodEnum.values()) {
            if (methodEnum.getValue() != value) continue;
            return methodEnum;
        }
        return null;
    }
}

