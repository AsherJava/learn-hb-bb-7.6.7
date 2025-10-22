/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrDataQueryTypeEnum {
    TOTAL("total", "\u603b\u6570\u660e\u7ec6"),
    CONFIRM("confirm", "\u5df2\u786e\u8ba4\u660e\u7ec6"),
    PARTCONFIRM("partconfirm", "\u90e8\u5206\u786e\u8ba4\u660e\u7ec6"),
    NOTCONFIRM("notconfirm", "\u672a\u786e\u8ba4\u660e\u7ec6"),
    REJECT("reject", "\u9a73\u56de\u660e\u7ec6");

    private String code;
    private String title;

    private ClbrDataQueryTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ClbrDataQueryTypeEnum getEnumByCode(String code) {
        for (ClbrDataQueryTypeEnum queryTypeEnum : ClbrDataQueryTypeEnum.values()) {
            if (!queryTypeEnum.getCode().equals(code)) continue;
            return queryTypeEnum;
        }
        return null;
    }
}

