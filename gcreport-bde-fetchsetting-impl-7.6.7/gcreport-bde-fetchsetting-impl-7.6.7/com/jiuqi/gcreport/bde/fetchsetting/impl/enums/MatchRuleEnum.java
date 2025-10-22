/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.enums;

import com.jiuqi.bde.common.exception.BdeRuntimeException;

@Deprecated
public enum MatchRuleEnum {
    EQ("EQ", "\u7b49\u4e8e"),
    LIKE("LIKE", "\u6a21\u7cca\u5339\u914d"),
    TREE("TREE", "\u6811\u5f62\u5339\u914d");

    private final String code;
    private final String name;

    private MatchRuleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static MatchRuleEnum getMatchRuleEnumByCode(String code) {
        for (MatchRuleEnum type : MatchRuleEnum.values()) {
            if (!type.code.equals(code)) continue;
            return type;
        }
        throw new BdeRuntimeException(String.format("\u5339\u914d\u89c4\u5219\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879", code));
    }

    public static MatchRuleEnum getMatchRuleEnumByName(String name) {
        for (MatchRuleEnum type : MatchRuleEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        throw new BdeRuntimeException(String.format("\u5339\u914d\u89c4\u5219\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879", name));
    }
}

