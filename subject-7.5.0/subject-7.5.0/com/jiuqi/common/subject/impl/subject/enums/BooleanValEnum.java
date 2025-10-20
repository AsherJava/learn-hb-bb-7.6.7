/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.subject.impl.subject.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum BooleanValEnum {
    YES(1, "\u662f"),
    NO(0, "\u5426");

    private Integer code;
    private String name;

    private BooleanValEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static BooleanValEnum fromCode(Integer code) {
        if (code == null) {
            return NO;
        }
        for (BooleanValEnum as : BooleanValEnum.values()) {
            if (as.code.compareTo(code) != 0) continue;
            return as;
        }
        return NO;
    }

    public static BooleanValEnum fromName(String name) {
        if (StringUtils.isEmpty((String)name)) {
            return NO;
        }
        for (BooleanValEnum as : BooleanValEnum.values()) {
            if (!as.name.equals(name)) continue;
            return as;
        }
        return NO;
    }
}

