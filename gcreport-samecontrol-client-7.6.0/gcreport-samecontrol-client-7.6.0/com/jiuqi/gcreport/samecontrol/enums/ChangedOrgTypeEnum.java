/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum ChangedOrgTypeEnum {
    SAME_CTRL_CHANGE(1, "\u540c\u63a7\u53d8\u52a8"),
    NON_SAME_CTRL_DISPOSE(0, "\u975e\u540c\u63a7\u5904\u7f6e"),
    NON_SAME_CTRL_NEW(2, "\u975e\u540c\u63a7\u65b0\u589e");

    private Integer code;
    private String title;

    private ChangedOrgTypeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getChangeTypeNameByCode(Integer code) {
        for (ChangedOrgTypeEnum orgTypeEnum : ChangedOrgTypeEnum.values()) {
            if (orgTypeEnum.getCode() != code) continue;
            return orgTypeEnum.getTitle();
        }
        return "";
    }
}

