/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.gcreport.offsetitem.enums;

import tk.mybatis.mapper.util.StringUtil;

public enum GcBusinessTypeQueryRuleEnum {
    EQ("EQ"),
    ACROSS("ACROSS"),
    ALL("ALL");

    private String code;

    public String getCode() {
        return this.code;
    }

    private GcBusinessTypeQueryRuleEnum(String code) {
        this.code = code;
    }

    public static GcBusinessTypeQueryRuleEnum getEnumByCode(String code) {
        if (StringUtil.isEmpty((String)code)) {
            return null;
        }
        for (GcBusinessTypeQueryRuleEnum item : GcBusinessTypeQueryRuleEnum.values()) {
            if (!item.getCode().equals(code)) continue;
            return item;
        }
        return null;
    }
}

