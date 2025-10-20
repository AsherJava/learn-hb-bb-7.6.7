/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

public enum RuleTypeClass {
    ITEM_BY_ITEM("ITEM_BY_ITEM", "\u9010\u6761\u6620\u5c04"),
    NON_ITEM_BY_ITEM("NON_ITEM_BY_ITEM", "\u975e\u9010\u6761\u6620\u5c04"),
    CUSTOM("CUSTOM", "\u81ea\u5b9a\u4e49\u9010\u6761\u6620\u5c04");

    private String code;
    private String name;

    private RuleTypeClass(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static RuleTypeClass fromCode(String code) {
        for (RuleTypeClass ruleTypeClass : RuleTypeClass.values()) {
            if (!ruleTypeClass.getCode().equals(code)) continue;
            return ruleTypeClass;
        }
        return null;
    }

    public static RuleTypeClass fromName(String name) {
        for (RuleTypeClass ruleTypeClass : RuleTypeClass.values()) {
            if (!ruleTypeClass.getName().equals(name)) continue;
            return ruleTypeClass;
        }
        return null;
    }
}

