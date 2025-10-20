/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

import java.util.Objects;

public enum SameCtrlChangeOrgDateTypeEnum {
    CURR_YEAR_CURR_MONTH("CURR_YEAR_CURR_MONTH", "\u53d8\u52a8\u5f53\u6708"),
    CURR_YEAR_AFTER_MONTH("CURR_YEAR_AFTER_MONTH", "\u53d8\u52a8\u4ee5\u540e\u671f-\u672c\u5e74"),
    AFTER_YEAR_CURR_MONTH("AFTER_YEAR_CURR_MONTH", "\u7b2c\u4e8c\u5e74\u5e74\u521d-\u7b2c\u4e8c\u5e74\u53d8\u52a8"),
    AFTER_YEAR_AFTER_MONTH("AFTER_YEAR_AFTER_MONTH", "\u7b2c\u4e8c\u5e74\u53d8\u52a8\u6708\u4ee5\u540e\u6708"),
    OTHER("OTHER", "\u5176\u4ed6");

    private String code;
    private String name;

    private SameCtrlChangeOrgDateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SameCtrlChangeOrgDateTypeEnum codeOf(String code) {
        for (SameCtrlChangeOrgDateTypeEnum sameCtrlChangeOrgDateTypeEnum : SameCtrlChangeOrgDateTypeEnum.values()) {
            if (!Objects.equals(sameCtrlChangeOrgDateTypeEnum.getCode(), code)) continue;
            return sameCtrlChangeOrgDateTypeEnum;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

