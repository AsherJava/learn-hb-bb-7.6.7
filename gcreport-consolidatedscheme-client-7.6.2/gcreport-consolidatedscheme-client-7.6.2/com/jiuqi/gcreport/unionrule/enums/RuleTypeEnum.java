/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.gcreport.unionrule.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

public enum RuleTypeEnum {
    FIXED_TABLE("FIXED_TABLE", "\u56fa\u8868\u89c4\u5219"),
    FIXED_ASSETS("FIXED_ASSETS", "\u56fa\u5b9a\u8d44\u4ea7"),
    INVENTORY("INVENTORY", "\u5b58\u8d27\u89c4\u5219"),
    FLEXIBLE("FLEXIBLE", "\u7075\u6d3b\u89c4\u5219"),
    DIRECT_INVESTMENT("DIRECT_INVESTMENT", "\u76f4\u63a5\u6295\u8d44\u89c4\u5219"),
    INDIRECT_INVESTMENT("INDIRECT_INVESTMENT", "\u95f4\u63a5\u6295\u8d44\u89c4\u5219"),
    DIRECT_INVESTMENT_SEGMENT("DIRECT_INVESTMENT_SEGMENT", "\u76f4\u63a5\u6295\u8d44\u89c4\u5219\uff08\u5206\u6bb5\uff09"),
    INDIRECT_INVESTMENT_SEGMENT("INDIRECT_INVESTMENT_SEGMENT", "\u95f4\u63a5\u6295\u8d44\u89c4\u5219\uff08\u5206\u6bb5\uff09"),
    PUBLIC_VALUE_ADJUSTMENT("PUBLIC_VALUE_ADJUSTMENT", "\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219"),
    FLOAT_LINE("FLOAT_LINE", "\u6d6e\u52a8\u884c\u89c4\u5219"),
    RELATE_TRANSACTIONS("RELATE_TRANSACTIONS", "\u5173\u8054\u4ea4\u6613\u89c4\u5219"),
    LEASE("LEASE", "\u5355\u636e\u89c4\u5219"),
    FINANCIAL_CHECK("FINANCIAL_CHECK", "\u51ed\u8bc1\u7ea7\u5173\u8054\u4ea4\u6613\u89c4\u5219");

    private String code;
    private String name;

    private RuleTypeEnum(String code, String name) {
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

    public static RuleTypeEnum codeOf(String code) {
        for (RuleTypeEnum ruleTypeEnum : RuleTypeEnum.values()) {
            if (!Objects.equals(ruleTypeEnum.name(), code)) continue;
            return ruleTypeEnum;
        }
        return null;
    }
}

