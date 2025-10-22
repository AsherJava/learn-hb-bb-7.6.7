/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.checkquery.enums;

public enum CheckQueryLevelEnum {
    ALL("all", "\u6240\u6709\u5408\u5e76\u5c42\u7ea7"),
    CURRENT("current", "\u5f53\u524d\u5408\u5e76\u5c42\u7ea7"),
    UP("up", "\u4e0a\u7ea7\u5408\u5e76\u5c42\u7ea7"),
    DOWN("down", "\u4e0b\u7ea7\u5408\u5e76\u5c42\u7ea7"),
    CUSTOM("custom", "\u81ea\u5b9a\u4e49");

    private String code;
    private String title;

    private CheckQueryLevelEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static CheckQueryLevelEnum getEnumByCode(String code) {
        for (CheckQueryLevelEnum checkQueryLevelEnum : CheckQueryLevelEnum.values()) {
            if (!checkQueryLevelEnum.getCode().equals(code)) continue;
            return checkQueryLevelEnum;
        }
        return null;
    }
}

