/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.gcreport.samecontrol.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

public enum SameCtrlRuleTypeEnum {
    DIRECT_INVESTMENT("DIRECT_INVESTMENT", "\u6536\u8d2d\u65b9\u6295\u8d44\u89c4\u5219"),
    DISPOSER_INVESTMENT("DISPOSER_INVESTMENT", "\u5904\u7f6e\u65b9\u6295\u8d44\u89c4\u5219");

    private String code;
    private String name;

    private SameCtrlRuleTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static SameCtrlRuleTypeEnum codeOf(String code) {
        for (SameCtrlRuleTypeEnum sameCtrlRuleTypeEnum : SameCtrlRuleTypeEnum.values()) {
            if (!Objects.equals(sameCtrlRuleTypeEnum.getCode(), code)) continue;
            return sameCtrlRuleTypeEnum;
        }
        return null;
    }
}

