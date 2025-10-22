/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.enums;

public enum MergeTypeEnum {
    CUR_LEVEL("CUR_LEVEL", "\u5f53\u524d\u5c42\u7ea7"),
    ALL_LEVEL("ALL_LEVEL", "\u5c42\u5c42\u5408\u5e76"),
    SUB_LEVEL("SUB_LEVEL", "\u4e0b\u7ea7\u5408\u5e76\u5c42\u7ea7"),
    CUSTOM_LEVEL("CUSTOM_LEVEL", "\u81ea\u5b9a\u4e49\u5c42\u7ea7");

    private String code;
    private String title;

    private MergeTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static MergeTypeEnum getEnumByCode(String code) {
        for (MergeTypeEnum operateEnum : MergeTypeEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

