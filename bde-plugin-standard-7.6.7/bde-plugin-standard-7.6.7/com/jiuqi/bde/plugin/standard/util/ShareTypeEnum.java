/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.standard.util;

public enum ShareTypeEnum {
    SHARE,
    ISOLATION,
    SHARE_ISOLATION;


    public static ShareTypeEnum fromCode(String code) {
        for (ShareTypeEnum type : ShareTypeEnum.values()) {
            if (!type.name().equals(code)) continue;
            return type;
        }
        return SHARE;
    }
}

