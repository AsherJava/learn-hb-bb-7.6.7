/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum MatchRuleEnum {
    EQ("EQ", "\u7b49\u4e8e"),
    LIKE("LIKE", "\u6a21\u7cca\u5339\u914d"),
    RANGE("RANGE", "\u8303\u56f4\u5339\u914d"),
    TREE("TREE", "\u6811\u5f62\u5339\u914d");

    private String code;
    private String name;

    private MatchRuleEnum(String code, String name) {
        this.code = code;
        this.name = name;
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

    public static MatchRuleEnum getEnumByCode(String code) {
        for (MatchRuleEnum MatchRuleEnum2 : MatchRuleEnum.values()) {
            if (!MatchRuleEnum2.getCode().equals(code)) continue;
            return MatchRuleEnum2;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684MatchRuleEnum\u679a\u4e3e code=" + code);
    }
}

